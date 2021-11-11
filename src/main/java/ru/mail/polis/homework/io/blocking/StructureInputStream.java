package ru.mail.polis.homework.io.blocking;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * Вам нужно реализовать StructureInputStream, который умеет читать данные из файла.
 * Читать поля нужно ручками, с помощью массива байт и методов {@link #read()}, {@link #read(byte[])} и так далее
 * 3 балла
 */
public class StructureInputStream extends FileInputStream {

    private static final int NULL_OBJECT = -1;

    private Structure[] structures;
    private int size;

    public StructureInputStream(File fileName) throws FileNotFoundException {
        super(fileName);
        structures = new Structure[0];
        size = 0;
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
        structure.setSubStructures(readSubstructureArray());
        structure.setCoeff(readFloat());
        boolean[] flags = readFlags();
        structure.setFlag1(flags[0]);
        structure.setFlag2(flags[1]);
        structure.setFlag3(flags[2]);
        structure.setFlag4(flags[3]);
        structure.setParam(readByte());
        if (size == structures.length) {
            structures = Arrays.copyOf(structures, size * 2 + 1);
        }
        structures[size++] = structure;
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
        return Arrays.copyOf(structures, size);
    }

    private int readInteger() throws IOException {
        byte[] bytes = new byte[Integer.BYTES];
        read(bytes);
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        return byteBuffer.getInt();
    }

    private long readLong() throws IOException {
        byte[] bytes = new byte[Long.BYTES];
        read(bytes);
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        return byteBuffer.getLong();
    }

    private float readFloat() throws IOException {
        byte[] bytes = new byte[Float.BYTES];
        read(bytes);
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        return byteBuffer.getFloat();
    }

    private double readDouble() throws IOException {
        byte[] bytes = new byte[Double.BYTES];
        read(bytes);
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        return byteBuffer.getDouble();
    }

    private String readString() throws IOException {
        int len = readInteger();
        if (len == NULL_OBJECT) {
            return null;
        }
        byte[] byteStr = new byte[len];
        read(byteStr);
        return new String(byteStr, StandardCharsets.UTF_8);
    }

    private boolean readBoolean() throws IOException {
        return read() == 1;
    }

    private boolean[] readFlags() throws IOException {
        byte b = readByte();
        boolean[] flags = new boolean[4];
        flags[0] = (b & 1) == 1;
        flags[1] = (b >> 1 & 1) == 1;
        flags[2] = (b >> 2 & 1) == 1;
        flags[3] = (b >> 3 & 1) == 1;
        return flags;
    }

    private byte readByte() throws IOException {
        byte[] bool = new byte[1];
        read(bool);
        return bool[0];
    }

    private SubStructure readSubstructure() throws IOException {
        int id = readInteger();
        String name = readString();
        boolean flag = readBoolean();
        double score = readDouble();
        return new SubStructure(id, name == null ? Structure.UNDEFINED_STRING : name, flag, score);
    }

    private SubStructure[] readSubstructureArray() throws IOException {
        int len = readInteger();
        if (len == NULL_OBJECT) {
            return null;
        }
        SubStructure[] subStructures = new SubStructure[len];
        for (int i = 0; i < len; i++) {
            subStructures[i] = readSubstructure();
        }
        return subStructures;
    }
}
