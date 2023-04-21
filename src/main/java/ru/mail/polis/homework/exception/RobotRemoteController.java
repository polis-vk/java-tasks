package ru.mail.polis.homework.exception;
import java.util.HashMap;

/**
 * Задание: Нужно создать свою мини библиотеку, с удаленным роботом и пультом управления.
 * Каждый класс оценивается отдельно
 * <p>
 * Пункт управления роботами. Через него управляются все роботы
 * <p>
 * 4 тугрика
 */
public class RobotRemoteController {

    private final RobotConnectionManager connectionManager = new ConnectionManager(new HashMap<>());

    /**
     * Метод должен открыть соединение и отправить робота в указанную точку. При неудаче - повторить действие еще 2 раза,
     * Если это не удалось, то прокинуть эту ошибку на уровень выше.
     * Попытка считается успешной, если соединение открылось и вызвался метод moveRobotTo без исключений.
     */
    public void moveTo(int robotId, int toX, int toY) {
        for (int i = 0; true; i++) {
            try (RobotConnection robotStream = connectionManager.getConnection(robotId)) {
                robotStream.moveRobotTo(toX, toY);
                return;
            } catch (RuntimeException e) {
                if (i == 2) {
                    throw e;
                }
            }
        }
    }

}
