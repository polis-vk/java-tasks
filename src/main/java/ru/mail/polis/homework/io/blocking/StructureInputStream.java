package ru.mail.polis.homework.io.blocking;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

/**
 * Вам нужно реализовать StructureInputStream, который умеет читать данные из файла.
 * Читать поля нужно ручками, с помощью массива байт и методов {@link #read()}, {@link #read(byte[])} и так далее
 * 3 тугрика
 */
public class StructureInputStream extends FileInputStream {

    private Structure[] structures;

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
        structure.setFlag1(readFlag());
        structure.setFlag2(readFlag());
        structure.setFlag3(readFlag());
        structure.setFlag4(readFlag());
        structure.setParam((byte) read());

        structures = Arrays.copyOf(structures, structures.length + 1);
        structures[structures.length - 1] = structure;
        return structure;
    }

    /**
     * Метод должен вернуть все структуры, которые есть в файле.
     * Если файл уже прочитан, но возвращается полный массив.
     */
    public Structure[] readStructures() throws IOException {
        return new Structure[0];
    }

    private long readLong() throws IOException {
        byte[] bytes = new byte[8];
        int temp = read(bytes);
        if (temp != 8) {
            throw new IOException();
        }
        return ((bytes[0] & 0xFFL) << 56)
                | ((bytes[1] & 0xFFL) << 48)
                | ((bytes[2] & 0xFFL) << 40)
                | ((bytes[3] & 0xFFL) << 32)
                | ((bytes[4] & 0xFFL) << 24)
                | ((bytes[5] & 0xFFL) << 16)
                | ((bytes[6] & 0xFFL) << 8)
                | ((bytes[7] & 0xFFL));
    }

    private int readInt() throws IOException {
        byte[] bytes = new byte[4];
        int temp = read(bytes);
        if (temp != 4) {
            throw new IOException();
        }
        return (bytes[0] << 24)
                | (bytes[1] << 16)
                | (bytes[2] << 8)
                | (bytes[3]);
    }

    private double readDouble() throws IOException {
        return Double.longBitsToDouble(readLong());
    }

    private float readFloat() throws IOException {
        return Float.intBitsToFloat(readInt());
    }

    private String readString() throws IOException {
        int length = readInt();
        if (length == -1) {
            throw new IOException();
        }
        byte[] bytes = new byte[length];
        int temp = read(bytes);
        if (temp != length) {
            throw new IOException();
        }
        return new String(bytes);
    }

    private SubStructure[] readSubStructures() throws IOException {
        int size = readInt();
        if (size == -1) {
            throw new IOException();
        }
        SubStructure[] subStructures = new SubStructure[size];
        for (int i = 0; i < size; i++) {
            subStructures[i] = readSubStructure();
        }
        return subStructures;
    }

    private SubStructure readSubStructure() throws IOException {
        int id = readInt();
        String name = readString();
        boolean flag = readFlag();
        double score = readDouble();
        return new SubStructure(id, name, flag, score);
    }

    private boolean readFlag() throws IOException {
        return readInt() != 0;
    }
}
