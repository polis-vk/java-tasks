package ru.mail.polis.homework.io.blocking;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * Вам нужно реализовать StructureInputStream, который умеет читать данные из файла.
 * Читать поля нужно ручками, с помощью массива байт и методов {@link #read()}, {@link #read(byte[])} и так далее
 * 3 тугрика
 */
public class StructureInputStream extends FileInputStream {

    private Structure[] structures = new Structure[0];
    int size = 0;

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
        structure.setSubStructures(readSubStructureArray());
        structure.setCoeff(readFloat());
        boolean[] flags = readFlags();
        structure.setFlag1(flags[0]);
        structure.setFlag2(flags[1]);
        structure.setFlag3(flags[2]);
        structure.setFlag4(flags[3]);
        structure.setParam((byte) read());

        addStructure(structure);
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
        return Arrays.copyOf(structures, size);
    }

    private void addStructure(Structure structure) {
        if (size == structures.length) {
            structures = Arrays.copyOf(structures, structures.length * 2 + 1);
        }
        structures[size] = structure;
        size++;
    }

    private int readInt() throws IOException {
        byte[] bytes = new byte[Integer.BYTES];
        if (read(bytes) != Integer.BYTES) {
            throw new IOException();
        }
        return ByteBuffer.wrap(bytes).getInt();
    }

    private long readLong() throws IOException {
        byte[] bytes = new byte[Long.BYTES];
        if (read(bytes) != Long.BYTES) {
            throw new IOException();
        }
        return ByteBuffer.wrap(bytes).getLong();
    }

    private float readFloat() throws IOException {
        byte[] bytes = new byte[Float.BYTES];
        if (read(bytes) != Float.BYTES) {
            throw new IOException();
        }
        return ByteBuffer.wrap(bytes).getFloat();
    }

    private double readDouble() throws IOException {
        byte[] bytes = new byte[Double.BYTES];
        if (read(bytes) != Double.BYTES) {
            throw new IOException();
        }
        return ByteBuffer.wrap(bytes).getDouble();
    }

    private String readString() throws IOException {
        int length = readInt();
        if (length == -1) {
            return null;
        }
        byte[] bytes = new byte[length];
        if (read(bytes) != length) {
            throw new IOException();
        }
        return new String(bytes, StandardCharsets.UTF_8);
    }

    private boolean[] readFlags() throws IOException {
        byte b = (byte) read();
        boolean[] flags = new boolean[4];
        flags[0] = (b & 1) == 1;
        flags[1] = (b >> 1 & 1) == 1;
        flags[2] = (b >> 2 & 1) == 1;
        flags[3] = (b >> 3 & 1) == 1;
        return flags;
    }

    private boolean readBoolean() throws IOException {
        return read() == 1;
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

    private SubStructure[] readSubStructureArray() throws IOException {
        int length = readInt();
        if (length == -1) {
            return null;
        }
        SubStructure[] array = new SubStructure[length];
        for (int i = 0; i < length; i++) {
            array[i] = readSubStructure();
        }
        return array;
    }
}
