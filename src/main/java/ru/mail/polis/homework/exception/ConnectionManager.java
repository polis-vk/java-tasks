package ru.mail.polis.homework.exception;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class ConnectionManager implements RobotConnectionManager {
    Map<Integer, Robot> bots = new HashMap<>();

    public ConnectionManager(List<Robot> bots) {
        for (Robot bot : bots) {
            this.bots.put(bot.getId(), bot);
        }
    }

    @Override
    public RobotConnection getConnection(int robotId) throws NoConnectionException {
        Robot bot = bots.get(robotId);
        if (bot == null) {
            throw new NoConnectionException("Invalid ID");
        }
        return new Connection(bot);
    }
}