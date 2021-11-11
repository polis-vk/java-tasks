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

    private final List<Structure> structures = new ArrayList<>();

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
        structure.setId(bytesToLong());
        structure.setName(bytesToString());
        structure.setCoeff(bytesToFloat());
        boolean[] flags = byteToFlags();
        structure.setFlag1(flags[0]);
        structure.setFlag2(flags[1]);
        structure.setFlag3(flags[2]);
        structure.setFlag4(flags[3]);
        structure.setParam((byte) read());
        structure.setSubStructures(readSubStructures());
        structures.add(structure);
        return structure;
    }

    /**
     * Метод должен вернуть все структуры, которые есть в файле.
     * Если файл уже прочитан, но возвращается полный массив.
     */
    public Structure[] readStructures() throws IOException {
        if (structures.isEmpty()) {
            while (available() != 0) {
                readStructure();
            }
        }
        return structures.toArray(new Structure[0]);
    }

    public SubStructure[] readSubStructures() throws IOException {
        int sLength = bytesToInt();
        if (sLength == -1) {
            return null;
        }
        SubStructure[] subStructures = new SubStructure[sLength];
        for (int i = 0; i < sLength; i++) {
            subStructures[i] = readSubStructure();
        }
        return subStructures;
    }

    public SubStructure readSubStructure() throws IOException {
        int id = bytesToInt();
        String name = bytesToString();
        boolean flag = byteToFlags()[0];
        double score = bytesToDouble();
        return new SubStructure(id, name == null ? Structure.UNDEFINED_STRING : name, flag, score);
    }

    public long bytesToLong() throws IOException {
        byte[] bytes = new byte[Long.BYTES];
        read(bytes);
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.put(bytes);
        buffer.flip();
        return buffer.getLong();
    }

    public double bytesToDouble() throws IOException {
        byte[] bytes = new byte[Double.BYTES];
        read(bytes);
        ByteBuffer buffer = ByteBuffer.allocate(Double.BYTES);
        buffer.put(bytes);
        buffer.flip();
        return buffer.getDouble();
    }

    public float bytesToFloat() throws IOException {
        byte[] bytes = new byte[Float.BYTES];
        read(bytes);
        ByteBuffer buffer = ByteBuffer.allocate(Float.BYTES);
        buffer.put(bytes);
        buffer.flip();
        return buffer.getFloat();
    }

    public int bytesToInt() throws IOException {
        byte[] bytes = new byte[Integer.BYTES];
        read(bytes);
        ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES);
        buffer.put(bytes);
        buffer.flip();
        return buffer.getInt();
    }

    public String bytesToString() throws IOException {
        int length = bytesToInt();
        if (length == -1) {
            return null;
        }
        byte[] bytes = new byte[length];
        read(bytes);
        return new String(bytes);
    }

    private boolean[] byteToFlags() throws IOException {
        byte flags = (byte) read();
        boolean[] bools = new boolean[4];
        for (int i = 0; i < bools.length; i++) {
            bools[i] = flags % 2 == 1;
            flags = (byte) (flags / 2);
        }
        return bools;
    }

}
