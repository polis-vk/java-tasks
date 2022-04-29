package ru.mail.polis.homework.exception;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Задание: Нужно создать свою мини библиотеку, с удаленным роботом и пультом управления.
 * Каждый класс оценивается отдельно
 *
 * Пункт управления роботами. Через него управляются все роботы
 *
 * 4 тугрика
 */
public class RobotRemoteControl {

    private final RobotConnectionManager connectionManager;

    public RobotRemoteControl(List<Robot> robotList) {
        connectionManager = new ConnectionManager(robotList.stream()
                .collect(Collectors.toMap(Robot::getRobotId, robot -> robot)));
    }

    /**
     * Метод должен открыть соединение и отправить робота в указанную точку. При неудаче - повторить действие еще 2 раза,
     * Если это не удалось, то прокинуть эту ошибку на уровень выше.
     * Попытка считается успешной, если соединение открылось и вызвался метод moveRobotTo без исключений.
     */
    public void moveTo(int robotId, int toX, int toY) throws RobotConnectionException {
        int countTry = 0;
        while (true) {
            try (RobotConnection connection = connectionManager.getConnection(robotId)){
                connection.moveRobotTo(toX, toY);
                break;
            } catch (RobotConnectionException e) {
                if (countTry > 1) {
                    throw e;
                }
            }

            countTry++;
        }
    }

}
