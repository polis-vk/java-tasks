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
        boolean[] flags = new boolean[] {
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
        write(buffer, 0, 8);
    }

    private void writeString(String value) throws IOException {
        if (value == null) {
            writeInt(-1);
            return;
        }
        writeInt(value.length());
        write(value.getBytes());
    }

    private void writeSubStructures(SubStructure[] value) throws IOException {
        if (value == null) {
            writeInt(-1);
            return;
        }
        writeInt(value.length);
        for (SubStructure subStructure: value) {
            writeSubStructure(subStructure);
        }
    }

    private void writeFloat(float value) throws IOException {
        writeInt(Float.floatToIntBits(value));
    }

    private void writeFlags(boolean[] values) throws IOException {
        write((byte) (booleanToByte(values[0])
                + (booleanToByte(values[1]) << 1)
                + (booleanToByte(values[2]) << 2)
                + (booleanToByte(values[3]) << 3)));
    }

    private byte booleanToByte(boolean b) {
        return (byte) (b ? 1 : 0);
    }

    private void writeBoolean(boolean value) throws IOException {
        write(value ? 1 : 0);
    }

    private void writeInt(int value) throws IOException {
        write(value >>> 24);
        write(value >>> 16);
        write(value >>> 8);
        write(value);
    }

    private void writeSubStructure(SubStructure value) throws IOException {
        writeInt(value.getId());
        writeSubString(value.getName());
        writeBoolean(value.isFlag());
        writeDouble(value.getScore());
    }

    private void writeSubString(String value) throws IOException {
        if (value == null) {
            throw new IllegalArgumentException();
        }
        writeInt(value.length());
        write(value.getBytes());
    }

    private void writeDouble(double value) throws IOException {
        writeLong(Double.doubleToLongBits(value));
    }
}
