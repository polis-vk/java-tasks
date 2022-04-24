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

    private final RobotConnectionManager connectionManager = new ConnectionManager(100);

    /**
     * Метод должен открыть соединение и отправить робота в указанную точку. При неудаче - повторить действие еще 2 раза,
     * Если это не удалось, то прокинуть эту ошибку на уровень выше.
     * Попытка считается успешной, если соединение открылось и вызвался метод moveRobotTo без исключений.
     */
    public void moveTo(int robotId, int toX, int toY) throws RobotEstablishConnectionException, RobotConnectionFailedException {
        int numberAttempts = 3;
        while (numberAttempts > 0) {
            try (RobotConnection robotConnection = connectionManager.getConnection(robotId)) {
                numberAttempts = 0;
                robotConnection.moveRobotTo(toX, toY);
            } catch (RobotEstablishConnectionException | RobotConnectionFailedException e) {
                numberAttempts--;
                if (numberAttempts == 0) {
                    throw e;
                }
            }
        }
    }
}
