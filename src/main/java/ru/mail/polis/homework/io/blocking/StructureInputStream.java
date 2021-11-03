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

    private final List<Structure> structures = new ArrayList<>();
    private final byte[] readBuffer = new byte[8];

    public StructureInputStream(File fileName) throws FileNotFoundException {
        super(fileName);
    }


    /**
     * Метод должен вернуть следующую прочитанную структуру.
     * Если структур в файле больше нет, то вернуть null
     */
    public Structure readStructure() throws IOException {
        if(available() <= 0) {
            return null;
        }
        Structure structure = new Structure();
        structure.setId(readLong());
        structure.setName(readString());
        structure.setSubStructures(readSubstructures());
        structure.setCoeff((float) readDouble());
        structure.setFlag1(readBoolean());
        structure.setFlag2(readBoolean());
        structure.setFlag3(readBoolean());
        structure.setFlag4(readBoolean());
        structure.setParam(readByte());
        structures.add(structure);
        return structure;
    }

    /**
     * Метод должен вернуть все структуры, которые есть в файле.
     * Если файл уже прочитан, но возвращается полный массив.
     */
    public Structure[] readStructures() throws IOException {
        Structure temp = readStructure();
        while(temp != null) {
            temp = readStructure();
        }
        return structures.size() == 0 ? new Structure[0] : structures.toArray(new Structure[0]);
    }

    private boolean readBoolean() throws IOException {
        int ch = read();
        if (ch < 0)
            throw new EOFException();
        return (ch != 0);
    }

    private byte readByte() throws IOException {
        int ch = read();
        if (ch < 0)
            throw new EOFException();
        return (byte) (ch);
    }

    private int readInt() throws IOException {
        int ch1 = read();
        int ch2 = read();
        int ch3 = read();
        int ch4 = read();
        if ((ch1 | ch2 | ch3 | ch4) < 0)
            throw new EOFException();
        return ((ch1 << 24) + (ch2 << 16) + (ch3 << 8) + (ch4));
    }

    private long readLong() throws IOException {
        int count = read(readBuffer, 0, Long.BYTES);
        if (count < 0) {
            throw new EOFException();
        }
        return (((long)readBuffer[0] << 56) +
                ((long)(readBuffer[1] & 255) << 48) +
                ((long)(readBuffer[2] & 255) << 40) +
                ((long)(readBuffer[3] & 255) << 32) +
                ((long)(readBuffer[4] & 255) << 24) +
                ((readBuffer[5] & 255) << 16) +
                ((readBuffer[6] & 255) <<  8) +
                ((readBuffer[7] & 255)));
    }

    private double readDouble() throws IOException {
        return Double.longBitsToDouble(readLong());
    }

    private String readString() throws IOException {
        int len = readInt();
        StringBuilder resultString = new StringBuilder();
        for (int i = 0; i < len; i++) {
            resultString.append((char) readByte());
        }
        return resultString.toString().equals(Structure.UNDEFINED_STRING) ? null : resultString.toString();
    }

    private SubStructure[] readSubstructures() throws IOException {
        int len = readInt();
        if (len == 0) {
            return new SubStructure[0];
        }
        SubStructure[] subStructures = new SubStructure[len];
        subStructures[0] = new SubStructure(readInt(), readString(), readBoolean(), readDouble());
        if(subStructures[0].getName() == null) {
            return null;
        }
        for(int i = 1; i < len; i++) {
            subStructures[i] = new SubStructure(readInt(), readString(), readBoolean(), readDouble());
        }
        return subStructures;
    }
}
