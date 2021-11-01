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

    private final short BUFFER_SIZE = 8;
    private byte[] readBuffer = new byte[BUFFER_SIZE];
    private List<Structure> structureList = new ArrayList<>();

    public StructureInputStream(File fileName) throws FileNotFoundException {
        super(fileName);
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
        structure.setCoeff(readFloat());
        byte flagsAsByte = readByte();
        structure.setFlag1((flagsAsByte / 8) % 2 == 1);
        structure.setFlag2((flagsAsByte / 4) % 2 == 1);
        structure.setFlag3((flagsAsByte / 2) % 2 == 1);
        structure.setFlag4(flagsAsByte % 2 == 1);
        structure.setParam(readByte());
        structureList.add(structure);
        return structure;
    }

    /**
     * Метод должен вернуть все структуры, которые есть в файле.
     * Если файл уже прочитан, но возвращается полный массив.
     */
    public Structure[] readStructures() throws IOException {
        while (super.available() > 0) {
            readStructure();
        }
        return structureList.toArray(new Structure[0]);
    }

    private boolean readBoolean() throws IOException {
        int ch = super.read();
        if (ch < 0) {
            throw new EOFException();
        }
        return (ch != 0);
    }

    private byte readByte() throws IOException {
        int ch = super.read();
        if (ch < 0) {
            throw new EOFException();
        }
        return (byte) (ch);
    }

    private int readInt() throws IOException {
        int ch1 = super.read();
        int ch2 = super.read();
        int ch3 = super.read();
        int ch4 = super.read();
        if ((ch1 | ch2 | ch3 | ch4) < 0) {
            throw new EOFException();
        }
        return ((ch1 << 24) + (ch2 << 16) + (ch3 << 8) + (ch4 << 0));
    }

    private long readLong() throws IOException {
        if (super.read(readBuffer) != readBuffer.length) {
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

    private float readFloat() throws IOException {
        return Float.intBitsToFloat(readInt());
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
        if (super.read(b) != stringLength) {
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
