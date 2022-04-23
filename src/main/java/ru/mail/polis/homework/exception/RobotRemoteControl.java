package ru.mail.polis.homework.exception;

import java.util.ArrayList;
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

    private final RobotConnectionManager robotConnectionManager;
    private final static List<Robot> robots = new ArrayList<>();

    public RobotRemoteControl(Robot robot) {
        robotConnectionManager = new RobotConnectionManager();
        robots.add(robot);
    }

    /**
     * Метод должен открыть соединение и отправить робота в указанную точку. При неудаче - повторить действие еще 2 раза,
     * Если это не удалось, то прокинуть эту ошибку на уровень выше.
     * Попытка считается успешной, если соединение открылось и вызвался метод moveRobotTo без исключений.
     */
    public void moveTo(int robotId, int toX, int toY) {
        boolean isConnection = false;
        for (int i = 0; !isConnection; i++) {
            try {
                RobotConnection robotConnection = robotConnectionManager.getConnection(robotId);
                robotConnection.moveRobotTo(toX, toY);
                isConnection = true;
            } catch (RobotConnectionException e) {
                if (i == 2) {
                    throw e;
                }
            }
        }
    }
    public static Robot getRobot(int id){
        return robots.get(id);
    }


}
