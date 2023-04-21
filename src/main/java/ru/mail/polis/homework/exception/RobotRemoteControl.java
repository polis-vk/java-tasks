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

    private final RobotConnectionManager connectionManager;

    public RobotRemoteControl(RobotConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    /**
     * Метод должен открыть соединение и отправить робота в указанную точку. При неудаче - повторить действие еще 2 раза,
     * Если это не удалось, то прокинуть эту ошибку на уровень выше.
     * Попытка считается успешной, если соединение открылось и вызвался метод moveRobotTo без исключений.
     */
    public int moveTo(int robotId, int toX, int toY) throws Exception {

        int tryCount = 0;
        while (tryCount <= 2) {
            try (RobotConnection localRobotConnection = connectionManager.getConnection(robotId)) {
                System.out.println("\nRobot " + robotId + " begins operation");
                localRobotConnection.moveRobotTo(toX, toY);
                return 1;  // successful connection
            } catch (Exception e) {
                System.out.println("Connection Error");
            }
            System.out.println("Reconnection...");
            System.out.println();
            tryCount++;
        }
        System.out.println("Third try failed. Terminating operation...");
        throw new Exception();


    }

}
