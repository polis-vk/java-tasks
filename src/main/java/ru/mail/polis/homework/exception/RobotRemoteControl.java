package ru.mail.polis.homework.exception;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
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

    private RobotConnectionManager connectionManager;
    RobotRemoteControl(List<Robot> collection)
    {
        this.connectionManager = new RobotRemoteConnectionManager(collection);
    }

    /**
     * Метод должен открыть соединение и отправить робота в указанную точку. При неудаче - повторить действие еще 2 раза,
     * Если это не удалось, то прокинуть эту ошибку на уровень выше.
     * Попытка считается успешной, если соединение открылось и вызвался метод moveRobotTo без исключений.
     */
    public void moveTo(int robotId, int toX, int toY) throws RobotConnectionException {
        int count_of_conn_attempts = 0; boolean fallenServer = false;
        while(count_of_conn_attempts < 3)
        {
            try(var conn = connectionManager.getConnection(robotId))
            {
                conn.moveRobotTo(toX, toY);
                fallenServer = true;
                count_of_conn_attempts = 3;
            }
            catch(RobotConnectionException rce)
            {
                if(fallenServer)
                {
                    count_of_conn_attempts = 3;
                }
                else
                {
                    ++count_of_conn_attempts;
                    if(count_of_conn_attempts == 3)
                    {
                        throw new RobotConnectionException();
                    }
                }
            }
        }
    }

}
