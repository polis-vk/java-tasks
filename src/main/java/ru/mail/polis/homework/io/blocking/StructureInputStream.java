package ru.mail.polis.homework.io.blocking;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * Вам нужно реализовать StructureInputStream, который умеет читать данные из файла.
 * Читать поля нужно ручками, с помощью массива байт и методов {@link #read()}, {@link #read(byte[])} и так далее
 * 3 тугрика
 */
public class StructureInputStream extends FileInputStream {

    private Structure[] structures = new Structure[1];
    private int size = 0;

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
        byte tempFlag = (byte) read();
        byte mask = 0b0000001;
        structure.setFlag1((tempFlag & (mask << 3)) != 0);
        structure.setFlag2((tempFlag & (mask << 2)) != 0);
        structure.setFlag3((tempFlag & (mask << 1)) != 0);
        structure.setFlag4((tempFlag & mask) != 0);
        structure.setParam((byte) read());

        add(structure);

        return structure;
    }

    private void add(Structure structure) {
        if (size == structures.length) {
            structures = Arrays.copyOf(structures, (int) (structures.length + Math.round(structures.length / 2.)));
        }

        structures[size] = structure;
        size++;
    }

    private long readLong() throws IOException {
        byte[] bytes = new byte[Long.BYTES];

        if (read(bytes) != Long.BYTES) {
            throw new IllegalArgumentException();
        }

        return ByteBuffer.wrap(bytes).getLong();
    }

    private int readInt() throws IOException {
        byte[] bytes = new byte[Integer.BYTES];

        if (read(bytes) != Integer.BYTES) {
            throw new IllegalArgumentException();
        }

        return ByteBuffer.wrap(bytes).getInt();
    }

    private double readDouble() throws IOException {
        byte[] bytes = new byte[Double.BYTES];

        if (read(bytes) != Double.BYTES) {
            throw new IllegalArgumentException();
        }

        return ByteBuffer.wrap(bytes).getDouble();
    }

    private float readFloat() throws IOException {
        byte[] bytes = new byte[Float.BYTES];

        if (read(bytes) != Float.BYTES) {
            throw new IllegalArgumentException();
        }

        return ByteBuffer.wrap(bytes).getFloat();
    }

    private String readString() throws IOException {
        int length = readInt();
        if (length == -1) {
            return null;
        }

        byte[] bytes = new byte[length];

        if (read(bytes) != length) {
            throw new IllegalArgumentException();
        }

        return new String(bytes);
    }

    private boolean readBoolean() throws IOException {
        return read() == 1;
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

    /**
     * Метод должен вернуть все структуры, которые есть в файле.
     * Если файл уже прочитан, но возвращается полный массив.
     */
    public Structure[] readStructures() throws IOException {
        while (available() != 0) {
            readStructure();
        }

        if (size == 0) {
            return new Structure[0];
        }

        if (structures.length == size) {
            return structures;
        }

        return Arrays.copyOfRange(structures, 0, size);
    }
}
