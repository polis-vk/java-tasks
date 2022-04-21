package ru.mail.polis.homework.io.blocking;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.io.EOFException;

/**
 * Вам нужно реализовать StructureInputStream, который умеет читать данные из файла.
 * Читать поля нужно ручками, с помощью массива байт и методов {@link #read()}, {@link #read(byte[])} и так далее
 * 3 тугрика
 */
public class StructureInputStream extends FileInputStream {

    private Structure[] structures = new Structure[0];
    private int structuresCount = 0;
    private static final int FLAGS_COUNT = 4;

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
        structuresCount++;
        if (structures.length <= structuresCount) {
            structures = Arrays.copyOf(structures, structuresCount + structuresCount / 2);
        }
        structures[structuresCount - 1] = structure;
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
        if (structures.length == structuresCount) {
            return structures;
        }
        return Arrays.copyOf(structures, structuresCount);
    }

    private ByteBuffer readBytes(int size) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(size);
        byte[] bytes = new byte[size];
        if (read(bytes) != bytes.length) {
            throw new EOFException();
        }
        buffer.put(bytes);
        buffer.flip();
        return buffer;
    }

    private long readLong() throws IOException {
        return readBytes(Long.BYTES).getLong();
    }

    private int readInt() throws IOException {
        return readBytes(Integer.BYTES).getInt();
    }

    private boolean readBoolean() throws IOException {
        return read() == 1;
    }

    private double readDouble() throws IOException {
        return readBytes(Double.BYTES).getDouble();
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
            throw new IOException();
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

    private float readFloat() throws IOException {
        return readBytes(Float.BYTES).getFloat();
    }

    private boolean[] readFlags() throws IOException {
        boolean[] flags = new boolean[FLAGS_COUNT];
        byte flagsByte = (byte) read();
        for (int i = 0; i < FLAGS_COUNT; i++) {
            flags[i] = ((flagsByte >> i) & 1) == 1;
        }
        return flags;
    }
}
