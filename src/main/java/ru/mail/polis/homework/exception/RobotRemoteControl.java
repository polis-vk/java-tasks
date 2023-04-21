package ru.mail.polis.homework.exception;

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

    public RobotRemoteControl(RobotConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    /**
     * Метод должен открыть соединение и отправить робота в указанную точку. При неудаче - повторить действие еще 2 раза,
     * Если это не удалось, то прокинуть эту ошибку на уровень выше.
     * Попытка считается успешной, если соединение открылось и вызвался метод moveRobotTo без исключений.
     */
    public void moveTo(int robotId, int toX, int toY) throws RobotConnectionException {
        RobotConnection connection;

        int tries = 0;
        while (true) {
            try {
                connection = connectionManager.getConnection(robotId);
                connection.moveRobotTo(toX, toY);
                break;
            } catch (RobotConnectionException e) {
                if (tries == 2) {
                    throw e;
                }
            }
            tries++;
        }
    }
}
