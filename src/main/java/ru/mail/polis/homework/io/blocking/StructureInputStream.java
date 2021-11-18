package ru.mail.polis.homework.io.blocking;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Вам нужно реализовать StructureInputStream, который умеет читать данные из файла.
 * Читать поля нужно ручками, с помощью массива байт и методов {@link #read()}, {@link #read(byte[])} и так далее
 * 3 балла
 */
public class StructureInputStream extends FileInputStream {

    private final ArrayList<Structure> structures = new ArrayList<>();

    public StructureInputStream(File fileName) throws FileNotFoundException {
        super(fileName);
    }


    /**
     * Метод должен вернуть следующую прочитанную структуру.
     * Если структур в файле больше нет, то вернуть null
     */
    public Structure readStructure() throws IOException {
        Structure structure = new Structure();
        long id;
        String name;
        float coeff;
        byte param;
        int flags;
        SubStructure[] subStructures;
        try {
            id = readLong();
            name = readString();
            coeff = readFloat();
            param = readByte();
            flags = readByte();
            int subLength = readInt();
            if (subLength == -1) {
                subStructures = null;
            } else {
                subStructures = new SubStructure[subLength];
                for (int i = 0; i < subStructures.length; i++) {
                    subStructures[i] = readSubStructure();
                }
            }
        } catch (EOFException e) {
            return null;
        }
        structure.setId(id);
        structure.setName(name);
        structure.setCoeff(coeff);
        structure.setParam(param);
        structure.setFlag1((flags & (1 << 3)) != 0);
        structure.setFlag2((flags & (1 << 2)) != 0);
        structure.setFlag3((flags & (1 << 1)) != 0);
        structure.setFlag4((flags & 1) != 0);
        structure.setSubStructures(subStructures);
        structures.add(structure);
        return structure;
    }

    /**
     * Метод должен вернуть все структуры, которые есть в файле.
     * Если файл уже прочитан, но возвращается полный массив.
     */
    public Structure[] readStructures() throws IOException {
        while (readStructure() != null) {

        }
        return structures.toArray(new Structure[0]);
    }

    private long readLong() throws IOException {
        byte[] buffer = new byte[Long.SIZE / 8];
        if (read(buffer) == -1) {
            throw new EOFException();
        }
        return ByteBuffer.wrap(buffer).getLong();
    }

    private int readInt() throws IOException {
        byte[] buffer = new byte[Integer.SIZE / 8];
        if (read(buffer) == -1) {
            throw new EOFException();
        }
        return ByteBuffer.wrap(buffer).getInt();
    }

    private float readFloat() throws IOException {
        byte[] buffer = new byte[Float.SIZE / 8];
        if (read(buffer) == -1) {
            throw new EOFException();
        }
        return ByteBuffer.wrap(buffer).getFloat();
    }

    private double readDouble() throws IOException {
        byte[] buffer = new byte[Double.SIZE / 8];
        if (read(buffer) == -1) {
            throw new EOFException();
        }
        return ByteBuffer.wrap(buffer).getDouble();
    }

    private byte readByte() throws IOException {
        int result = read();
        if (result == -1) {
            throw new EOFException();
        }
        return (byte) result;
    }

    private String readString() throws IOException {
        int size = readInt();
        if (size == -1) {
            return null;
        }
        byte[] buffer = new byte[size];
        if (read(buffer) == -1) {
            throw new EOFException();
        }
        return new String(buffer, StandardCharsets.UTF_8);
    }

    private SubStructure readSubStructure() throws IOException {
        int id = readInt();
        String name = readString();
        boolean flag = (readByte() == 1);
        double score = readDouble();
        return new SubStructure(id, name, flag, score);
    }
}
