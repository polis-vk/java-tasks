package ru.mail.polis.homework.exception;

import java.util.Map;

/**
 * Задание: Нужно создать свою мини библиотеку, с удаленным роботом и пультом управления.
 * Каждый класс оценивается отдельно
 * <p>
 * Пункт управления роботами. Через него управляются все роботы
 * <p>
 * 4 тугрика
 */
public class RobotRemoteControl {
    private static final int MAX_CONNECTION_ATTEMPTS = 3;
    private final RobotConnectionManager connectionManager;

    public RobotRemoteControl(Map<Integer, Robot> connections) {
        connectionManager = new RobotConnectionManagerImpl(connections);
    }

    /**
     * Метод должен открыть соединение и отправить робота в указанную точку. При неудаче - повторить действие еще 2 раза,
     * Если это не удалось, то прокинуть эту ошибку на уровень выше.
     * Попытка считается успешной, если соединение открылось и вызвался метод moveRobotTo без исключений.
     */
    public void moveTo(int robotId, int toX, int toY) throws RobotConnectionException {
        for (int tryCount = 1; tryCount <= MAX_CONNECTION_ATTEMPTS; ++tryCount) {
            try (RobotConnection connection = connectionManager.getConnection(robotId)) {
                connection.moveRobotTo(toX, toY);
                return;
            } catch (RobotConnectionException e) {
                if (tryCount == MAX_CONNECTION_ATTEMPTS) {
                    throw e;
                }
                System.out.println("Connection with robot lost. Robot ID: " + robotId + ". Retrying...");
            }
        }
    }

}
