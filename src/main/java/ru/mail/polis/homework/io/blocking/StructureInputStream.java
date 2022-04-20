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
        structure.setFlag1(readBoolean());
        structure.setFlag2(readBoolean());
        structure.setFlag3(readBoolean());
        structure.setFlag4(readBoolean());
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
        read(bytes);
        return ByteBuffer.wrap(bytes).getInt();
    }

    private long readLong() throws IOException {
        byte[] bytes = new byte[Long.BYTES];
        read(bytes);
        return ByteBuffer.wrap(bytes).getLong();
    }

    private float readFloat() throws IOException {
        byte[] bytes = new byte[Float.BYTES];
        read(bytes);
        return ByteBuffer.wrap(bytes).getFloat();
    }

    private double readDouble() throws IOException {
        byte[] bytes = new byte[Double.BYTES];
        read(bytes);
        return ByteBuffer.wrap(bytes).getDouble();
    }

    private String readString() throws IOException {
        int length = readInt();
        if (length == -1) {
            return null;
        }
        byte[] bytes = new byte[length];
        read(bytes);
        return new String(bytes, StandardCharsets.UTF_8);
    }

    private boolean readBoolean() throws IOException {
        int value = read();
        return (value == 1);
    }

    private SubStructure readSubStructure() throws IOException {
        if (available() == 0) {
            return null;
        }

        int id = readInt();
        String name = readString();
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