package ru.mail.polis.homework.exception;

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

    public RobotRemoteControl(List<Robot> bots) {
        connectionManager = new ConnectionManager(bots);
    }

    /**
     * Метод должен открыть соединение и отправить робота в указанную точку. При неудаче - повторить действие еще 2 раза,
     * Если это не удалось, то прокинуть эту ошибку на уровень выше.
     * Попытка считается успешной, если соединение открылось и вызвался метод moveRobotTo без исключений.
     */
    public void moveTo(int robotId, int toX, int toY) throws NoConnectionException {
        for (int attemptCounter = 0; attemptCounter < 3; attemptCounter++) {
            try (RobotConnection connection = connectionManager.getConnection(robotId)) {
                connection.moveRobotTo(toX, toY);
                return;
            } catch (NoConnectionException exception) {
                if (attemptCounter > 1) {
                    throw exception;
                }
            }
        }
    }

}
