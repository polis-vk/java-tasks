package ru.mail.polis.homework.exception;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Задание: Нужно создать свою мини библиотеку, с удаленным роботом и пультом управления.
 * Каждый класс оценивается отдельно
 * <p>
 * Пункт управления роботами. Через него управляются все роботы
 * <p>
 * 4 тугрика
 */
public class RobotRemoteControl {
    private final RobotConnectionManager connectionManager;

    public RobotRemoteControl(Robot... robots) {
        List<Robot> allRobots = new ArrayList<>(Arrays.asList(robots));
        connectionManager = new RobotConnectionManagerCustom(allRobots);
    }

    /**
     * Метод должен открыть соединение и отправить робота в указанную точку. При неудаче - повторить действие еще 2 раза,
     * Если это не удалось, то прокинуть эту ошибку на уровень выше.
     * Попытка считается успешной, если соединение открылось и вызвался метод moveRobotTo без исключений.
     */
    public void moveTo(int robotId, int toX, int toY) throws RobotNotFoundException, RobotConnectionException {
        RobotConnection robotConnection = connectionManager.getConnection(robotId);

        int tryConnection = 0;
        boolean isConnection = false;
        while (tryConnection < 3 || !isConnection) {
            isConnection = robotConnection.connection();
            tryConnection++;
        }

        if (tryConnection == 3) {
            robotConnection.close();
            throw new RobotConnectionException("Unsuccessful connection", robotId);
        }

        try {
            robotConnection.moveRobotTo(toX, toY);
        } catch (RobotConnectionException ex) {
            robotConnection.close();
        }

    }

}
