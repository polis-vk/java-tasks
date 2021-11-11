package ru.mail.polis.homework.io.blocking;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Вам нужно реализовать StructureInputStream, который умеет читать данные из файла.
 * Читать поля нужно ручками, с помощью массива байт и методов {@link #read()}, {@link #read(byte[])} и так далее
 * 3 балла
 */
public class StructureInputStream extends FileInputStream {

    private final List<Structure> structs = new ArrayList<>();

    public StructureInputStream(File fileName) throws FileNotFoundException {
        super(fileName);
    }

    /**
     * Метод должен вернуть следующую прочитанную структуру.
     * Если структур в файле больше нет, то вернуть null
     */
    public Structure readStructure() {
        Structure struct = null;
        try {
            if (available() != 0) {
                struct = new Structure();
                boolean[] flags = readFlags();
                struct.setFlag1(flags[0]);
                struct.setFlag2(flags[1]);
                struct.setFlag3(flags[2]);
                struct.setFlag4(flags[3]);
                struct.setId(readLong());
                struct.setName(readString());
                struct.setSubStructures(readSubStructures());
                struct.setCoeff(readFloat());
                struct.setParam((byte)read());
                structs.add(struct);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return struct;
    }

    /**
     * Метод должен вернуть все структуры, которые есть в файле.
     * Если файл уже прочитан, но возвращается полный массив.
     */
    public Structure[] readStructures() {
        try {
            while (available() != 0) {
                readStructure();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return structs.toArray(new Structure[0]);
    }

    private SubStructure readSubStructure() throws IOException {
        return new SubStructure(readInt(), readString(), read() == 1, readDouble());
    }

    private SubStructure[] readSubStructures() throws IOException {
        int size = readInt();
        if (size == -1) {
            return null;
        }
        SubStructure[] subStructs = new SubStructure[size];
        for (int i = 0; i < size; ++i) {
            subStructs[i] = readSubStructure();
        }
        return subStructs;
    }

    private int readInt() throws IOException {
        byte[] bytes = new byte[Integer.BYTES];
        read(bytes);
        return ByteBuffer.allocate(bytes.length).put(bytes).flip().getInt();
    }

    private long readLong() throws IOException {
        byte[] bytes = new byte[Long.BYTES];
        read(bytes);
        return ByteBuffer.allocate(bytes.length).put(bytes).flip().getLong();
    }

    private float readFloat() throws IOException {
        return Float.intBitsToFloat(readInt());
    }

    private double readDouble() throws IOException {
        return Double.longBitsToDouble(readLong());
    }

    private String readString() throws IOException {
        int length = readInt();
        if (length == -1) {
            return null;
        }
        byte[] bytes = new byte[length];
        read(bytes);
        return new String(bytes);
    }

    private boolean[] readFlags() throws IOException {
        int byteFlags = read(); // 0b0000_XXXX
        boolean[] boolFlags = new boolean[4];
        for (int i = 0; i < 4; ++i) {
            int mask = 1 << i;
            boolFlags[3 - i] = (mask & byteFlags) != 0;
        }
        return boolFlags;
    }

}