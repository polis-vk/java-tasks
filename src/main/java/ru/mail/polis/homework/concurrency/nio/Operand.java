package ru.mail.polis.homework.concurrency.nio;

/**
 * Операнд для вычислений.
 * Пример:
 * 10 + 23 - 123123 + sin(123) / |-123213| + tan(123.94) * e^3 / ln(25) =
 * Op(10, PLUS), Op(23, MINUS), Op(123123, PLUS), Op(SIN, 123, DIVIDE), Op(ABS, -123213, PLUS),
 * Op(TAN, 123.94, MULT), Op(EXP, 3, DIVIDE), Op(LN, 25, EQUALS)
 */
public class Operand {
    private final OperandType operationFirst;
    private final double a;
    private final OperandType operationSecond;

    public Operand(double a, OperandType operationSecond) {
        this(null, a, operationSecond);
    }

    public Operand(OperandType operationFirst, double a, OperandType operationSecond) {
        this.operationFirst = operationFirst;
        this.a = a;
        this.operationSecond = operationSecond;
    }

    public OperandType getOperationFirst() {
        return operationFirst;
    }

    public double getA() {
        return a;
    }

    public OperandType getOperationSecond() {
        return operationSecond;
    }
}
