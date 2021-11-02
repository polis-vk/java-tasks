package ru.mail.polis.homework.io.blocking;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
        structure.setSubStructures(readSubstructures());
        structure.setCoeff(readDouble());
        boolean[] flags = readFlags(4);
        structure.setFlag1(flags[0]);
        structure.setFlag2(flags[1]);
        structure.setFlag3(flags[2]);
        structure.setFlag4(flags[3]);
        structure.setParam((byte) read());
        structures.add(structure);
        return structure;
    }

    /**
     * Метод должен вернуть все структуры, которые есть в файле.
     * Если файл уже прочитан, но возвращается полный массив.
     */
    public Structure[] readStructures() throws IOException {
        Structure structure;
        do {
            structure = readStructure();
        }
        while (structure != null);
        return Arrays.copyOf(structures.toArray(), structures.size(), Structure[].class);
    }

    private SubStructure[] readSubstructures() throws IOException {
        int length = readInt();
        if (length == -1) {
            return null;
        }
        SubStructure[] subStructures = new SubStructure[length];
        for (int i = 0; i < length; i++) {
            subStructures[i] = readSubStructure();
        }
        return subStructures;
    }

    private SubStructure readSubStructure() throws IOException {
        return new SubStructure(readInt(), readString(), readFlags(1)[0], readDouble());
    }

    private String readString() throws IOException {
        int length = readInt();
        if (length == -1) {
            return null;
        }
        char[] str = new char[length];
        for (int i = 0; i < length; i++) {
            int ch1 = read();
            int ch2 = read();
            if ((ch1 | ch2) < 0) {
                throw new IOException();
            }
            str[i] = (char) ((ch1 << 8) + (ch2));
        }

        return String.valueOf(str);
    }

    private long readLong() throws IOException {
        byte[] bytes = new byte[8];
        int total = read(bytes);
        if (total != 8) {
            throw new IOException();
        }
        return ((bytes[0] & 0xFFL) << 56) |
                ((bytes[1] & 0xFFL) << 48) |
                ((bytes[2] & 0xFFL) << 40) |
                ((bytes[3] & 0xFFL) << 32) |
                ((bytes[4] & 0xFFL) << 24) |
                ((bytes[5] & 0xFFL) << 16) |
                ((bytes[6] & 0xFFL) << 8) |
                ((bytes[7] & 0xFFL));
    }

    private int readInt() throws IOException {
        int byte1 = read();
        int byte2 = read();
        int byte3 = read();
        int byte4 = read();
        if ((byte1 | byte2 | byte3 | byte4) < 0) {
            throw new IOException();
        }
        return ((byte1 << 24) + (byte2 << 16) + (byte3 << 8) + (byte4));
    }

    private double readDouble() throws IOException {
        return Double.longBitsToDouble(readLong());
    }

    private boolean[] readFlags(int numberOfFlags) throws IOException {
        int flagsByte = read();
        boolean[] flags = new boolean[numberOfFlags];
        for (int i = 0; i < numberOfFlags; i++) {
            int template = 1 << numberOfFlags - 1 - i;
            flags[i] = (flagsByte & template) == template;
        }
        return flags;
    }

}
