package ru.mail.polis.homework.exception;

import ru.mail.polis.homework.exception.exception.RobotConnectionException;
import ru.mail.polis.homework.exception.impl.RobotConnectionManagerImpl;

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

    private static final int CONNECTION_ATTEMPTS = 3;

    private final RobotConnectionManager connectionManager;

    public RobotRemoteControl(Map<Integer, Robot> robots) {
        connectionManager = new RobotConnectionManagerImpl(robots);
    }

    /**
     * Метод должен открыть соединение и отправить робота в указанную точку. При неудаче - повторить действие еще 2 раза,
     * Если это не удалось, то прокинуть эту ошибку на уровень выше.
     * Попытка считается успешной, если соединение открылось и вызвался метод moveRobotTo без исключений.
     */
    public void moveTo(int robotId, int toX, int toY) throws RobotConnectionException {
        for (int tryNumber = 0; tryNumber < CONNECTION_ATTEMPTS; tryNumber++) {
            try (RobotConnection connection = connectionManager.getConnection(robotId)) {
                connection.moveRobotTo(toX, toY);
                return;
            } catch (RobotConnectionException e) {
                if (tryNumber == CONNECTION_ATTEMPTS - 1) {
                    throw e;
                }
                System.out.println("Connection with robot by id [" + robotId + "] was lost. Retry..");
            }
        }
    }

}
