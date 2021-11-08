package ru.mail.polis.homework.io.blocking;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;

/**
 * Вам нужно реализовать StructureInputStream, который умеет читать данные из файла.
 * Читать поля нужно ручками, с помощью массива байт и методов {@link #read()}, {@link #read(byte[])} и так далее
 * 3 балла
 */
public class StructureInputStream extends FileInputStream {
    private final LinkedList<Structure> structures = new LinkedList<>();

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
        boolean[] flags = readFlags();
        structure.setFlag1(flags[0]);
        structure.setFlag2(flags[1]);
        structure.setFlag3(flags[2]);
        structure.setFlag4(flags[3]);
        structure.setParam((byte) read());

        structures.add(structure);
        return structure;
    }

    /**
     * Метод должен вернуть все структуры, которые есть в файле.
     * Если файл уже прочитан, но возвращается полный массив.
     */
    public Structure[] readStructures() throws IOException {
        while (readStructure() != null) ;
        return structures.toArray(new Structure[0]);
    }

    private SubStructure[] readSubStructures() throws IOException {
        int subCount = readInt();

        if (subCount == StructureOutputStream.NULL_PTR) {
            return null;
        }

        SubStructure[] subStructures = new SubStructure[subCount];
        for (int i = 0; i < subCount; i++) {
            subStructures[i] = readSubStructure();
        }

        return subStructures;
    }

    private SubStructure readSubStructure() throws IOException {
        return new SubStructure(readInt(), readString(), readBoolean(), readDouble());
    }

    private long readLong() throws IOException {
        return getByteBuff(Long.BYTES).getLong();
    }

    private int readInt() throws IOException {
        return getByteBuff(Integer.BYTES).getInt();
    }

    private String readString() throws IOException {
        int strSize = readInt();
        if (strSize == StructureOutputStream.NULL_PTR) {
            return null;
        }
        if (strSize == 0) {
            return "";
        }

        byte[] stringBuff = new byte[strSize];
        read(stringBuff);
        return new String(stringBuff, StandardCharsets.UTF_8);
    }

    private double readDouble() throws IOException {
        return getByteBuff(Double.BYTES).getDouble();
    }

    private float readFloat() throws IOException {
        return getByteBuff(Float.BYTES).getFloat();
    }

    private boolean[] readFlags() throws IOException {
        byte buffByte = (byte) read();
        boolean[] flags = new boolean[4];
        for (int i = 0; i < 4; i++) {
            flags[i] = ((buffByte >> (7 - i)) & 0x01) == 1;
        }

        return flags;
    }

    private boolean readBoolean() throws IOException {
        return read() == 1;
    }

    private ByteBuffer getByteBuff(int bytesC) throws IOException {
        byte[] buff = new byte[bytesC];
        int readBytesC = read(buff);
        if (readBytesC != bytesC) {
            throw new IOException();
        }

        ByteBuffer bBuff = ByteBuffer.allocate(bytesC);
        bBuff.put(buff);
        bBuff.flip(); //need flip

        return bBuff;
    }
}
