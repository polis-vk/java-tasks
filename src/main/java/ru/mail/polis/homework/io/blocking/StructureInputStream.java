package ru.mail.polis.homework.io.blocking;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Вам нужно реализовать StructureInputStream, который умеет читать данные из файла.
 * Читать поля нужно ручками, с помощью массива байт и методов {@link #read()}, {@link #read(byte[])} и так далее
 * 3 балла
 */
public class StructureInputStream extends FileInputStream {

    private Structure[] structures;
    private int position = 0;

    public StructureInputStream(File fileName) throws FileNotFoundException {
        super(fileName);
        this.structures = new Structure[0];
    }


    /**
     * Метод должен вернуть следующую прочитанную структуру.
     * Если структур в файле больше нет, то вернуть null
     */
    public Structure readStructure() throws IOException {
        if (super.available() == 0) {
            return null;
        }
        Structure structure = new Structure();
        structure.setId(readLong());
        structure.setName(readString());
        structure.setSubStructures(readSubStructures());
        structure.setCoeff((float) readDouble());
        structure.setFlag1(readBoolean());
        structure.setFlag2(readBoolean());
        structure.setFlag3(readBoolean());
        structure.setFlag4(readBoolean());
        structure.setParam(readByte());

        if (structures.length == position) {
            Structure[] newStructure = new Structure[structures.length + 1];
            System.arraycopy(structures, 0, newStructure, 0, structures.length);
            structures = newStructure;
        }
        structures[position++] = structure;
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
        return structures;
    }

    private boolean readBoolean() throws IOException {
        byte[] buffer = new byte[1];
        if (read(buffer) < 0) {
            throw new EOFException();
        }
        return (buffer[0] != 0);
    }

    private byte readByte() throws IOException {
        byte[] buffer = new byte[1];
        if (read(buffer) < 0) {
            throw new EOFException();
        }
        return buffer[0];
    }

    private int readInt() throws IOException {
        byte[] buffer = new byte[4];
        if (read(buffer) < 0) {
            throw new EOFException();
        }
        return (((buffer[0] & 255) << 24) +
                ((buffer[1] & 255) << 16) +
                ((buffer[2] & 255) << 8) +
                ((buffer[3] & 255)));
    }

    private long readLong() throws IOException {
        byte[] buffer = new byte[8];
        if (read(buffer) < 0) {
            throw new EOFException();
        }
        return (((long) (buffer[0] & 255) << 56) +
                ((long) (buffer[1] & 255) << 48) +
                ((long) (buffer[2] & 255) << 40) +
                ((long) (buffer[3] & 255) << 32) +
                ((long) (buffer[4] & 255) << 24) +
                ((long) (buffer[5] & 255) << 16) +
                ((long) (buffer[6] & 255) << 8) +
                ((long) (buffer[7] & 255)));
    }

    private float readFloat() throws IOException {
        byte[] buffer = new byte[4];
        if (read(buffer) < 0) {
            throw new EOFException();
        }
        return ByteBuffer.wrap(buffer).getFloat();
    }

    private double readDouble() throws IOException {
        byte[] buffer = new byte[8];
        if (read(buffer) < 0) {
            throw new EOFException();
        }
        return ByteBuffer.wrap(buffer).getDouble();
    }

    private char readChar() throws IOException {
        byte[] buffer = new byte[2];
        if (read(buffer) < 0) {
            throw new EOFException();
        }
        return (char) (((buffer[0] & 255) << 8) + buffer[1]);
    }

    private String readString() throws IOException {
        int length = readInt();
        if (length < 0) {
            return null;
        }
        char[] charsOfString = new char[length];
        for (int i = 0; i < length; ++i) {
            charsOfString[i] = readChar();
        }
        return String.valueOf(charsOfString);
    }

    private SubStructure readSubStructure() throws IOException {
        return new SubStructure(readInt(), readString(), readBoolean(), readDouble());
    }

    private SubStructure[] readSubStructures() throws IOException {
        int length = readInt();
        if (length < 0) {
            return null;
        }
        SubStructure[] subStructures = new SubStructure[length];
        for (int i = 0; i < length; ++i) {
            subStructures[i] = readSubStructure();
        }
        return subStructures;
    }
}
