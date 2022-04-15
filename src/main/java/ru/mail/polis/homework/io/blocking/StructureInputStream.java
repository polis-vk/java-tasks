package ru.mail.polis.homework.io.blocking;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * Вам нужно реализовать StructureInputStream, который умеет читать данные из файла.
 * Читать поля нужно ручками, с помощью массива байт и методов {@link #read()}, {@link #read(byte[])} и так далее
 * 3 тугрика
 */
public class StructureInputStream extends FileInputStream {

    private Structure[] structures;
    private int count = 0;

    public StructureInputStream(File fileName) throws FileNotFoundException {
        super(fileName);
        structures = new Structure[0];
        count = 0;
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
        structure.setId(readForLong());
        structure.setName(readForString());
        structure.setSubStructures(readSubStructures());
        structure.setCoeff((float) readForDouble());
        structure.setFlag1(readForBoolean());
        structure.setFlag2(readForBoolean());
        structure.setFlag3(readForBoolean());
        structure.setFlag4(readForBoolean());
        structure.setParam((byte) read());
        addStructure(structure);
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
        if (structures.length != count) {
            structures = Arrays.copyOf(structures, count);
        }
        return structures;
    }

    private void read(byte[] b, int length) throws IOException {
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

    private boolean readForBoolean() throws IOException {
        return read() == 1;
    }

    private long readForLong() throws IOException {
        byte[] readBuffer = new byte[Long.BYTES];
        read(readBuffer, Long.BYTES);
        return ByteBuffer.wrap(readBuffer).getLong();
    }

    private double readForDouble() throws IOException {
        byte[] readBuffer = new byte[Double.BYTES];
        read(readBuffer, Double.BYTES);
        return ByteBuffer.wrap(readBuffer).getDouble();
    }

    private String readForString() throws IOException {
        int length = (int) readForLong();
        if (length == -1) {
            return null;
        }
        byte[] readBuffer = new byte[length];
        read(readBuffer, length);
        return new String(readBuffer);
    }

    private SubStructure readSubStructure() throws IOException {
        int id = (int) readForLong();
        String name = readForString();
        if (name == null) {
            throw new IllegalArgumentException();
        }
        boolean flag = readForBoolean();
        double score = readForDouble();
        return new SubStructure(id, name, flag, score);
    }

    private SubStructure[] readSubStructures() throws IOException {
        int length = (int) readForLong();
        if (length == -1) {
            return null;
        }

        SubStructure[] subStructureList = new SubStructure[length];
        for (int i = 0; i < length; i++) {
            subStructureList[i] = readSubStructure();
        }
        return subStructureList;
    }

    private void addStructure(Structure structure) {
        if (structures.length <= count) {
            structures = Arrays.copyOf(structures, (structures.length + 1) * 3 / 2);
        }
        structures[count++] = structure;
    }
}
