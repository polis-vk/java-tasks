package ru.mail.polis.homework.concurrency.nio;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Класс, в котором хранится вся информация о входящем запросе.
 * Его статус, полученные данные, результат и так далее
 */
class ServerOperation {
    private final Map<Integer, TreeMap<Integer, List<Operand>>> operationOrderResults = new TreeMap<>();
    private final Map<Integer, ReentrantLock> receivingCalculationLocks = new ConcurrentHashMap<>();
    private final AtomicInteger receivedOperands = new AtomicInteger(0);
    private volatile ServerState serverState = ServerState.LOADING;
    private volatile int totalOperands;
    private volatile int answerPort;

    public int getTotalOperands() {
        return totalOperands;
    }

    public void setTotalOperands(int totalOperands) {
        this.totalOperands = totalOperands;
    }

    public int getAnswerPort() {
        return answerPort;
    }

    public void setAnswerPort(int answerPort) {
        this.answerPort = answerPort;
    }

    public void addReceivedOperands(int operandsNumber) {
        receivedOperands.updateAndGet(operand -> operand + operandsNumber);
    }

    public int getReceivedOperands() {
        return receivedOperands.get();
    }

    public Map<Integer, TreeMap<Integer, List<Operand>>> getOperationOrderResults() {
        return operationOrderResults;
    }

    public Map<Integer, ReentrantLock> getReceivingCalculationLocks() {
        return receivingCalculationLocks;
    }

    public ServerState getServerState() {
        return serverState;
    }

    public void setServerState(ServerState serverState) {
        this.serverState = serverState;
    }
}
