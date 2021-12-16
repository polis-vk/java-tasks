package ru.mail.polis.homework.concurrency.nio;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

/**
 * Класс, в котором хранится вся информация о входящем запросе.
 * Его статус, полученные данные, результат и так далее
 */
public class ServerOperation {
    private static final Map<OperandType, UnaryOperator<Double>> unaryOperands = new HashMap<>();
    private static final Map<OperandType, BinaryOperator<Double>> binaryOperands = new HashMap<>();

    static {
        unaryOperands.put(OperandType.ABS, Math::abs);
        unaryOperands.put(OperandType.TAN, Math::tan);
        unaryOperands.put(OperandType.SIN, Math::sin);
        unaryOperands.put(OperandType.COS, Math::cos);
        unaryOperands.put(OperandType.LN, Math::log);
        unaryOperands.put(OperandType.EXP, Math::exp);
        unaryOperands.put(OperandType.SQUARE, (x) -> x * x);


        binaryOperands.put(OperandType.MINUS, (a, b) -> a - b);
        binaryOperands.put(OperandType.PLUS, (a, b) -> a + b);
        binaryOperands.put(OperandType.DIVIDE, (a, b) -> a / b);
        binaryOperands.put(OperandType.MULT, (a, b) -> a * b);
    }

    private final Integer totalOperands;
    private Operand[] operands;
    private ServerState state = ServerState.LOADING;
    private final Set<Integer> receivedOperands;
    private final Integer id;
    private Double result;

    public ServerOperation(Integer totalOperands, Integer id) {
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

        this.state = ServerState.CALCULATING;

        Double result = 0.0;
        OperandType linkOperation = OperandType.PLUS;
        for (int i = 0; i < operands.length; i++) {
            Double tempResultValue = operands[i].getA();
            OperandType operation = operands[i].getOperationFirst();
            if (operation != null) {
                tempResultValue = unaryOperands.get(operation).apply(tempResultValue);
            }
            result = binaryOperands.get(linkOperation).apply(result, tempResultValue);
            linkOperation = operands[i].getOperationSecond();
            if (linkOperation == OperandType.EQUALS) {
                break;
            }
        }

        this.result = result;
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

    synchronized public void setState(ServerState state) {
        this.state = state;
    }

    public ServerState getState() {
        return state;
    }
}

