package ru.mail.polis.homework.io.blocking;

import java.io.EOFException;
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

    private final List<Structure> structureList = new ArrayList<>();

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
        structure.setSubStructures(readSubStructures());
        structure.setCoeff(readFloat());
        setFlags(structure);
        structure.setParam((byte) read());

        structureList.add(structure);
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
        return structureList.toArray(new Structure[0]);
    }

    private long readLong() throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        byte[] bytes = new byte[Long.BYTES];
        if (read(bytes) != Long.BYTES) {
            throw new EOFException("Error parse long");
        }
        buffer.put(bytes);
        buffer.flip();
        return buffer.getLong();
    }

    private String readString() throws IOException {
        int length = readInt();
        if (length == -1) {
            return null;
        }
        byte[] bytes = new byte[length];
        if (read(bytes) != length) {
            throw new EOFException("Error parse string");
        }
        return new String(bytes);
    }

    private String readStringForSubStructure() throws IOException {
        String name = readString();
        if (name == null) {
            throw new EOFException("SubStructure name can't be null");
        }
        return name;
    }

    private double readDouble() throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(Double.BYTES);
        byte[] bytes = new byte[Double.BYTES];
        int read = read(bytes);
        if (read != Double.BYTES) {
            throw new EOFException("Error parse double");
        }
        buffer.put(bytes);
        buffer.flip();
        return buffer.getDouble();
    }

    private float readFloat() throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(Float.BYTES);
        byte[] bytes = new byte[Float.BYTES];
        if (read(bytes) != Float.BYTES) {
            throw new EOFException("Error parse float");
        }
        buffer.put(bytes);
        buffer.flip();
        return buffer.getFloat();
    }

    private boolean readBoolean() throws IOException {
        int ch = read();
        if (ch < 0) {
            throw new EOFException("Error parse boolean");
        }
        return ch == 1;
    }

    private void setFlags(Structure structure) throws IOException {
        int ch = read();
        if (ch < 0) {
            throw new EOFException("Error parse flags");
        }
        structure.setFlag1(byteToBoolean((byte) (ch >> 3)));
        structure.setFlag2(byteToBoolean((byte) (ch >> 2)));
        structure.setFlag3(byteToBoolean((byte) (ch >> 1)));
        structure.setFlag4(byteToBoolean((byte) ch));
    }

    private boolean byteToBoolean(byte b) {
        return (b % 2) == 1;
    }

    private int readInt() throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES);
        byte[] bytes = new byte[Integer.BYTES];
        if (read(bytes) != Integer.BYTES) {
            throw new EOFException("Error parse int");
        }
        buffer.put(bytes);
        buffer.flip();
        return buffer.getInt();
    }

    private SubStructure readSubStructure() throws IOException {
        return new SubStructure(readInt(), readStringForSubStructure(), readBoolean(), readDouble());
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
}