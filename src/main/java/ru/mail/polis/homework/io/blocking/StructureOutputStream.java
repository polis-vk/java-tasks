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
    private static final int COUNT_OF_FLAGS = 4;
    private static final int NULL_ELEM_VALUE = -1;

    public StructureOutputStream(File name) throws FileNotFoundException {
        super(name);
    }

    /**
     * Метод должен записать прочитанную структуру.
     */
    public void write(Structure structure) throws IOException {
        writeLong(structure.getId());
        writeString(structure.getName());
        writeSubStructures(structure.getSubStructures());
        writeFloat((float) structure.getCoeff());
        boolean[] flags = new boolean[COUNT_OF_FLAGS];
        flags[0] = structure.isFlag1();
        flags[1] = structure.isFlag2();
        flags[2] = structure.isFlag3();
        flags[3] = structure.isFlag4();
        writeFlags(flags);
        write(structure.getParam());
        flush();
    }

    /**
     * Метод должен записать массив прочитанных структур.
     */
    public void write(Structure[] structures) throws IOException {
        if (structures == null) {
            return;
        }
        for (Structure structure : structures) {
            write(structure);
        }
    }

    private void writeSubStructures(SubStructure[] subStructures) throws IOException {
        if (subStructures == null) {
            writeInt(NULL_ELEM_VALUE);
            return;
        }
        writeInt(subStructures.length);
        for (SubStructure subStructure : subStructures) {
            writeSubStructure(subStructure);
        }
    }

    private void writeSubStructure(SubStructure subStructure) throws IOException {
        writeInt(subStructure.getId());
        writeString(subStructure.getName());
        writeBoolean(subStructure.isFlag());
        writeDouble(subStructure.getScore());
    }

    private void writeInt(int value) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES);
        buffer.putInt(value);
        write(buffer.array());
    }

    private void writeLong(long value) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(value);
        write(buffer.array());
    }

    private void writeDouble(double value) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(Double.BYTES);
        buffer.putDouble(value);
        write(buffer.array());
    }

    private void writeFloat(float value) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(Float.BYTES);
        buffer.putFloat(value);
        write(buffer.array());
    }

    private void writeString(String string) throws IOException {
        if (string == null) {
            writeInt(NULL_ELEM_VALUE);
            return;
        }
        writeInt(string.length());
        write(string.getBytes());
    }

    private void writeFlags(boolean[] flags) throws IOException {
        byte value = 0;
        for (int i = 0; i < flags.length; i++) {
            value += flags[i] ? 1 << i : 0;
        }
        write(value);
    }

    private void writeBoolean(boolean value) throws IOException {
        write(value ? 1 : 0);
    }
}
