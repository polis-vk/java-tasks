package ru.mail.polis.homework.exception;

import org.junit.Test;
import static org.junit.Assert.*;

public class RobotRemoteControlTest {

    @Test
    public void testMainRobotMethod(){
        RobotRemoteControl robotRemoteControl = new RobotRemoteControl();
        try {
            robotRemoteControl.moveTo(1, 14, 20);
        } catch (RobotConnectionException e) {
            throw new RuntimeException(e);
        }
        int x;
        int y;
        try {
            x = ((Connection) robotRemoteControl.getConnectionManager().getConnection(1)).getRobot().getX();
            y = ((Connection) robotRemoteControl.getConnectionManager().getConnection(1)).getRobot().getY();
        } catch (RobotConnectionException e) {
            throw new RuntimeException(e);
        }
        assertEquals(14, x);
        assertEquals(20, y);
    }
}
