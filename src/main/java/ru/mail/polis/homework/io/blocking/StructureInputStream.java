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

    private final List<Structure> structureList = new ArrayList<>();

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
        setFlags(structure);
        structure.setParam(readByte());
        structureList.add(structure);
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
        return structureList.toArray(new Structure[0]);
    }

    private SubStructure readSubStructure() throws IOException {
        int id = readInt();
        String name = readString();
        if (name == null) {
            throw new IllegalArgumentException();
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
        int value = read();
        if (value == -1) {
            throw new EOFException();
        }
        return (byte) value;
    }

    private int readInt() throws IOException {
        byte[] b = new byte[4];
        read(b);
        return ByteBuffer.wrap(b).getInt();

    }

    private long readLong() throws IOException {
        byte[] b = new byte[8];
        read(b);
        return ByteBuffer.wrap(b).getLong();
    }

    private float readFloat() throws IOException {
        byte[] b = new byte[4];
        read(b);
        return ByteBuffer.wrap(b).getFloat();
    }

    private double readDouble() throws IOException {
        byte[] b = new byte[8];
        read(b);
        return ByteBuffer.wrap(b).getDouble();
    }

    private String readString() throws IOException {
        int size = readInt();
        if (size < 0) {
            return null;
        }
        byte[] b = new byte[size];
        read(b);
        return new String(b);
    }

    private void setFlags(Structure structure) throws IOException {
        int ch = read();
        if (ch < 0) {
            throw new EOFException("Can't set flags");
        }
        boolean[] flags = new boolean[4];
        for (int i = 0; i < 4; i++) {
            flags[i] = ((ch >> i) & 1) == 1;
        }
        structure.setFlag1(flags[0]);
        structure.setFlag2(flags[1]);
        structure.setFlag3(flags[2]);
        structure.setFlag4(flags[3]);
    }

}

