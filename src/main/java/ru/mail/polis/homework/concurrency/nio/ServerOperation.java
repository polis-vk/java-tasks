package ru.mail.polis.homework.concurrency.nio;

/**
 * Класс, в котором хранится вся информация о входящем запросе.
 * Его статус, полученные данные, результат и так далее
 */
public class ServerOperation {
    private final int id;
    private ServerState state = ServerState.LOADING;

    ServerOperation(int id) {
        this.id = id;
    }

    public ServerState getState() {
        return null;
    }
}
