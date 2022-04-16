package ru.mail.polis.homework.io.blocking;

import java.io.*;
import java.util.Arrays;

/**
 * Вам нужно реализовать StructureInputStream, который умеет читать данные из файла.
 * Читать поля нужно ручками, с помощью массива байт и методов {@link #read()}, {@link #read(byte[])} и так далее
 * 3 тугрика
 */
public class StructureInputStream extends FileInputStream {

    private Structure[] structures = new Structure[0];

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
        structure.setFlag1(readBool());
        structure.setFlag2(readBool());
        structure.setFlag3(readBool());
        structure.setFlag4(readBool());
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
        Structure structure = readStructure();
        while (structure != null) {
            structure = readStructure();
        }
        return structures;
    }

    private int readInt() throws IOException {
        byte[] data = new byte[4];
        int buff = read(data);
        if (buff != 4) {
            throw new IOException();
        }
        return ((data[0] & 0xff) << 24)
                | ((data[1] & 0xff) << 16)
                | ((data[2] & 0xff) << 8)
                | ((data[3] & 0xff) << 0);
    }

    private long readLong() throws IOException {
        byte[] data = new byte[8];
        int buff = read(data);
        if (buff != 8) {
            throw new IOException();
        }
        return ((data[0] & 0xffl) << 56)
                | ((data[1] & 0xffl) << 48)
                | ((data[2] & 0xffl) << 40)
                | ((data[3] & 0xffl) << 32)
                | ((data[4] & 0xffl) << 24)
                | ((data[5] & 0xffl) << 16)
                | ((data[6] & 0xffl) << 8)
                | ((data[7] & 0xffl) << 0);
    }

    private double readDouble() throws IOException {
        return Double.longBitsToDouble(readLong());
    }

    private float readFloat() throws IOException {
        return Float.intBitsToFloat(readInt());
    }

    private String readString() throws IOException {
        int length = readInt();
        if (length != -1) {
            byte[] data = new byte[length];
            int buff = read(data);
            if (buff != length) {
                throw new IOException();
            }
            return new String(data);
        }
        return null;
    }

    private SubStructure[] readSubStructures() throws IOException {
        int size = readInt();
        if (size != -1) {
            SubStructure[] subStructures = new SubStructure[size];
            for (int i = 0; i < size; i++) {
                subStructures[i] = readSubStructure();
            }
            return subStructures;
        }
        return null;
    }

    private SubStructure readSubStructure() throws IOException {
        int id = readInt();
        String line = readString();
        boolean flag = readBool();
        double count = readDouble();
        return new SubStructure(id, line, flag, count);
    }

    private boolean readBool() throws IOException {
        return readInt() != 0 ? true : false;
    }
}
