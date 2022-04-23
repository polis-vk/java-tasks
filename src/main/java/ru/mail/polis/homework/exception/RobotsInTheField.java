package ru.mail.polis.homework.exception;

import java.util.HashMap;

public class RobotsInTheField {
    public static HashMap<Integer, Robot> realRobots = new HashMap<>();

    static {
        realRobots.put(1, (new Robot(1.0, 1.0, 11)));
        realRobots.put(2, (new Robot(1.0, 1.0, 22)));
        realRobots.put(3, (new Robot(1.0, 1.0, 33)));
        realRobots.put(4, (new Robot(1.0, 1.0, 44)));
        realRobots.put(5, (new Robot(1.0, 1.0, 55)));
    }

}
