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

    private final RobotConnectionManagerImpl robotConnectionManager;

    public RobotRemoteControl(int robotId, Robot robot) {
        robotConnectionManager = new RobotConnectionManagerImpl();
        RobotConnectionManagerImpl.addRobot(robotId, robot);
    }

    /**
     * Метод должен открыть соединение и отправить робота в указанную точку. При неудаче - повторить действие еще 2 раза,
     * Если это не удалось, то прокинуть эту ошибку на уровень выше.
     * Попытка считается успешной, если соединение открылось и вызвался метод moveRobotTo без исключений.
     */
    public void moveTo(int robotId, int toX, int toY) {
        for (int i = 0; ; i++) {
            try {
                RobotConnectionImpl robotConnection = robotConnectionManager.getConnection(robotId);
                robotConnection.moveRobotTo(toX, toY);
                break;
            } catch (RobotConnectionException e) {
                if (i == 2) {
                    throw e;
                }
            }
        }
    }

    public void makeRoar(int robotId) {
        for (int i = 0; ; i++) {
            try {
                RobotConnectionImpl robotConnection = robotConnectionManager.getConnection(robotId);
                robotConnection.makeRoar();
                break;
            } catch (RobotConnectionException e) {
                if (i == 2) {
                    throw e;
                }
            }
        }
    }

    public void pickMushroom(int robotId) {
        for (int i = 0; ; i++) {
            try {
                RobotConnectionImpl robotConnection = robotConnectionManager.getConnection(robotId);
                robotConnection.pickMushroom();
                break;
            } catch (RobotConnectionException e) {
                if (i == 2) {
                    throw e;
                }
            }
        }
    }

    public void throwMushroom(int robotId) {
        for (int i = 0; ; i++) {
            try {
                RobotConnectionImpl robotConnection = robotConnectionManager.getConnection(robotId);
                robotConnection.throwMushroom();
                break;
            } catch (RobotConnectionException e) {
                if (i == 2) {
                    throw e;
                }
            }
        }
    }
}
