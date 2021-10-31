package ru.mail.polis.homework.io.blocking;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Вам нужно реализовать StructureInputStream, который умеет читать данные из файла.
 * Читать поля нужно ручками, с помощью массива байт и методов {@link #read()}, {@link #read(byte[])} и так далее
 * 3 балла
 */
public class StructureInputStream extends FileInputStream {

    private Structure[] structures;

    public StructureInputStream(File fileName) throws FileNotFoundException {
        super(fileName);
        structures = new Structure[0];
    }


    /**
     * Метод должен вернуть следующую прочитанную структуру.
     * Если структур в файле больше нет, то вернуть null
     */
    public Structure readStructure() throws IOException {
        if (available() == 0) {
            return null;
        }
        Structure inputStructure = new Structure();
        inputStructure.setId(readLong());
        int sizeString = readInt();
        inputStructure.setName(readString(sizeString));
        inputStructure.setSubStructures(readSubStructures());
        inputStructure.setCoeff((float) readDouble());
        inputStructure.setFlag1(readBoolean());
        inputStructure.setFlag2(readBoolean());
        inputStructure.setFlag3(readBoolean());
        inputStructure.setFlag4(readBoolean());
        inputStructure.setParam((byte) read());
        return addElementInStructure(inputStructure);
    }

    /**
     * Метод должен вернуть все структуры, которые есть в файле.
     * Если файл уже прочитан, но возвращается полный массив.
     */
    public Structure[] readStructures() throws IOException {
        if (available() == 0) {
            return structures;
        }
        while (available() != 0) {
            readStructure();
        }
        return structures;
    }

    private SubStructure readSubStructure() throws IOException {
        if (available() == 0) {
            return null;
        }
        int id = readInt();
        int sizeName = readInt();
        String name = readString(sizeName);
        boolean flag = readBoolean();
        double score = readDouble();
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
        List<SubStructure> subStructuresList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            subStructuresList.add(readSubStructure());
        }

        return subStructuresList.toArray(new SubStructure[0]);
    }

    private long readLong() throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        byte[] bytes = new byte[Long.BYTES];
        read(bytes);
        buffer.put(bytes);
        buffer.flip();
        return buffer.getLong();
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

    private String readString(int length) throws IOException {
        if (length == -1) {
            return null;
        }
        byte[] bytes = new byte[length];
        read(bytes);
        return new String(bytes);
    }

    private boolean readBoolean() throws IOException {
        return (byte) read() == 1;
    }

    private Structure addElementInStructure(Structure structure) {
        Structure[] copyOfStructures = Arrays.copyOf(structures, structures.length);
        structures = new Structure[copyOfStructures.length + 1];
        System.arraycopy(copyOfStructures, 0, structures,
                0, copyOfStructures.length);
        structures[structures.length - 1] = structure;
        return structure;
    }
}