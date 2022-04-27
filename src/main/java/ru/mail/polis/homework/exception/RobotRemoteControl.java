package ru.mail.polis.homework.exception;


import java.util.ArrayList;

/**
 * Задание: Нужно создать свою мини библиотеку, с удаленным роботом и пультом управления.
 * Каждый класс оценивается отдельно
 * <p>
 * Пункт управления роботами. Через него управляются все роботы
 * <p>
 * 4 тугрика
 */
public class RobotRemoteControl {

    private final RobotConnectionManager connectionManager = new ConnectionManager(new ArrayList<>());

    /**
     * Метод должен открыть соединение и отправить робота в указанную точку. При неудаче - повторить действие еще 2 раза,
     * Если это не удалось, то прокинуть эту ошибку на уровень выше.
     * Попытка считается успешной, если соединение открылось и вызвался метод moveRobotTo без исключений.
     */
    public void moveTo(int robotId, int toX, int toY) throws RobotConnectionException {
        for (int retries = 0; ; retries++) {
            try {
                RobotConnection connection = connectionManager.getConnection(robotId);
                connection.moveRobotTo(toX, toY);
                connection.close();
                return;
            } catch (RobotConnectionException e) {
                if (retries > 1) {
                    throw e;
                }
            }
        }
    }
}
