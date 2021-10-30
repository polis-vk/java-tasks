package ru.mail.polis.homework.io.blocking;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Вам нужно реализовать StructureInputStream, который умеет читать данные из файла.
 * Читать поля нужно ручками, с помощью массива байт и методов {@link #read()}, {@link #read(byte[])} и так далее
 * 3 балла
 */
public class StructureInputStream extends FileInputStream {

    public static final short bufferSize = 8;
    private final byte[] readBuffer = new byte[bufferSize];
    private final BufferedInputStream bufferedInputStream;

    public StructureInputStream(File fileName) throws FileNotFoundException {
        super(fileName);
        bufferedInputStream = new BufferedInputStream(this);
        bufferedInputStream.mark((int) fileName.length());
    }

    /**
     * Метод должен вернуть следующую прочитанную структуру.
     * Если структур в файле больше нет, то вернуть null
     */
    public Structure readStructure() throws IOException {
        if (bufferedInputStream.available() == 0) {
            return null;
        }
        return new Structure(
                readLong(),
                readString(),
                readSubStructures(),
                readDouble(),
                readByte(),
                readByte()
        );
    }

    /**
     * Метод должен вернуть все структуры, которые есть в файле.
     * Если файл уже прочитан, но возвращается полный массив.
     */
    public Structure[] readStructures() throws IOException {
        bufferedInputStream.reset();
        List<Structure> structureList = new ArrayList<>();
        while (bufferedInputStream.available() > 0) {
            structureList.add(readStructure());
        }
        Structure[] structures = new Structure[structureList.size()];
        for (int i = 0; i < structureList.size(); i++) {
            structures[i] = structureList.get(i);
        }
        return structures;
    }

    private boolean readBoolean() throws IOException {
        int ch = bufferedInputStream.read();
        if (ch < 0)
            throw new EOFException();
        return (ch != 0);
    }

    private byte readByte() throws IOException {
        int ch = bufferedInputStream.read();
        if (ch < 0)
            throw new EOFException();
        return (byte) (ch);
    }

    private int readInt() throws IOException {
        int ch1 = bufferedInputStream.read();
        int ch2 = bufferedInputStream.read();
        int ch3 = bufferedInputStream.read();
        int ch4 = bufferedInputStream.read();
        if ((ch1 | ch2 | ch3 | ch4) < 0)
            throw new EOFException();
        return ((ch1 << 24) + (ch2 << 16) + (ch3 << 8) + (ch4 << 0));
    }

    private long readLong() throws IOException {
        if (bufferedInputStream.read(readBuffer) != readBuffer.length) {
            throw new IOException();
        }
        return (((long) readBuffer[0] << 56) +
                ((long) (readBuffer[1] & 255) << 48) +
                ((long) (readBuffer[2] & 255) << 40) +
                ((long) (readBuffer[3] & 255) << 32) +
                ((long) (readBuffer[4] & 255) << 24) +
                ((readBuffer[5] & 255) << 16) +
                ((readBuffer[6] & 255) << 8) +
                ((readBuffer[7] & 255) << 0));
    }

    private double readDouble() throws IOException {
        return Double.longBitsToDouble(readLong());
    }

    private String readString() throws IOException {
        int stringLength = readInt();
        if (stringLength == 0) {
            return null;
        }
        byte[] b = new byte[stringLength];
        if (bufferedInputStream.read(b) != stringLength) {
            throw new IOException();
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (byte stringByte : b) {
            stringBuilder.append((char) stringByte);
        }
        return stringBuilder.toString();
    }

    private SubStructure readSubStructure() throws IOException {
        return new SubStructure(
                readInt(),
                readString(),
                readBoolean(),
                readDouble()
        );
    }

    private SubStructure[] readSubStructures() throws IOException {
        int subStructuresNumber = readInt();
        if (subStructuresNumber == 0) {
            return null;
        }
        SubStructure[] subStructures = new SubStructure[subStructuresNumber];
        for (int i = 0; i < subStructures.length; i++) {
            subStructures[i] = readSubStructure();
        }
        return subStructures;
    }
}
