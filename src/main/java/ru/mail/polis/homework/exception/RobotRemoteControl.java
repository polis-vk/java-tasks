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

    public RobotRemoteControl(List<Robot> robots) {
        this.connectionManager = new ConnectionManager(robots);
    }


    private static final int MAX_TRIES = 3;
    /**
     * Метод должен открыть соединение и отправить робота в указанную точку. При неудаче - повторить действие еще 2 раза,
     * Если это не удалось, то прокинуть эту ошибку на уровень выше.
     * Попытка считается успешной, если соединение открылось и вызвался метод moveRobotTo без исключений.
     */
    public void moveTo(int robotId, int toX, int toY) throws ConnectionException, InterruptedException {
        int countTries = 0;
        while (true) {
            try (RobotConnection connection = connectionManager.getConnection(robotId)) {
                connection.moveRobotTo(toX, toY);
                break;
            } catch (ConnectionException e) {
                if (++countTries == MAX_TRIES) {
                    throw e;
                }
                Thread.sleep(100);
            }
        }
    }

}
