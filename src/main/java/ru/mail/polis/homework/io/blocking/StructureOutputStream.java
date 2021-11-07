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
        writeDouble(structure.getCoeff());
        writeBoolean(structure.isFlag1());
        writeBoolean(structure.isFlag2());
        writeBoolean(structure.isFlag3());
        writeBoolean(structure.isFlag4());
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

    private void writeBoolean(boolean value) throws IOException {
        write(value ? 1 : 0);
    }

    private void writeByte(byte value) throws IOException {
        write(value);
    }

    private void writeInt(int value) throws IOException {
        byte[] buffer = new byte[] {
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

    private void writeDouble(double value)  throws IOException {
        writeLong(Double.doubleToLongBits(value));
    }

    private void writeChar(char value) throws IOException {
        byte[] buffer = new byte[] {
                (byte) (value >>> 8),
                (byte) value
        };
        write(buffer);
    }

    private void writeString(String value) throws IOException {
        if (value == null) {
            writeInt(-1);
            return;
        }
        writeInt(value.length());
        for (int i = 0; i < value.length(); ++i) {
            writeChar(value.charAt(i));
        }
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
        for (SubStructure subStructure: value) {
            writeSubStructure(subStructure);
        }
    }
}
