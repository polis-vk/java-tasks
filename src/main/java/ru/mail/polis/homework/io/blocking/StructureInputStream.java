package ru.mail.polis.homework.io.blocking;

import java.io.EOFException;
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
        structure.setSubStructures(readSubStructures());
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
        return new Structure[0];
    }

    private ByteBuffer readValue(int size) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES);
        byte[] bytes = new byte[size];
        if (read(bytes) != bytes.length) {
            throw new EOFException();
        }
        ByteBuffer buf = ByteBuffer.allocate(Long.BYTES);
        return buf.put(bytes).flip();
    }

    private long readLong() throws IOException {
        return readValue(Long.BYTES).getLong();
    }

    private boolean readBoolean() throws IOException {
        return read() == 1;
    }

    private int readInt() throws IOException {
        return readValue(Integer.BYTES).getInt();
    }

    private float readFloat() throws IOException {
        return readValue(Float.BYTES).getFloat();
    }

    private double readDouble() throws IOException {
        return readValue(Double.BYTES).getDouble();
    }

    private String readString() throws IOException {
        int length = readInt();
        if (length == -1) {
            return null;
        }
        byte[] bytes = new byte[length];
        if (read(bytes) != length) {
            throw new EOFException();
        }
        return new String(bytes);
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

    private boolean[] readFlags() throws IOException {
        boolean[] flags = new boolean[4];
        byte bytes = (byte) read();
        for (int i = flags.length - 1; i >= 0; i--) {
            flags[i] = (bytes & (1 << i)) != 0;
        }
        return flags;
    }
}
