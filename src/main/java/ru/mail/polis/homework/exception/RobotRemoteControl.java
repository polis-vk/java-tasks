package ru.mail.polis.homework.exception;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Задание: Нужно создать свою мини библиотеку, с удаленным роботом и пультом управления.
 * Каждый класс оценивается отдельно
 *
 * Пункт управления роботами. Через него управляются все роботы
 *
 * 4 тугрика
 */
public class RobotRemoteControl {
    public static final List<Robot> robotList = new ArrayList<>();
    private final RobotConnectionManager connectionManager = new RobotConnectionManagerClass();

    /**
     * Метод должен открыть соединение и отправить робота в указанную точку. При неудаче - повторить действие еще 2 раза,
     * Если это не удалось, то прокинуть эту ошибку на уровень выше.
     * Попытка считается успешной, если соединение открылось и вызвался метод moveRobotTo без исключений.
     */
    public void moveTo(int robotId, int toX, int toY) throws RobotConnectException {
        int countAttempts = 3;
        for (int i = 1; i <= countAttempts; i++) {
            try (RobotConnectionClass robotConnection = (RobotConnectionClass) connectionManager.getConnection(robotId)) {
                robotConnection.moveRobotTo(toX, toY);
                break;
            } catch (RobotConnectException e) {
                if (i == countAttempts) {
                    throw e;
                }
            }
        }
    }

}
