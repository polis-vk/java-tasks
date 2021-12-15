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
    private ServerState state = ServerState.LOADING;
    private final Set<Integer> receivedOperands;
    private final Integer id;
    private Double result;
    public boolean readyForCalculation;

    ServerOperation(Integer totalOperands, Integer id) {
        this.totalOperands = totalOperands;
        operands = new Operand[totalOperands];
        receivedOperands = new HashSet<>();
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public Double getResult() {
        return result;
    }

    public Double execute() {
        assert receivedOperands.size() == totalOperands;
        assert receivedOperands.size() > 0;

        Double tempResultValue = operands[0].getA();
        OperandType operation = operands[0].getOperationSecond();
        for (int i = 1; i < operands.length; i++) {
            tempResultValue += operands[i].getA();
            if (operands[i].getOperationSecond() == OperandType.EQUALS) {
                break;
            }
        }
        this.state = ServerState.CALCULATING;

        result = tempResultValue;
        return result;
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

    synchronized void setState(ServerState state) {
        this.state = state;
    }

    public ServerState getState() {
        return state;
    }
}

