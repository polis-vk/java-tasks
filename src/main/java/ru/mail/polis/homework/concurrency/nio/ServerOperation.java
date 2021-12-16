package ru.mail.polis.homework.concurrency.nio;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Класс, в котором хранится вся информация о входящем запросе.
 * Его статус, полученные данные, результат и так далее
 */
public class ServerOperation {

    private final int clientPort;

    private final Map<Integer, ServerResult> results = new HashMap<>();

    private final Map<Integer, List<Operand>> calculatingResults = new HashMap<>();

    public ServerOperation(int clientPort) {
        this.clientPort = clientPort;
    }

    public int getClientPort() {
        return clientPort;
    }

    public Map<Integer, ServerResult> getResults() {
        return results;
    }

    public Map<Integer, List<Operand>> getCalculatingResults() {
        return calculatingResults;
    }

    public ServerState getState() {
        return null;
    }
}
