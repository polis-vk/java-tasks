package ru.mail.polis.homework.exception;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Тесты работают, если запускать их по-отдельности
 */
public class RobotRemoteControlTest {

    @Test
    public void testRobot(){
        RobotRemoteControl robotRemoteControl = new RobotRemoteControl();
        try {
            robotRemoteControl.moveTo(1, 14, 20);
        } catch (RobotConnectionException e) {
            throw new RuntimeException(e);
        }
        int x = 0;
        int y = 0;
        try {
            x = ((Connection) robotRemoteControl.getConnectionManager().getConnection(1)).getRobot().getX();
            y = ((Connection) robotRemoteControl.getConnectionManager().getConnection(1)).getRobot().getY();
        } catch (RobotConnectionException robotConnectionException) {
            robotConnectionException.printStackTrace();
        }
        assertEquals(14, x);
        assertEquals(20, y);
    }

    @Test(expected = RobotConnectionException.class)
    public void testExpectedRobotException() throws RobotConnectionException {
        // если запускать тесты по-отдельности - они работают
        // тут будет Exception, так как пытаемся использовать Connection для робота, которого нет (с id = 4)
        // Роботов для примера создаю в конструкторе класса RobotRemoteControl
        RobotRemoteControl robotRemoteControl = new RobotRemoteControl();
        robotRemoteControl.moveTo(4, 14, 20);
    }
}
