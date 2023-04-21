package ru.mail.polis.homework;

import org.junit.Before;
import org.junit.Test;
import ru.mail.polis.homework.exception.LocalRobotConnectionManager;
import ru.mail.polis.homework.exception.Robot;
import ru.mail.polis.homework.exception.RobotRemoteControl;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ExceptionTest {
    RobotRemoteControl controller;

    @Before
    public void initialize() {
        Map<Integer, Robot> robots = new HashMap<>();
        robots.put(0, new Robot(0, 0));
        robots.put(1, new Robot(1, 1));

        LocalRobotConnectionManager manager = new LocalRobotConnectionManager(robots);
        controller = new RobotRemoteControl(manager);
    }

    @Test
    public void successfulConnectionTest() {
        try {
            assertEquals(1, controller.moveTo(1, 3, 3));
            assertEquals(1, controller.moveTo(1, 6, 6));
            assertEquals(1, controller.moveTo(1, 11, 11));
            assertEquals(1, controller.moveTo(1, 0, 0));

        } catch (Exception e) {
            throw new AssertionError();
        }
    }

    @Test
    public void failedConnectionTest() {
        try {
            controller.moveTo(2, 3, 3);
            throw new AssertionError();
        } catch (Exception e) {
            assertTrue(true); // failed connection
        }

        try {
            controller.moveTo(0, 16, 16);
            throw new AssertionError();
        } catch (Exception e) {
            assertTrue(true); // failed connection
        }
    }
}
