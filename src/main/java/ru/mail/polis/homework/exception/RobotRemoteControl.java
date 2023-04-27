package ru.mail.polis.homework.exception;

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
    private final RobotConnectionManager connectionManager;

    RobotRemoteControl(List<Robot> robots) {
        connectionManager = new RobotConnectionManagerRealization(robots);
    }

    /**
     * Метод должен открыть соединение и отправить робота в указанную точку. При неудаче - повторить действие еще 2 раза,
     * Если это не удалось, то прокинуть эту ошибку на уровень выше.
     * Попытка считается успешной, если соединение открылось и вызвался метод moveRobotTo без исключений.
     */
    private final int NUMBER_ALLOWED_ERRORS = 3;

    public void moveTo(int robotId, int toX, int toY) throws RobotConnectionException {
        for (int attempt = 1; attempt <= NUMBER_ALLOWED_ERRORS; attempt++) {
            try (RobotConnection connection = connectionManager.getConnection(robotId)) {
                connection.moveRobotTo(toX, toY);
                return;
            } catch (RobotConnectionException exception) {
                if (attempt == NUMBER_ALLOWED_ERRORS) {
                    throw exception;
                }
            }
        }
    }

}
