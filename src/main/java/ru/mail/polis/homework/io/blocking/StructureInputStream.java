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

    private Structure[] structures = new Structure[0];
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
        structure.setFlag1(readBoolean());
        structure.setFlag2(readBoolean());
        structure.setFlag3(readBoolean());
        structure.setFlag4(readBoolean());
        structure.setParam((byte) read());
        add(structure);
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
        return Arrays.copyOf(structures, size);
    }

    private void add(Structure structure) {
        float currentLength = structures.length;
        if (size >= currentLength) {
            structures = Arrays.copyOf(structures, (int) (currentLength + (Math.round(currentLength / 2)) + 1));
        }
        structures[size++] = structure;
    }

    private boolean readBoolean() throws IOException {
        return read() == 1;
    }

    private void checkValue(byte[] buffer, int size) throws IOException {
        if (read(buffer) != size) {
            throw new IllegalArgumentException();
        }
    }

    private int readInt() throws IOException {
        int size = Integer.BYTES;
        byte[] buffer = new byte[size];
        checkValue(buffer, size);
        return ByteBuffer.wrap(buffer).getInt();
    }

    private double readDouble() throws IOException {
        int size = Double.BYTES;
        byte[] buffer = new byte[size];
        checkValue(buffer, size);
        return ByteBuffer.wrap(buffer).getDouble();
    }

    private long readLong() throws IOException {
        int size = Long.BYTES;
        byte[] buffer = new byte[size];
        checkValue(buffer, size);
        return ByteBuffer.wrap(buffer).getLong();
    }

    private float readFloat() throws IOException {
        int size = Float.BYTES;
        byte[] buffer = new byte[size];
        checkValue(buffer, size);
        return ByteBuffer.wrap(buffer).getFloat();
    }

    private String readString() throws IOException {
        int length = readInt();
        if (length == -1) {
            return null;
        }
        byte[] buffer = new byte[length];
        read(buffer);
        return new String(buffer);
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
}
