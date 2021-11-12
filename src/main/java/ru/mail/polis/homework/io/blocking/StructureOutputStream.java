package ru.mail.polis.homework.io.blocking;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

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
        writeLong(structure.getId());
        writeString(structure.getName());
        writeSubStructures(structure.getSubStructures());
        writeFloat(structure.getCoeff());
        boolean[] flags = new boolean[]{
                structure.isFlag1(),
                structure.isFlag2(),
                structure.isFlag3(),
                structure.isFlag4()
        };
        writeFlags(flags);
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

    private void writeFlags(boolean[] flags) throws IOException {
        byte needToWrite = 0;
        for (int i = 0, j = 3; i < flags.length; ++i, --j) {
            needToWrite += (byte) (flags[i] ? 1 : 0) << j;
        }
        write(needToWrite);
    }

    private void writeBoolean(boolean value) throws IOException {
        write(value ? 1 : 0);
    }

    private void writeInt(int value) throws IOException {
        byte[] buffer = new byte[]{
                (byte) (value >>> 24),
                (byte) (value >>> 16),
                (byte) (value >>> 8),
                (byte) value
        };
        write(buffer);
    }

    private void writeLong(long value) throws IOException {
        byte[] buffer = new byte[]{
                (byte) (value >>> 56),
                (byte) (value >>> 48),
                (byte) (value >>> 40),
                (byte) (value >>> 32),
                (byte) (value >>> 24),
                (byte) (value >>> 16),
                (byte) (value >>> 8),
                (byte) value
        };
        write(buffer);
    }

    private void writeFloat(float value) throws IOException {
        writeInt(Float.floatToIntBits(value));
    }

    private void writeDouble(double value) throws IOException {
        writeLong(Double.doubleToLongBits(value));
    }

    private void writeString(String value) throws IOException {
        if (value == null) {
            writeInt(-1);
            return;
        }
        writeInt(value.length());
        write(value.getBytes());
    }

    private void writeSubStructure(SubStructure value) throws IOException {
        writeInt(value.getId());
        writeString(value.getName());
        writeBoolean(value.isFlag());
        writeDouble(value.getScore());
    }

    private void writeSubStructures(SubStructure[] value) throws IOException {
        if (value == null) {
            writeInt(-1);
            return;
        }
        writeInt(value.length);
        for (SubStructure subStructure : value) {
            writeSubStructure(subStructure);
        }
    }
}