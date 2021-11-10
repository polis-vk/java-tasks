package ru.mail.polis.homework.io.blocking;

import java.io.*;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
        structure.setId(readLong());
        structure.setName(readUTF());
        structure.setSubStructures(readSubStructures());
        structure.setCoeff(readFloat());
        byte k = (byte) read();
        structure.setFlag1((k & 8) != 0);
        structure.setFlag2((k & 4) != 0);
        structure.setFlag3((k & 2) != 0);
        structure.setFlag4((k & 1) != 0);
        structure.setParam((byte) read());
        structures.add(structure);
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
        return structures.toArray(new Structure[0]);
    }

    public SubStructure readSubStructure() throws IOException {
        int id = readInt();
        String name = readUTF();
        if (name == null) {
            throw new InvalidParameterException("Name of Substring must be @NonNull");
        }
        boolean flag = readBoolean();
        double score = readDouble();
        return new SubStructure(id, name, flag, score);
    }

    public SubStructure[] readSubStructures() throws IOException {
        int len = readInt();
        if (len == -1) {
            return null;
        }
        SubStructure[] subStructures = new SubStructure[len];

        for (int i = 0; i < len; i++) {
            subStructures[i] = readSubStructure();
        }
        return subStructures;
    }

    public void readFully(byte[] b) throws IOException {
        readFully(b, 0, b.length);
    }

    public void readFully(byte[] b, int off, int len) throws IOException {
        Objects.checkFromIndexSize(off, len, b.length);
        int n = 0;
        while (n < len) {
            int count = read(b, off + n, len - n);
            if (count < 0)
                throw new EOFException();
            n += count;
        }
    }

    public boolean readBoolean() throws IOException {
        int ch = read();
        if (ch < 0)
            throw new EOFException();
        return (ch != 0);
    }

    private final byte[] READ_BUFFER = new byte[8];

    public long readLong() throws IOException {
        readFully(READ_BUFFER, 0, 8);
        return (((long) READ_BUFFER[0] << 56) +
                ((long) (READ_BUFFER[1] & 255) << 48) +
                ((long) (READ_BUFFER[2] & 255) << 40) +
                ((long) (READ_BUFFER[3] & 255) << 32) +
                ((long) (READ_BUFFER[4] & 255) << 24) +
                ((READ_BUFFER[5] & 255) << 16) +
                ((READ_BUFFER[6] & 255) << 8) +
                ((READ_BUFFER[7] & 255)));
    }

    public int readInt() throws IOException {
        readFully(READ_BUFFER, 0, 4);
        return (((READ_BUFFER[0] & 255) << 24) +
                ((READ_BUFFER[1] & 255) << 16) +
                ((READ_BUFFER[2] & 255) << 8) +
                ((READ_BUFFER[3] & 255)));
    }

    public float readFloat() throws IOException {
        return Float.intBitsToFloat(readInt());
    }

    public double readDouble() throws IOException {
        return Double.longBitsToDouble(readLong());
    }

    public String readUTF() throws IOException {
        int utflen = readInt();

        if (utflen == -1) {
            return null;
        }
        byte[] byteArray = new byte[utflen];
        if (read(byteArray) != utflen) {
            throw new IOException();
        }
        return new String(byteArray);
    }
}
