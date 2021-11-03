package ru.mail.polis.homework.io.blocking;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

/**
 * Вам нужно реализовать StructureInputStream, который умеет читать данные из файла.
 * Читать поля нужно ручками, с помощью массива байт и методов {@link #read()}, {@link #read(byte[])} и так далее
 * 3 балла
 */
public class StructureInputStream extends FileInputStream {

    private Structure[] structures;
    private int n = 0;

    public StructureInputStream(File fileName) throws FileNotFoundException {
        super(fileName);
        structures = new Structure[0];
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

        structure.setCoeff(readDouble());

        setFlags(structure);
        structure.setParam((byte) super.read());

        if (n >= structures.length) {
            Structure[] newStructures = new Structure[structures.length + 1];
            System.arraycopy(structures, 0, newStructures, 0, structures.length);
            structures = newStructures;
        }
        structures[n++] = structure;

        return structure;
    }

    /**
     * Метод должен вернуть все структуры, которые есть в файле.
     * Если файл уже прочитан, но возвращается полный массив.
     */
    public Structure[] readStructures() throws IOException {
        while (readStructure() != null) ;
        return structures;
    }

    private long readLong() throws IOException {
        byte[] buffer = new byte[8];
        super.read(buffer);
        return (((long) buffer[0] << 56) +
                ((long) (buffer[1] & 255) << 48) +
                ((long) (buffer[2] & 255) << 40) +
                ((long) (buffer[3] & 255) << 32) +
                ((long) (buffer[4] & 255) << 24) +
                ((buffer[5] & 255) << 16) +
                ((buffer[6] & 255) << 8) +
                (buffer[7] & 255));
    }

    private String readString() throws IOException {
        int length = readInt();
        if (length == -1) {
            return null;
        }

        char[] res = new char[length];
        for (int i = 0; i < length; i++) {
            int ch1 = super.read();
            int ch2 = super.read();
            if ((ch1 | ch2) < 0) {
                throw new EOFException();
            }
            res[i] = (char) ((ch1 << 8) + (ch2));
        }

        return String.valueOf(res);
    }

    private double readDouble() throws IOException {
        return Double.longBitsToDouble(readLong());
    }

    private boolean readBoolean() throws IOException {
        int ch = super.read();
        if (ch < 0) {
            throw new EOFException();
        }
        return (ch != 0);
    }

    private void setFlags(Structure structure) throws IOException {
        int ch = super.read();
        if (ch < 0) {
            throw new EOFException();
        }
        structure.setFlag1(byteToBoolean((byte) (ch >> 3)));
        structure.setFlag2(byteToBoolean((byte) (ch >> 2)));
        structure.setFlag3(byteToBoolean((byte) (ch >> 1)));
        structure.setFlag4(byteToBoolean((byte) ch));
    }

    private boolean byteToBoolean(byte b) {
        return (b % 2) != 0;
    }


    private int readInt() throws IOException {
        int ch1 = super.read();
        int ch2 = super.read();
        int ch3 = super.read();
        int ch4 = super.read();
        if ((ch1 | ch2 | ch3 | ch4) < 0) {
            throw new EOFException();
        }
        return ((ch1 << 24) + (ch2 << 16) + (ch3 << 8) + (ch4));
    }

    private SubStructure readSubStructure() throws IOException {
        return new SubStructure(readInt(), readString(), readBoolean(), readDouble());
    }

    private SubStructure[] readSubStructures() throws IOException {
        SubStructure[] result;
        int length = readInt();

        if (length == -1) {
            result = null;
        } else {
            SubStructure[] subStructures = new SubStructure[length];
            for (int i = 0; i < length; i++) {
                subStructures[i] = readSubStructure();
            }
            result = subStructures;
        }

        return result;
    }
}
