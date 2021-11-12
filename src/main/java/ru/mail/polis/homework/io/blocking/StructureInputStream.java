 package ru.mail.polis.homework.io.blocking;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Вам нужно реализовать StructureInputStream, который умеет читать данные из файла.
 * Читать поля нужно ручками, с помощью массива байт и методов {@link #read()}, {@link #read(byte[])} и так далее
 * 3 балла
 */
public class StructureInputStream extends FileInputStream {

    public static final int VALUE_ERROR = -1;
    public static final int COUNT_FLAGS = 4;

    private List<Structure> structures = new ArrayList<>();

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
        structure.setSubStructures(readSubstructures());
        structure.setCoeff(readFloat());
        boolean[] flags = readFlags();
        structure.setFlag1(flags[0]);
        structure.setFlag2(flags[1]);
        structure.setFlag3(flags[2]);
        structure.setFlag4(flags[3]);
        structure.setParam((byte) read());
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

    private int readInt() throws IOException {
        byte[] bytes = new byte[Integer.BYTES];
        read(bytes);
        return ByteBuffer.wrap(bytes).getInt();
    }

    private long readLong() throws IOException {
        byte[] bytes = new byte[Long.BYTES];
        read(bytes);
        return ByteBuffer.wrap(bytes).getLong();
    }

    private float readFloat() throws IOException {
        byte[] bytes = new byte[Float.BYTES];
        read(bytes);
        return ByteBuffer.wrap(bytes).getFloat();
    }

    private double readDouble() throws IOException {
        byte[] bytes = new byte[Double.BYTES];
        read(bytes);
        return ByteBuffer.wrap(bytes).getDouble();
    }

    private boolean readBoolean() throws IOException {
        return read() == 1;
    }

    private String readString() throws IOException {
        int length = readInt();
        if (length == VALUE_ERROR) {
            return null;
        }
        byte[] bytes = new byte[length];
        read(bytes);
        return new String(bytes, StandardCharsets.UTF_8);
    }

    private boolean[] readFlags() throws IOException {
        boolean[] flags = new boolean[COUNT_FLAGS];
        byte b = (byte) read();
        IntStream.range(0, COUNT_FLAGS).forEach(i -> flags[i] = (b >> i & 1) == 1);
        return flags;
    }

    private SubStructure readSubstructure() throws IOException {
        int id = readInt();
        String name = readString();
        return new SubStructure(id, name == null ? Structure.UNDEFINED_STRING : name, readBoolean(), readDouble());
    }

    private SubStructure[] readSubstructures() throws IOException {
        int length = readInt();
        if (length == VALUE_ERROR) {
            return null;
        }
        SubStructure[] subStructures = new SubStructure[length];
        for (int i = 0; i < length; i++) {
            subStructures[i] = readSubstructure();
        }
        return subStructures;
    }
}
