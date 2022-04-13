package ru.mail.polis.homework.io.blocking;

import java.util.Arrays;
import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.io.IOException;
import java.io.EOFException;
import java.io.FileNotFoundException;

/**
 * Вам нужно реализовать StructureInputStream, который умеет читать данные из файла.
 * Читать поля нужно ручками, с помощью массива байт и методов {@link #read()}, {@link #read(byte[])} и так далее
 * 3 тугрика
 */
public class StructureInputStream extends FileInputStream {

    private Structure[] structures;
    private int structuresCount;

    public StructureInputStream(File fileName) throws FileNotFoundException {
        super(fileName);
        structures = new Structure[0];
        structuresCount = 0;
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
        structure.setFlag1(readBoolean());
        structure.setFlag2(readBoolean());
        structure.setFlag3(readBoolean());
        structure.setFlag4(readBoolean());
        structure.setParam((byte) read());
        addStructure(structure);
        return structure;
    }

    /**
     * Метод должен вернуть все структуры, которые есть в файле.
     * Если файл уже прочитан, но возвращается полный массив.
     *
     * @return
     */
    public Structure[] readStructures() throws IOException {
        while (available() != 0) {
            readStructure();
        }

        if (structures.length != structuresCount) {
            structures = Arrays.copyOf(structures, structuresCount);
        }
        return structures;
    }

    private void readFully(byte[] b, int length) throws IOException {
        if (length < 0) {
            throw new IndexOutOfBoundsException();
        }
        int n = 0;
        while (n < length) {
            int count = read(b, n, length - n);
            if (count < 0) {
                throw new EOFException();
            }
            n += count;
        }
    }

    private boolean readBoolean() throws IOException {
        return read() == 1;
    }

    private int readInt() throws IOException {
        byte[] readBuffer = new byte[4];
        readFully(readBuffer, 4);
        return ByteBuffer.wrap(readBuffer).getInt();
    }

    private float readFloat() throws IOException {
        byte[] readBuffer = new byte[4];
        readFully(readBuffer, 4);
        return ByteBuffer.wrap(readBuffer).getFloat();
    }

    private long readLong() throws IOException {
        byte[] readBuffer = new byte[8];
        readFully(readBuffer, 8);
        return ByteBuffer.wrap(readBuffer).getLong();
    }

    private double readDouble() throws IOException {
        byte[] readBuffer = new byte[8];
        readFully(readBuffer, 8);
        return ByteBuffer.wrap(readBuffer).getDouble();
    }

    private String readString() throws IOException {
        int length = readInt();
        if (length == -1) {
            return null;
        }

        byte[] readBuffer = new byte[length];
        readFully(readBuffer, length);
        return new String(readBuffer);
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
        int length = readInt();
        if (length == -1) {
            return null;
        }

        SubStructure[] subStructures = new SubStructure[length];
        for (int i = 0; i < length; i++) {
            subStructures[i] = readSubStructure();
        }
        return subStructures;
    }

    private void addStructure(Structure structure) {
        if (structures.length <= structuresCount) {
            structures = Arrays.copyOf(structures, (structures.length + 1) * 3 / 2);
        }
        structures[structuresCount++] = structure;
    }
}
