package ru.mail.polis.homework.concurrency.nio;

import java.io.Serializable;

/**
 * Операнд для вычислений.
 * Пример:
 * 10 + 23 - 123123 + sin(123) / |-123213| + tan(123.94) * e^3 / ln(25) =
 * Op(10, PLUS), Op(23, MINUS), Op(123123, PLUS), Op(SIN, 123, DIVIDE), Op(ABS, -123213, PLUS),
 * Op(TAN, 123.94, MULT), Op(EXP, 3, DIVIDE), Op(LN, 25, EQUALS)
 */
public class Operand implements Serializable {
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

    public byte[] bytes() {
        byte op1 = operationFirst == null ? (byte) -4 : (byte) operationFirst.ordinal();
        byte[] num = writeDouble(a);
        byte op2 = (byte) operationSecond.ordinal();
        byte[] result = new byte[10];
        result[0] = op1;
        System.arraycopy(num, 0, result, 1, 8);
        result[9] = op2;
        return result;
    }

    private byte[] writeDouble(double d) {
        long value = Double.doubleToLongBits(d);
        return new byte[]{
                (byte) (value >>> 56),
                (byte) (value >>> 48),
                (byte) (value >>> 40),
                (byte) (value >>> 32),
                (byte) (value >>> 24),
                (byte) (value >>> 16),
                (byte) (value >>> 8),
                (byte) (value)
        };
    }

}
