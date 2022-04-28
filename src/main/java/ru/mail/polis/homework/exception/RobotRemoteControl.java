package ru.mail.polis.homework.exception;

import java.util.HashMap;
import java.util.Map;

/**
 * Задание: Нужно создать свою мини библиотеку, с удаленным роботом и пультом управления.
 * Каждый класс оценивается отдельно
 *
 * Пункт управления роботами. Через него управляются все роботы
 *
 * 4 тугрика
 */
public class RobotRemoteControl {

    private RobotConnectionManager connectionManager;
    private Robot robot1;
    private Robot robot2;
    private Robot robot3;

    private void makeConnectionManager(){
        Map<Integer, Robot> robots = new HashMap<>();
        robots.put(1, robot1);
        robots.put(2, robot2);
        robots.put(3, robot3);
        connectionManager = new ConnectionManager(robots);
    }

    /**
     * Метод должен открыть соединение и отправить робота в указанную точку. При неудаче - повторить действие еще 2 раза,
     * Если это не удалось, то прокинуть эту ошибку на уровень выше.
     * Попытка считается успешной, если соединение открылось и вызвался метод moveRobotTo без исключений.
     */
    public void moveTo(int robotId, int toX, int toY) throws RobotConnectionException {
        for (int i = 0; i < 3; i++) {
            try (RobotConnection robotConnection = connectionManager.getConnection(robotId)) {
                if (robotConnection == null) {
                    throw new IllegalArgumentException();
                }
                robotConnection.moveRobotTo(toX, toY);
                robotConnection.close();
                break;
            } catch (RobotConnectionException e) {
                if (i == 2) {
                    throw e;
                }
            }
        }
    }
}
