package ru.mail.polis.homework.io.blocking;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

/**
 * Вам нужно реализовать StructureInputStream, который умеет читать данные из файла.
 * Читать поля нужно ручками, с помощью массива байт и методов {@link #read()}, {@link #read(byte[])} и так далее
 * 3 балла
 */
public class StructureInputStream extends FileInputStream {

    private final ArrayList<Structure> structures;

    public StructureInputStream(File fileName) throws FileNotFoundException {
        super(fileName);
        this.structures = new ArrayList<>();
    }

    /**
     * Метод должен вернуть следующую прочитанную структуру.
     * Если структур в файле больше нет, то вернуть null
     */
    public Structure readStructure() throws IOException {
        if (available() == 0) {
            return null;
        }
        Structure structure = new Structure();
        structure.setId(readLong());
        structure.setName(readString());
        structure.setSubStructures(readSubStructures());
        structure.setCoeff(readFloat());
        boolean[] flags = readFlags();
        structure.setFlag1(flags[0]);
        structure.setFlag2(flags[1]);
        structure.setFlag3(flags[2]);
        structure.setFlag4(flags[3]);
        structure.setParam(readByte());
        structures.add(structure);
        return structure;
    }

    /**
     * Метод должен вернуть все структуры, которые есть в файле.
     * Если файл уже прочитан, но возвращается полный массив.
     */
    public Structure[] readStructures() throws IOException {
        while (available() != 0) {
            readStructure();
        }
        return structures.toArray(new Structure[0]);
    }

    private boolean[] readFlags() throws IOException {
        int flag = read();
        if (flag < 0) {
            throw new EOFException();
        }
        boolean[] flags = new boolean[4];
        for (int i = 0, j = 3; i < 4; ++i, --j) {
            flags[i] = (byte) (flag >> j) % 2 == 1;
        }
        return flags;
    }

    private boolean readBoolean() throws IOException {
        int buffer = read();
        if (buffer < 0) {
            throw new EOFException();
        }
        return (buffer != 0);
    }

    private byte readByte() throws IOException {
        int buffer = read();
        if (buffer < 0) {
            throw new EOFException();
        }
        return (byte) buffer;
    }

    private int readInt() throws IOException {
        byte[] buffer = new byte[4];
        if (read(buffer) < 0) {
            throw new EOFException();
        }
        return (((buffer[0] & 255) << 24) +
                ((buffer[1] & 255) << 16) +
                ((buffer[2] & 255) << 8) +
                ((buffer[3] & 255)));
    }

    private long readLong() throws IOException {
        byte[] buffer = new byte[8];
        if (read(buffer) < 0) {
            throw new EOFException();
        }
        return (((long) (buffer[0] & 255) << 56) +
                ((long) (buffer[1] & 255) << 48) +
                ((long) (buffer[2] & 255) << 40) +
                ((long) (buffer[3] & 255) << 32) +
                ((long) (buffer[4] & 255) << 24) +
                ((long) (buffer[5] & 255) << 16) +
                ((long) (buffer[6] & 255) << 8) +
                ((long) (buffer[7] & 255)));
    }

    private float readFloat() throws IOException {
        byte[] buffer = new byte[4];
        if (read(buffer) < 0) {
            throw new EOFException();
        }
        return ByteBuffer.wrap(buffer).getFloat();
    }

    private double readDouble() throws IOException {
        byte[] buffer = new byte[8];
        if (read(buffer) < 0) {
            throw new EOFException();
        }
        return ByteBuffer.wrap(buffer).getDouble();
    }

    private String readString() throws IOException {
        int length = readInt();
        if (length < 0) {
            return null;
        }
        byte[] buffer = new byte[length];
        if (read(buffer) != length) {
            throw new IOException();
        }
        return new String(buffer);
    }

    private SubStructure readSubStructure() throws IOException {
        return new SubStructure(readInt(), readStringForSubStructure(), readBoolean(), readDouble());
    }

    private SubStructure[] readSubStructures() throws IOException {
        int length = readInt();
        if (length < 0) {
            return null;
        }
        SubStructure[] subStructures = new SubStructure[length];
        for (int i = 0; i < length; ++i) {
            subStructures[i] = readSubStructure();
        }
        return subStructures;
    }

    private String readStringForSubStructure() throws IOException {
        String string = readString();
        if (string == null) {
            throw new EOFException();
        }
        return string;
    }
}