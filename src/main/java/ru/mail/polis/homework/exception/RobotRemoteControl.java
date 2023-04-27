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

    public RobotRemoteControl() {
        // создаём 3 подключения в конструкторе для примера
        connectionManager = new ConnectionManager();
        Robot robot_1 = new Robot(5, 5);
        Robot robot_2 = new Robot(10, 10);
        Robot robot_3 = new Robot(15, 15);
        ((ConnectionManager) connectionManager).addRobotConnection(robot_1);
        ((ConnectionManager) connectionManager).addRobotConnection(robot_2);
        ((ConnectionManager) connectionManager).addRobotConnection(robot_3);
    }

    /**
     * Метод должен открыть соединение и отправить робота в указанную точку. При неудаче - повторить действие еще 2 раза,
     * Если это не удалось, то прокинуть эту ошибку на уровень выше.
     * Попытка считается успешной, если соединение открылось и вызвался метод moveRobotTo без исключений.
     */
    public void moveTo(int robotId, int toX, int toY) throws RobotConnectionException {
        RobotConnection robotConnection = null;
        for (int i = 0; i < 3; i++) {
            try {
                robotConnection = connectionManager.getConnection(robotId);
                robotConnection.moveRobotTo(toX, toY);
                return;
            } catch (RobotConnectionException exception) {
                throw new RobotConnectionException("Failed connection!");
            } finally {
                if (robotConnection != null) {
                    robotConnection.close();
                }
            }
        }
        throw new RobotConnectionException("Failed connection!");
    }

    public RobotConnectionManager getConnectionManager() {
        return connectionManager;
    }
}
