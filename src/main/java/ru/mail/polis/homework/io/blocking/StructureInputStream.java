package ru.mail.polis.homework.io.blocking;

import java.io.*;
import java.nio.charset.StandardCharsets;
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
        Structure result = new Structure();
        try {
            result.setId(readLong());
            result.setName(readNullableString());
            result.setSubStructures(readNullableSubstructures());
            result.setCoeff(readFloat());
            int bits = readUnsignedByte();
            result.setFlag1(cast(bits));
            bits >>= 1;
            result.setFlag2(cast(bits));
            bits >>= 1;
            result.setFlag3(cast(bits));
            bits >>= 1;
            result.setFlag4(cast(bits));
            result.setParam(readByte());
            structures.add(result);
            return result;
        } catch (EOFException e) {
            return null;
        }
    }

    /**
     * Метод должен вернуть все структуры, которые есть в файле.
     * Если файл уже прочитан, но возвращается полный массив.
     */
    public Structure[] readStructures() throws IOException {
        while (readStructure() != null) {
            // should be empty
        }
        return structures.toArray(new Structure[0]);
    }

    private long readLong() throws IOException {
        long result = 0;
        for (int i = 0; i < Long.BYTES; i++) {
            result |= (long) readUnsignedByte() << (i * 8);
        }
        return result;
    }

    private String readNullableString() throws IOException {
        if (!readBoolean()) {
            return null;
        }
        return readString();
    }

    private SubStructure[] readNullableSubstructures() throws IOException {
        if (!readBoolean()) {
            return null;
        }
        return readSubstructures();
    }

    private SubStructure[] readSubstructures() throws IOException {
        final SubStructure[] result = new SubStructure[readInt()];
        for (int i = 0; i < result.length; i++) {
            result[i] = readNullableSubstructure();
        }
        return result;
    }

    private SubStructure readNullableSubstructure() throws IOException {
        if (!readBoolean()) {
            return null;
        }
        return readSubstructure();
    }

    private SubStructure readSubstructure() throws IOException {
        return new SubStructure(readInt(), readString(), readBoolean(), readDouble());
    }

    private int readInt() throws IOException {
        int result = 0;
        for (int i = 0; i < Integer.BYTES; i++) {
            result |= readUnsignedByte() << (i * 8);
        }
        return result;
    }

    private String readString() throws IOException {
        byte[] bytes = new byte[readInt()];
        if (read(bytes) != bytes.length) {
            throw new EOFException();
        }
        return new String(bytes, StandardCharsets.UTF_8);
    }


    private double readDouble() throws IOException {
        return Double.longBitsToDouble(readLong());
    }

    private float readFloat() throws IOException {
        return Float.intBitsToFloat(readInt());
    }

    private boolean readBoolean() throws IOException {
        int bits = read();
        if (bits < 0) {
            throw new EOFException();
        }
        return cast(bits);
    }

    private byte readByte() throws IOException {
        return (byte) (readUnsignedByte() - 128);
    }

    private int readUnsignedByte() throws IOException {
        int bits = read();
        if (bits < 0) {
            throw new EOFException();
        }
        return bits;
    }

    private boolean cast(int value) {
        return (value & 1) != 0;
    }
}
