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

    private static final Map<Integer, Robot> robots = new HashMap<>();
    private final RobotConnectionManager connectionManager = new ConnectionManager();

    /**
     * Метод должен открыть соединение и отправить робота в указанную точку. При неудаче - повторить действие еще 2 раза,
     * Если это не удалось, то прокинуть эту ошибку на уровень выше.
     * Попытка считается успешной, если соединение открылось и вызвался метод moveRobotTo без исключений.
     */
    public void moveTo(int robotId, int toX, int toY) throws ConnectionFailedException {
        int attempts = 3;
        for (int i = 0; i < attempts; i++) {
            try (RobotConnection connection = connectionManager.getConnection(robotId)) {
                connection.moveRobotTo(toX, toY);
                i = attempts;
            } catch (ConnectionFailedException e) {
                if (i == attempts - 1) {
                    throw e;
                }
            }
        }
    }

    public static Robot getRobotById(int Id) {
        return robots.get(Id);
    }

}
