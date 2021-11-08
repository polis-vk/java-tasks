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

    private List<Structure> structureList = new ArrayList<>();

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
        while (available() > 0) {
            readStructure();
        }
        return structureList.toArray(new Structure[0]);
    }

    private boolean readBoolean() throws IOException {
        return read() == 1;
    }

    private byte readByte() throws IOException {
        return (byte) read();
    }

    private int readInt() throws IOException {
        return ((read() << 24) + (read() << 16) + (read() << 8) + (read() << 0));
    }

    private long readLong() throws IOException {
        byte[] readBuffer = new byte[Long.BYTES];
        if (read(readBuffer) != readBuffer.length) {
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
        if (stringLength == -1) {
            return null;
        }
        byte[] b = new byte[stringLength];
        if (read(b) != stringLength) {
            throw new IOException();
        }
        return new String(b);
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
        if (subStructuresNumber == -1) {
            return null;
        }
        SubStructure[] subStructures = new SubStructure[subStructuresNumber];
        for (int i = 0; i < subStructures.length; i++) {
            subStructures[i] = readSubStructure();
        }
        return subStructures;
    }
}
