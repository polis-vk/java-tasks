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

    private Structure[] structures;
    private final List<Structure> list = new ArrayList<>();;
    private int index = 0;

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
        structure.setCoeff(readFloat());
        boolean[] flags = read4Booleans();
        structure.setFlag1(flags[3]);
        structure.setFlag2(flags[2]);
        structure.setFlag3(flags[1]);
        structure.setFlag4(flags[0]);
        structure.setParam((byte) readInt());
        structure.setSubStructures(readSubStructures());
        list.add(structure);
        return structure;
    }

    private long readLong() throws IOException {
        byte[] data = new byte[Long.BYTES];
        if (read(data) == -1) {
            return Structure.UNDEFINED_LONG;
        }
        return ByteBuffer.allocate(data.length).put(data).flip().getLong();
    }

    private double readDouble() throws IOException {
        byte[] data = new byte[Double.BYTES];
        if (read(data) == -1) {
            return Structure.UNDEFINED_DOUBLE;
        }
        return ByteBuffer.allocate(data.length).put(data).flip().getDouble();
    }

    private float readFloat() throws IOException {
        byte[] data = new byte[Float.BYTES];
        if (read(data) == -1) {
            return Structure.UNDEFINED_FLOAT;
        }
        return ByteBuffer.allocate(data.length).put(data).flip().getFloat();
    }

    private String readString() throws IOException {
        int length = readInt();
        if (length == -1) {
            return null;
        }
        byte[] bytes = new byte[length];
        read(bytes);
        return new String(bytes);

    }

    private int readInt() throws IOException {
        byte[] data = new byte[Integer.BYTES];
        if (read(data) == -1) {
            return Structure.UNDEFINED_INT;
        }
        return ByteBuffer.allocate(data.length).put(data).flip().getInt();
    }

    private boolean[] read4Booleans() throws IOException {
        byte flags = (byte) read();
        boolean[] result = new boolean[4];
        for (int i = 0; i < 4; i++) {
            result[i] = (flags & 1) == 1;
            flags >>= 1;
        }
        return result;
    }

    private SubStructure readSubStructure() throws IOException {
        int id = readInt();
        String name = readString();
        double score = readDouble();
        boolean flag = read() == 1;
        return new SubStructure(id, name, flag, score);
    }

    private SubStructure[] readSubStructures() throws IOException {
        if (available() == 0) {
            return null;
        }
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
        return list.toArray(new Structure[0]);
    }
}
