package ru.mail.polis.homework.concurrency.nio;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;


// Считает не учитывая вес бинарных операторов, то есть a + b * c = (a + b) * c, а не a + (b * c)
// Без умножения и деления будет работать стандартно
// Но я думаю, что в данной работе это не так важно
public class OperandsParser {

    private static final Map<OperandType, UnaryOperator<Double>> unaryOperandsMap = new HashMap<>();
    private static final Map<OperandType, BinaryOperator<Double>> binaryOperandsMap = new HashMap<>();

    static {
        unaryOperandsMap.put(OperandType.SIN, Math::sin);
        unaryOperandsMap.put(OperandType.COS, Math::cos);
        unaryOperandsMap.put(OperandType.TAN, Math::tan);
        unaryOperandsMap.put(OperandType.EXP, Math::exp);
        unaryOperandsMap.put(OperandType.ABS, Math::abs);
        unaryOperandsMap.put(OperandType.SQUARE, Math::sqrt);
        unaryOperandsMap.put(OperandType.LN, Math::log);
        unaryOperandsMap.put(OperandType.EMPTY, UnaryOperator.identity());

        binaryOperandsMap.put(OperandType.MINUS, (a, b) -> a - b);
        binaryOperandsMap.put(OperandType.PLUS, (a, b) -> a + b);
        binaryOperandsMap.put(OperandType.MULT, (a, b) -> a * b);
        binaryOperandsMap.put(OperandType.DIVIDE, (a, b) -> a / b);
    }

    public static double parseAndCalculate(List<Operand> operands) {
        Operand currentOperand = operands.get(0);
        double result = calculateUnaryPart(currentOperand);
        if (isEqualsOperand(currentOperand) && operands.size() == 1) {
            return result;
        }
        Operand nextOperand = null;
        OperandType currentOperandSecond = currentOperand.getOperationSecond();
        for (int i = 1; i < operands.size(); i++) {
            nextOperand = operands.get(i);
            result = binaryOperandsMap.get(currentOperandSecond).apply(
                    result,
                    calculateUnaryPart(nextOperand)
            );
            currentOperandSecond = nextOperand.getOperationSecond();
        }
        return (nextOperand != null && isEqualsOperand(nextOperand)) ? result : Double.NaN;
    }

    private static double calculateUnaryPart(Operand operand) {
        return unaryOperandsMap.get(operand.getOperationFirst()).apply(operand.getA());
    }

    private static boolean isEqualsOperand(Operand operand) {
        return operand.getOperationSecond().equals(OperandType.EQUALS);
    }
}
