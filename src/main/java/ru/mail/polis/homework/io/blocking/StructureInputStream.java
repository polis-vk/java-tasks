package ru.mail.polis.homework.io.blocking;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * Вам нужно реализовать StructureInputStream, который умеет читать данные из файла.
 * Читать поля нужно ручками, с помощью массива байт и методов {@link #read()}, {@link #read(byte[])} и так далее
 * 3 балла
 */
public class StructureInputStream extends FileInputStream {
    private static final String STRUCTURE_BEGIN = "Structure";
    private final ArrayList<Structure> structures = new ArrayList<>();

    public StructureInputStream(File fileName) throws FileNotFoundException {
        super(fileName);
    }


    /**
     * Метод должен вернуть следующую прочитанную структуру.
     * Если структур в файле больше нет, то вернуть null
     */
    public Structure readStructure() throws IOException {
        String structureBeg;
        try {
            structureBeg = readString();
        } catch (IOException e) {
            return null;
        }

        if (!structureBeg.equals(STRUCTURE_BEGIN)) {
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
        structure.setParam((byte) super.read());

        structures.add(structure);
        return structure;
    }

    /**
     * Метод должен вернуть все структуры, которые есть в файле.
     * Если файл уже прочитан, но возвращается полный массив.
     */
    public Structure[] readStructures() throws IOException {
        Structure structure = readStructure();
        while (structure != null) {
            structure = readStructure();
        }

        return structures.toArray(new Structure[0]);
    }

    private SubStructure[] readSubStructures() throws IOException {
        int subCount = readInt();

        if (subCount == 0) {
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
        if(strSize == 0) {
            return null;
        }

        byte[] stringBuff = new byte[strSize];
        super.read(stringBuff);
        return new String(stringBuff, StructureOutputStream.STRING_CHARSET);
    }

    private double readDouble() throws IOException {
        return getByteBuff(Double.BYTES).getDouble();
    }

    private boolean readBoolean() throws IOException {
        return super.read() == 1;
    }

    private ByteBuffer getByteBuff(int bytesC) throws IOException {
        byte[] buff = new byte[bytesC];
        int readBytesC = super.read(buff);
        if (readBytesC != bytesC) {
            throw new IOException();
        }

        ByteBuffer bBuff = ByteBuffer.allocate(bytesC);
        bBuff.put(buff);
        bBuff.flip(); //need flip

        return bBuff;
    }
}
