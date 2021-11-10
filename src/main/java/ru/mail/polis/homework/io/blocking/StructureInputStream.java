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
 * 3 балла
 */
public class StructureInputStream extends FileInputStream {
    private static final int COUNT_OF_FLAGS = 4;
    private static final int NULL_ELEM_VALUE = -1;

    private List<Structure> structureList = new ArrayList<>();

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
        structure.setParam(readByte());
        structureList.add(structure);
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
        return structureList.toArray(new Structure[0]);
    }

    private SubStructure readSubStructure() throws IOException {
        int id = readInt();
        String name = readString();
        if(name == null){
            throw new IllegalAccessError();
        }
        boolean flag = readBoolean();
        double score = readDouble();
        return new SubStructure(id, name, flag, score);
    }

    private SubStructure[] readSubStructures() throws IOException {
        int size = readInt();
        if (size == NULL_ELEM_VALUE) {
            return null;
        }
        SubStructure[] subStructures = new SubStructure[size];
        for (int i = 0; i < size; i++) {
            subStructures[i] = readSubStructure();
        }
        return subStructures;
    }

    private boolean readBoolean() throws IOException {
        return read() == 1;
    }

    private byte readByte() throws IOException {
        int value = read();
        if (value == -1) {
            throw new EOFException();
        }
        return (byte) value;
    }

    private int readInt() throws IOException {
        return abstractReadVariable(Integer.BYTES).getInt();
    }

    private long readLong() throws IOException {
        return abstractReadVariable(Long.BYTES).getLong();
    }

    private float readFloat() throws IOException {
        return abstractReadVariable(Float.BYTES).getFloat();
    }

    private double readDouble() throws IOException {
        return abstractReadVariable(Double.BYTES).getDouble();
    }

    private ByteBuffer abstractReadVariable(int byteSize) throws IOException {
        byte[] bytes = new byte[byteSize];
        if (read(bytes) != bytes.length) {
            throw new EOFException();
        }
        ByteBuffer buffer = ByteBuffer.allocate(byteSize);
        return buffer.put(bytes).flip();
    }

    private String readString() throws IOException {
        int length = readInt();
        if (length == NULL_ELEM_VALUE) {
            return null;
        }
        byte[] bytes = new byte[length];
        if (read(bytes) != length) {
            throw new EOFException();
        }
        return new String(bytes);
    }

    private boolean[] readFlags() throws IOException {
        int flagValue = read();
        boolean[] flags = new boolean[COUNT_OF_FLAGS];
        for (int i = 0; i < COUNT_OF_FLAGS; i++) {
            flags[i] = ((flagValue >> i) & 1) == 1;
        }
        return flags;
    }
}
