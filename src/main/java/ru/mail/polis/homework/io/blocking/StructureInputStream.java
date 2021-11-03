package ru.mail.polis.homework.io.blocking;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

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
        if (available() == 0) {
            return null;
        }

        Structure current = new Structure.Builder()
                .setId(readLong())
                .setName(readString())
                .setSubStructures(readSubStructures())
                .setCoeff(readDouble())
                .setFlags(readBooleans())
                .setParam(readByte())
                .build();
        structures.add(current);
        return current;
    }

    /**
     * Метод должен вернуть все структуры, которые есть в файле.
     * Если файл уже прочитан, то возвращается полный массив.
     */
    public Structure[] readStructures() throws IOException {
        while (available() != 0) {
            readStructure();
        }

        Structure[] result = new Structure[structures.size()];
        structures.toArray(result);
        return result;
    }

    private long readLong() throws IOException {
        byte[] buffer = new byte[8];
        if (read(buffer) == -1) {
            throw new EOFException();
        }

        ByteBuffer changer = ByteBuffer.allocate(Long.BYTES);
        return changer.put(buffer).flip().getLong();
    }

    private double readDouble() throws IOException {
        return Double.longBitsToDouble(readLong());
    }

    private int readInt() throws IOException {
        byte[] buffer = new byte[4];
        if (read(buffer) == -1) {
            throw new EOFException();
        }

        ByteBuffer changer = ByteBuffer.allocate(Integer.BYTES);
        return changer.put(buffer).flip().getInt();
    }

    private boolean[] readBooleans() throws IOException {
        boolean[] result = new boolean[4];
        for (int i = 0; i < result.length; i++) {
            result[i] = read() == 1;
        }
        return result;
    }

    private byte readByte() throws IOException {
        int value = read();
        if (value == -1) {
            throw new EOFException();
        }
        return (byte) value;
    }

    private String readString() throws IOException {
        int length;
        if ((length = readInt()) == -1) {
            throw new EOFException();
        } else if (length == 0) {
            return null;
        }

        byte[] buffer = new byte[length];
        if (read(buffer) == -1) {
            throw new EOFException();
        }
        return new String(buffer);
    }

    private SubStructure[] readSubStructures() throws IOException {
        int count;
        if ((count = readInt()) == -1) {
            throw new EOFException();
        } else if (count == 0) {
            return null;
        }

        SubStructure[] result = new SubStructure[count];
        for (int i = 0; i < count; i++) {
            result[i] = readSubStructure();
        }
        return result;
    }

    private SubStructure readSubStructure() throws IOException {
        int id = readInt();
        String name = readString();
        boolean flag = read() == 1;
        double score = readDouble();
        return new SubStructure(id, name, flag, score);
    }
}
