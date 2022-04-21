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
 * 3 тугрика
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
        boolean[] flags = readFlags();
        structure.setFlag1(flags[0]);
        structure.setFlag2(flags[1]);
        structure.setFlag3(flags[2]);
        structure.setFlag4(flags[3]);
        structure.setParam((byte) read());
        structureList.add(structure);
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
        return structureList.toArray(new Structure[0]);
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


    private int readInt() throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES);
        byte[] bytes = new byte[Integer.BYTES];
        read(bytes);
        buffer.put(bytes);
        buffer.flip();
        return buffer.getInt();
    }

    private double readDouble() throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(Double.BYTES);
        byte[] bytes = new byte[Double.BYTES];
        read(bytes);
        buffer.put(bytes);
        buffer.flip();
        return buffer.getDouble();
    }

    private boolean[] readFlags() throws IOException {
        boolean[] flags = new boolean[4];
        byte bytes = (byte) read();
        for (int i = 0; i < 4; i++) {
            flags[i] = bytes % 2 == 1;
            bytes /= 2;
        }
        return flags;
    }

    private String readString() throws IOException {
        int len = readInt();
        if (len == -1) {
            return null;
        }
        byte[] bytes = new byte[len];
        read(bytes);
        return new String(bytes);
    }

    private SubStructure readSubStructure() throws IOException {
        int id = readInt();
        String name = readString();
        boolean flag = read() == 1;
        double score = readDouble();
        return new SubStructure(id, name, flag, score);
    }

    private SubStructure[] readSubStructures() throws IOException {
        int len = readInt();
        if (len == -1) {
            return null;
        }
        SubStructure[] subStructures = new SubStructure[len];
        for (int i = 0; i < len; i++) {
            subStructures[i] = readSubStructure();
        }
        return subStructures;
    }
}
