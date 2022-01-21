package ru.mail.polis.homework.io.blocking;

import java.io.*;
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
        writeLong(structure.getId());
        writeString(structure.getName());
        writeSubStructures(structure.getSubStructures());
        writeFloat((float) structure.getCoeff());
        writeFlags(structure);
        write(structure.getParam());
        flush();
    }

    /**
     * Метод должен вернуть записать массив прочитанных структур.
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
            writeInt(-1);
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

    //https://stackoverflow.com/questions/4485128/how-do-i-convert-long-to-byte-and-back-in-java

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
            writeInt(-1);
            return;
        }
        writeInt(string.length());
        write(string.getBytes());
    }

    private void writeBoolean(boolean value) throws IOException {
        write(value ? 1 : 0);
    }

    private void writeFlags(Structure structure) throws IOException {
        byte value = 0;
        boolean[] flags = new boolean[4];
        flags[0] = structure.isFlag1();
        flags[1] = structure.isFlag2();
        flags[2] = structure.isFlag3();
        flags[3] = structure.isFlag4();
        for (int i = 0; i < flags.length; i++) {
            value += flags[i] ? 1 << i : 0;
        }
        write(value);
    }
}
