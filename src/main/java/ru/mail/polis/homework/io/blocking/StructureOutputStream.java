package ru.mail.polis.homework.io.blocking;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Вам нужно реализовать StructureOutputStream, который умеет писать данные в файл.
 * Писать поля нужно ручками, с помощью массива байт и методов {@link #write(int)}, {@link #write(byte[])} и так далее
 * 3 балла
 */
public class StructureOutputStream extends FileOutputStream {

    public StructureOutputStream(File name) throws FileNotFoundException {
        super(name);
    }

    /**
     * Метод должен вернуть записать прочитанную структуру.
     */
    public void write(Structure structure) throws IOException {
        write(getBytesFromLong(structure.getId()));
        write(structure.getName());
        write(structure.getSubStructures());
        write(getBytesFromFloat(structure.getCoeff()));
        write(getIntFromFlags(structure.isFlag1(), structure.isFlag2(),
                structure.isFlag3(), structure.isFlag4()));
        write(structure.getParam());
    }

    /**
     * Метод должен вернуть записать массив прочитанных структур.
     */
    public void write(Structure[] structures) throws IOException {
        for (Structure structure : structures) {
            write(structure);
        }
    }

    private byte[] getBytesFromLong(long x) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(x);
        return buffer.array();
    }

    private byte[] getBytesFromFloat(float x) {
        ByteBuffer buffer = ByteBuffer.allocate(Float.BYTES);
        buffer.putFloat(x);
        return buffer.array();
    }

    private int getIntFromBoolean(boolean b) {
        return b ? 1 : 0;
    }

    private int getIntFromFlags(boolean f1, boolean f2, boolean f3, boolean f4) {
        int result = 0;
        result += getIntFromBoolean(f1) * 1 + getIntFromBoolean(f2) * 2
                + getIntFromBoolean(f3) * 4 + getIntFromBoolean(f4) * 8;
        return result;
    }

    private void write(String str) throws IOException {
        if (str == null) {
            write(getBytesFromInt(-1));
            return;
        }
        write(getBytesFromInt(str.length()));
        write(str.getBytes());
    }

    private byte[] getBytesFromInt(int x) {
        ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES);
        buffer.putInt(x);
        return buffer.array();
    }

    private byte[] getBytesFromDouble(double x) {
        ByteBuffer buffer = ByteBuffer.allocate(Double.BYTES);
        buffer.putDouble(x);
        return buffer.array();
    }

    public void write(SubStructure subStructure) throws IOException {
        write(getBytesFromInt(subStructure.getId()));
        write(subStructure.getName());
        write(getIntFromBoolean(subStructure.isFlag()));
        write(getBytesFromDouble(subStructure.getScore()));
    }

    public void write(SubStructure[] subStructures) throws IOException {
        if (subStructures == null) {
            write(getBytesFromInt(-1));
            return;
        }
        write(getBytesFromInt(subStructures.length));
        for (SubStructure subStructure : subStructures) {
            write(subStructure);
        }
    }
}
