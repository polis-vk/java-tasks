package ru.mail.polis.homework.exception;

import java.util.HashMap;

public class RobotsInTheField {
    public static HashMap<Integer, Robot> realRobots = new HashMap<>();

    static {
        realRobots.put(1, (new Robot(1.0, 1.0, 1)));
        realRobots.put(2, (new Robot(1.0, 1.0, 2)));
        realRobots.put(3, (new Robot(1.0, 1.0, 3)));
        realRobots.put(4, (new Robot(1.0, 1.0, 4)));
        realRobots.put(5, (new Robot(1.0, 1.0, 5)));
    }

}
