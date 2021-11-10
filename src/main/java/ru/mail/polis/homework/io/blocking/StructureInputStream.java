package ru.mail.polis.homework.io.blocking;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Вам нужно реализовать StructureInputStream, который умеет читать данные из файла.
 * Читать поля нужно ручками, с помощью массива байт и методов {@link #read()}, {@link #read(byte[])} и так далее
 * 3 балла
 */
public class StructureInputStream extends FileInputStream {

    private final List<Structure> structures = new ArrayList<>();

    public StructureInputStream(File fileName) throws FileNotFoundException {
        super(fileName);
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
        while (available() > 0) {
            readStructure();
        }
        return structures.toArray(new Structure[0]);
    }

    private SubStructure readSubStructure() throws IOException {
        int id = readInt();
        String name = readString();
        if (name == null) {
            throw new IOException();
        }
        boolean flag = readBoolean();
        double score = readDouble();
        return new SubStructure(id, name, flag, score);
    }

    private SubStructure[] readSubStructures() throws IOException {
        int size = readInt();
        if (size == -1) {
            return null;
        }
        SubStructure[] subStructures = new SubStructure[size];
        for (int i = 0; i < size; i++) {
            subStructures[i] = readSubStructure();
        }
        return subStructures;
    }

    private boolean readBoolean() throws IOException {
        return read() == 1;
    }

    private byte readByte() throws IOException {
        int ch = super.read();
        if (ch < 0) {
            throw new EOFException();
        }
        return (byte) ch;
    }

    private int readInt() throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        byte[] bytes = new byte[4];
        if (read(bytes) != bytes.length) {
            throw new EOFException();
        }
        buffer.put(bytes);
        buffer.flip();
        return buffer.getInt();
    }

    private long readLong() throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        byte[] bytes = new byte[8];
        if (read(bytes) != bytes.length) {
            throw new EOFException();
        }
        buffer.put(bytes);
        buffer.flip();
        return buffer.getLong();
    }

    private float readFloat() throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        byte[] bytes = new byte[4];
        if (read(bytes) != bytes.length) {
            throw new EOFException();
        }
        buffer.put(bytes);
        buffer.flip();
        return buffer.getFloat();
    }

    private double readDouble() throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        byte[] bytes = new byte[8];
        if (read(bytes) != bytes.length) {
            throw new EOFException();
        }
        buffer.put(bytes);
        buffer.flip();
        return buffer.getDouble();
    }

    private String readString() throws IOException {
        int length = readInt();
        if (length == -1) {
            return null;
        }
        byte[] bytes = new byte[length];
        if (read(bytes) != length) {
            throw new EOFException();
        }
        return new String(bytes);
    }

    private boolean[] readFlags() throws IOException {
        boolean[] flags = new boolean[4];
        int inputFlags = read();
        for (int i = 0; i < 4; i++) {
            flags[i] = ((inputFlags >> i) & 1) == 1;
        }
        return flags;
    }
}