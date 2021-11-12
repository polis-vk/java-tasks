package ru.mail.polis.homework.io.blocking;

import java.io.*;
import java.nio.charset.StandardCharsets;

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
     * Метод должен записать прочитанную структуру.
     */
    public void write(Structure structure) throws IOException {
        writeLong(structure.getId());
        writeNullableString(structure.getName());
        writeNullableSubstructures(structure.getSubStructures());
        writeFloat(structure.getCoeff());
        int bits = 0;
        bits |= cast(structure.isFlag4());
        bits <<= 1;
        bits |= cast(structure.isFlag3());
        bits <<= 1;
        bits |= cast(structure.isFlag2());
        bits <<= 1;
        bits |= cast(structure.isFlag1());
        write(bits);
        writeByte(structure.getParam());
    }

    /**
     * Метод должен вернуть записать массив прочитанных структур.
     */
    public void write(Structure[] structures) throws IOException {
        for (Structure structure : structures) {
            write(structure);
        }
    }

    private void writeLong(long value) throws IOException {
        long copied = value;
        for (int i = 0; i < Long.BYTES; i++) {
            write((int) (copied & 255));
            copied >>= 8;
        }
    }

    private void writeNullableString(String value) throws IOException {
        writeBoolean(value != null);
        if (value != null) {
            writeString(value);
        }
    }

    private void writeNullableSubstructures(SubStructure[] values) throws IOException {
        writeBoolean(values != null);
        if (values != null) {
            writeSubstructures(values);
        }
    }

    private void writeSubstructures(SubStructure[] values) throws IOException {
        writeInt(values.length);
        for (SubStructure value : values) {
            writeNullableSubstructure(value);
        }
    }

    private void writeNullableSubstructure(SubStructure value) throws IOException {
        writeBoolean(value != null);
        if (value != null) {
            writeSubstructure(value);
        }
    }

    private void writeSubstructure(SubStructure value) throws IOException {
        writeInt(value.getId());
        writeString(value.getName());
        writeBoolean(value.isFlag());
        writeDouble(value.getScore());
    }

    private void writeInt(int value) throws IOException {
        int copied = value;
        for (int i = 0; i < Integer.BYTES; i++) {
            write(copied & 255);
            copied >>= 8;
        }
    }

    private void writeString(String value) throws IOException {
        byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
        writeInt(bytes.length);
        write(bytes);
    }

    private void writeDouble(double value) throws IOException {
        writeLong(Double.doubleToLongBits(value));
    }

    private void writeFloat(float value) throws IOException {
        writeInt(Float.floatToIntBits(value));
    }

    private void writeBoolean(boolean value) throws IOException {
        write(cast(value));
    }

    private void writeByte(byte value) throws IOException {
        write(value + 128);
    }

    private int cast(boolean value) {
        return value ? 1 : 0;
    }
}
