package ru.mail.polis.homework.io.blocking;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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
        Structure readableStructure = new Structure();

        readableStructure.setId(readLong());
        readableStructure.setName(readString());
        readableStructure.setSubStructures(readSubStructures());
        readableStructure.setCoeff(readFloat());
        boolean[] flags = readFlags();
        readableStructure.setFlag1(flags[0]);
        readableStructure.setFlag2(flags[1]);
        readableStructure.setFlag3(flags[2]);
        readableStructure.setFlag4(flags[3]);
        int param = read();
        if (param < 0) {
            throw new EOFException();
        }
        readableStructure.setParam((byte) param);

        structures.add(readableStructure);
        return readableStructure;
    }


    /**
     * Метод должен вернуть все структуры, которые есть в файле.
     * Если файл уже прочитан, то возвращается полный массив.
     */
    public Structure[] readStructures() throws IOException {
        while (available() != 0) {
            readStructure();
        }
        return structures.toArray(new Structure[0]);
    }

    private long readLong() throws IOException {
        byte[] buffer = new byte[Long.BYTES];
        if (read(buffer) < 0) {
            throw new EOFException();
        }
        return (((long) buffer[0] << 56) +
                ((long) (buffer[1] & 255) << 48) +
                ((long) (buffer[2] & 255) << 40) +
                ((long) (buffer[3] & 255) << 32) +
                ((long) (buffer[4] & 255) << 24) +
                ((buffer[5] & 255) << 16) +
                ((buffer[6] & 255) << 8) +
                ((buffer[7] & 255)));
    }

    private String readString() throws IOException {
        int length = readInt();
        if (length < 0) {
            return null;
        }
        byte[] stringBuffer = new byte[length];
        if (read(stringBuffer) != length) {
            throw new EOFException();
        }
        return new String(stringBuffer);
    }

    private SubStructure[] readSubStructures() throws IOException {
        int size = readInt();
        if (size < 0) {
            return null;
        }
        SubStructure[] subStructures = new SubStructure[size];
        for (int i = 0; i < size; i++) {
            subStructures[i] = readSubStructure();
        }
        return subStructures;
    }

    private float readFloat() throws IOException {
        return Float.intBitsToFloat(readInt());
    }

    private boolean[] readFlags() throws IOException {
        int ch = read();
        if (ch < 0) {
            throw new EOFException();
        }
        return new boolean[] {
                byteToBoolean((byte) ch),
                byteToBoolean((byte) (ch >> 1)),
                byteToBoolean((byte) (ch >> 2)),
                byteToBoolean((byte) (ch >> 3))
        };
    }

    private boolean byteToBoolean(byte b) {
        return (b % 2) != 0;
    }

    private int readInt() throws IOException {
        int ch1 = read();
        int ch2 = read();
        int ch3 = read();
        int ch4 = read();
        if ((ch1 | ch2 | ch3 | ch4) < 0) {
            throw new EOFException();
        }
        return ((ch1 << 24) + (ch2 << 16) + (ch3 << 8) + ch4);
    }

    private SubStructure readSubStructure() throws IOException {
        return new SubStructure(readInt(), readSubStructureString(), readBoolean(), readDouble());
    }

    private String readSubStructureString() throws IOException { // Строка в SubStructure NotNull
        String res = readString();
        if (res == null) {
            throw new EOFException();
        }
        return res;
    }

    private boolean readBoolean() throws IOException {
        int ch = read();
        if (ch < 0) {
            throw new EOFException();
        }
        return (ch != 0);
    }

    private double readDouble() throws IOException {
        return Double.longBitsToDouble(readLong());
    }
}
