package ru.mail.polis.homework.io.blocking;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.ArrayList;

/**
 * Вам нужно реализовать StructureInputStream, который умеет читать данные из файла.
 * Читать поля нужно ручками, с помощью массива байт и методов {@link #read()}, {@link #read(byte[])} и так далее
 * 3 балла
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
        if (available() <= 0) {
            return null;
        }

        Structure structure = new Structure();
        structure.setId(readLong());
        structure.setName(readString());
        structure.setSubStructures(readSubStructures());
        structure.setCoeff(readFloat());
        setFlags(structure, readFlags());
        structure.setParam(readByte());
        structures.add(structure);

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

        return structures.toArray(new Structure[0]);
    }

    private byte readByte() throws IOException {
        return (byte) read();
    }

    private int readInt() throws IOException {
        byte[] bytes = new byte[4];
        read(bytes);
        return ByteBuffer.wrap(bytes).getInt();
    }

    private long readLong() throws IOException {
        byte[] bytes = new byte[8];
        read(bytes);
        return ByteBuffer.wrap(bytes).getLong();
    }

    private float readFloat() throws IOException {
        byte[] bytes = new byte[4];
        read(bytes);
        return ByteBuffer.wrap(bytes).getFloat();
    }

    private double readDouble() throws IOException {
        byte[] bytes = new byte[8];
        read(bytes);
        return ByteBuffer.wrap(bytes).getDouble();
    }

    private String readString() throws IOException {
        int len = readInt();
        if (len < 0) {
            return null;
        }

        byte[] byteStr = new byte[len];
        read(byteStr);
        return new String(byteStr);
    }

    private boolean readBoolean() throws IOException {
        return read() == 1;
    }

    private boolean[] readFlags() throws IOException {
        boolean[] flags = new boolean[4];

        for (int i = 0; i < 4; i++) {
            flags[i] = readBoolean();
        }

        return flags;
    }

    private SubStructure[] readSubStructures() throws IOException {
        int len = readInt();
        if (len == -1) {
            return null;
        }

        SubStructure[] subStructures = new SubStructure[len];
        for (int i = 0; i < len; i++) {
            int id = readInt();
            String name = readString();
            boolean flag = readBoolean();
            double score = readDouble();

            subStructures[i] = new SubStructure(id, name == null ? Structure.UNDEFINED_STRING : name, flag, score);
        }
        return subStructures;
    }

    private void setFlags(Structure struct, boolean[] flags) {
        struct.setFlag1(flags[0]);
        struct.setFlag2(flags[1]);
        struct.setFlag3(flags[2]);
        struct.setFlag4(flags[3]);
    }
}
