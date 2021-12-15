package ru.mail.polis.homework.concurrency.nio;

import java.util.HashSet;
import java.util.Set;

/**
 * Класс, в котором хранится вся информация о входящем запросе.
 * Его статус, полученные данные, результат и так далее
 */
public class ServerOperation {
    private final Integer totalOperands;
    private Operand[] operands;
    public ServerState state = ServerState.LOADING;
    private final Set<Integer> receivedOperands;
    private final Integer id;
    public boolean readyForCalculation;

    ServerOperation(Integer totalOperands, Integer id) {
        this.totalOperands = totalOperands;
        operands = new Operand[totalOperands];
        receivedOperands = new HashSet<>();
        this.id = id;
    }

    synchronized public boolean AddOperand(Operand operand, Integer order) {
        if (receivedOperands.contains(order)) {
            return false;
        } else {
            receivedOperands.add(order);
            operands[order] = operand;
            if (receivedOperands.size() == totalOperands) {
                this.state = ServerState.WAITING_CALCULATE;
            }
            return true;
        }
    }

    public ServerState getState() {
        return state;
    }
}

