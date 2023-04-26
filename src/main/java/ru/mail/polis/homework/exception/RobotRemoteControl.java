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

    private RobotConnectionManager connectionManager;
    private static final int ATTEMPTS = 3;

    /**
     * Метод должен открыть соединение и отправить робота в указанную точку. При неудаче - повторить действие еще 2 раза,
     * Если это не удалось, то прокинуть эту ошибку на уровень выше.
     * Попытка считается успешной, если соединение открылось и вызвался метод moveRobotTo без исключений.
     */
    public void moveTo(int robotId, int toX, int toY) throws ConnectionExc {
        int numOfFailedAttempts = 1;
        while (numOfFailedAttempts < ATTEMPTS) {
            try (RobotConnection connection = connectionManager.getConnection(robotId)) {
                connection.moveRobotTo(toX, toY);
            } catch (ConnectionExc e) {
                if (numOfFailedAttempts >= 2) {
                    throw e;
                }
            } finally {
                numOfFailedAttempts++;
            }
        }

    }

}
