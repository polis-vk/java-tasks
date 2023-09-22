package ru.mail.polis.homework.exception;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RobotRemoteConnectionManager implements RobotConnectionManager{
    List<Robot> robots = new LinkedList<>();
    RobotRemoteConnectionManager(List<Robot> collection) {
        this.robots = collection;
    }

    @Override
    public RobotConnection getConnection(int robotId) throws RobotConnectionException {
        List<Robot> robotStream = robots.stream().filter(x -> x.getID()==robotId).limit(1).toList();
        return new RobotRemoteConnection(robotStream.get(0));
        }
    }
