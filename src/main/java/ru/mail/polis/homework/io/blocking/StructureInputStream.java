package ru.mail.polis.homework.io.blocking;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Вам нужно реализовать StructureInputStream, который умеет читать данные из файла.
 * Читать поля нужно ручками, с помощью массива байт и методов {@link #read()}, {@link #read(byte[])} и так далее
 * 3 балла
 */
public class StructureInputStream extends FileInputStream {

    private List<Structure> structures = new ArrayList<>();

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
        structure.setCoeff(readFloat());
        boolean[] flags = readFlags();
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
        while (available() != 0) {
            readStructure();
        }
        return structures.toArray(new Structure[0]);
    }

    private long readLong() throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        byte[] bytes = new byte[Long.BYTES];
        read(bytes);
        buffer.put(bytes);
        buffer.flip();
        return buffer.getLong();
    }

    private float readFloat() throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(Float.BYTES);
        byte[] bytes = new byte[Float.BYTES];
        read(bytes);
        buffer.put(bytes);
        buffer.flip();
        return buffer.getFloat();
    }

    private boolean[] readFlags() throws IOException {
        boolean[] flags = new boolean[4];
        int b = read();
        for (int i = 0; i < 4; i++) {
            flags[i] = b % 2 == 1;
            b /= 2;
        }
        return flags;
    }

    private int readInt() throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES);
        byte[] bytes = new byte[Integer.BYTES];
        read(bytes);
        buffer.put(bytes);
        buffer.flip();
        return buffer.getInt();
    }

    private String readString() throws IOException {
        int size = readInt();
        if (size == -1) {
            return null;
        }
        byte[] bytes = new byte[size];
        read(bytes);
        return new String(bytes);
    }

    private double readDouble() throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(Double.BYTES);
        byte[] bytes = new byte[Double.BYTES];
        read(bytes);
        buffer.put(bytes);
        buffer.flip();
        return buffer.getDouble();
    }

    private SubStructure readSubStructure() throws IOException {
        int id = readInt();
        String name = readString();
        boolean flag = read() == 1;
        double score = readDouble();
        return new SubStructure(id, name, flag, score);
    }

    private SubStructure[] readSubstructures() throws IOException {
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
