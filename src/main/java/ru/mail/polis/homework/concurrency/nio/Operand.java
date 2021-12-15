package ru.mail.polis.homework.concurrency.nio;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Операнд для вычислений.
 * Пример:
 * 10 + 23 - 123123 + sin(123) / |-123213| + tan(123.94) * e^3 / ln(25) =
 * Op(10, PLUS), Op(23, MINUS), Op(123123, PLUS), Op(SIN, 123, DIVIDE), Op(ABS, -123213, PLUS),
 * Op(TAN, 123.94, MULT), Op(EXP, 3, DIVIDE), Op(LN, 25, EQUALS)
 */
public class Operand implements Serializable {
    public static final int SIZE_OF_INT = 4;
    public static final int SIZE_OF_DOUBLE = 8;

    private final OperandType operationFirst;
    private final double a;

    // NotNull
    private final OperandType operationSecond;

    public Operand(double a, OperandType operationSecond) {
        this(null, a, operationSecond);
    }

    public Operand(OperandType operationFirst, double a, OperandType operationSecond) {
        this.operationFirst = operationFirst;
        this.a = a;
        this.operationSecond = operationSecond;
    }

    public Operand(ByteBuffer bb) {
        this.operationFirst = OperandType.values()[bb.getInt()];
        this.a = bb.getDouble();
        this.operationSecond = OperandType.values()[bb.getInt()];
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

    public void writeIntoBuffer(ByteBuffer bb) {
        bb.putInt(operationFirst.ordinal());
        bb.putDouble(a);
        bb.putInt(operationSecond.ordinal());
    }

    public byte[] getCharacterRepresentation() {
        byte[] result = new byte[SIZE_OF_INT + SIZE_OF_DOUBLE + SIZE_OF_INT];
        if (operationFirst != null) {
            writeIntToBuffer(operationFirst.ordinal(), result, 0);
        } else {
            writeIntToBuffer(-1, result, 0);
        }
        writeDoubleToBuffer(a, result, SIZE_OF_INT);
        writeIntToBuffer(operationSecond.ordinal(), result, SIZE_OF_INT + SIZE_OF_DOUBLE);
        return result;
    }

    private static void writeIntToBuffer(int data, byte[] buffer, int offset) {
        for (int i = 0; i < SIZE_OF_INT; ++i) {
            buffer[offset + i] = (byte) ((data >> (SIZE_OF_INT - 1 - i) * 8) & 0xff);
        }
    }

    private static void writeDoubleToBuffer(double data, byte[] buffer, int offset) {
        long longRepresentation = Double.doubleToRawLongBits(data);
        for (int i = 0; i < SIZE_OF_DOUBLE; ++i) {
            buffer[offset + i] = (byte) ((longRepresentation >> (SIZE_OF_DOUBLE - 1 - i) * 8) & 0xff);
        }
    }
}
