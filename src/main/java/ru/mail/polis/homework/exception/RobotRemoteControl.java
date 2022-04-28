package ru.mail.polis.homework.exception;

/**
 * Задание: Нужно создать свою мини библиотеку, с удаленным роботом и пультом управления.
 * Каждый класс оценивается отдельно
 * <p>
 * Пункт управления роботами. Через него управляются все роботы
 * <p>
 * 4 тугрика
 */
public class RobotRemoteControl {

    public final RobotConnectionManager connectionManager = new ConnectionManager();

    /**
     * Метод должен открыть соединение и отправить робота в указанную точку. При неудаче - повторить действие еще 2 раза,
     * Если это не удалось, то прокинуть эту ошибку на уровень выше.
     * Попытка считается успешной, если соединение открылось и вызвался метод moveRobotTo без исключений.
     */
    public void moveTo(int robotId, int toX, int toY) throws RobotConnectionException {
        int tries = 0;
        for (int i = 1; i <= 3; i++) {
            try {
                RobotConnection connection = connectionManager.getConnection(robotId);
                connection.moveRobotTo(toX, toY);
                break;
            } catch (RobotConnectionException e) {
                tries++;
                if (tries == 3) {
                    throw e;
                }
            }
        }
        connectionManager.closeConnection(robotId);
    }
}
