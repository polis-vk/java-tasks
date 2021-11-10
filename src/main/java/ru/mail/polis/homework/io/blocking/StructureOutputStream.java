package ru.mail.polis.homework.io.blocking;

import java.io.*;

/**
 * Вам нужно реализовать StructureOutputStream, который умеет писать данные в файл.
 * Писать поля нужно ручками, с помощью массива байт и методов {@link #write(int)}, {@link #write(byte[])} и так далее
 * 3 балла
 */
public class StructureOutputStream extends FileOutputStream {

    protected int written;

    public StructureOutputStream(File name) throws FileNotFoundException {
        super(name);
    }

    /**
     * Метод должен вернуть записать прочитанную структуру.
     */
    public void write(Structure structure) throws IOException {
        this.writeLong(structure.getId());
        this.writeUTF(structure.getName());
        this.write(structure.getSubStructures());
        this.writeFloat(structure.getCoeff());
        byte k = getBitsFlag(structure.isFlag1(), structure.isFlag2(), structure.isFlag3(), structure.isFlag4());
        incCount(1);
        this.writeByte(k);
        this.writeByte(structure.getParam());
    }

    private byte getBitsFlag(boolean flag1, boolean flag2, boolean flag3, boolean flag4) {
        return (byte) (
                (boolToByte(flag1) << 3)
                        + (boolToByte(flag2) << 2)
                        + (boolToByte(flag3) << 1)
                        + (boolToByte(flag4))
        );
    }

    private byte boolToByte(boolean b) {
        return (byte) (b ? 1 : 0);
    }

    /**
     * Метод должен вернуть записать массив прочитанных структур.
     */
    public void write(Structure[] structures) throws IOException {
        for (Structure structure : structures) {
            this.write(structure);
        }
    }

    public void write(SubStructure subStructure) throws IOException {
        writeInt(subStructure.getId());
        writeUTF(subStructure.getName());
        writeBoolean(subStructure.isFlag());
        writeDouble(subStructure.getScore());
    }

    public void write(SubStructure[] subStructures) throws IOException {
        if (subStructures == null) {
            writeInt(-1);
            return;
        }
        writeInt(subStructures.length);
        for (SubStructure subStructure : subStructures) {
            write(subStructure);
        }
    }

    private void incCount(int value) {
        int temp = written + value;
        if (temp < 0) {
            temp = Integer.MAX_VALUE;
        }
        written = temp;
    }

    public final void writeBoolean(boolean v) throws IOException {
        write(v ? 1 : 0);
        incCount(1);
    }

    public final void writeByte(int v) throws IOException {
        write(v);
        incCount(1);
    }

    private final byte[] writeBuffer = new byte[8];

    public final void writeInt(int v) throws IOException {
        writeBuffer[0] = (byte) (v >>> 24);
        writeBuffer[1] = (byte) (v >>> 16);
        writeBuffer[2] = (byte) (v >>> 8);
        writeBuffer[3] = (byte) (v);
        super.write(writeBuffer, 0, 4);
        incCount(4);
    }

    public final void writeLong(long v) throws IOException {
        writeBuffer[0] = (byte) (v >>> 56);
        writeBuffer[1] = (byte) (v >>> 48);
        writeBuffer[2] = (byte) (v >>> 40);
        writeBuffer[3] = (byte) (v >>> 32);
        writeBuffer[4] = (byte) (v >>> 24);
        writeBuffer[5] = (byte) (v >>> 16);
        writeBuffer[6] = (byte) (v >>> 8);
        writeBuffer[7] = (byte) (v);
        super.write(writeBuffer, 0, 8);
        incCount(8);
    }

    public final void writeFloat(float v) throws IOException {
        writeInt(Float.floatToIntBits(v));
    }

    public final void writeDouble(double v) throws IOException {
        writeLong(Double.doubleToLongBits(v));
    }

    public void writeUTF(String str) throws IOException {
        if (str == null) {
            writeInt(-1);
            return;
        }
        writeInt(str.length());
        write(str.getBytes());
        incCount(str.getBytes().length);
    }

    public final int size() {
        return written;
    }
}
