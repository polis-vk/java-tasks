package ru.mail.polis.homework.io.blocking;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Вам нужно реализовать StructureInputStream, который умеет читать данные из файла.
 * Читать поля нужно ручками, с помощью массива байт и методов {@link #read()}, {@link #read(byte[])} и так далее
 * 3 тугрика
 */
public class StructureInputStream extends FileInputStream {

    //private Structure[] structures;
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
        readFlags(structure);
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

    private ByteBuffer readNumber(int size) throws IOException {
        byte[] bytes = new byte[size];
        if (read(bytes) != bytes.length) {
            throw new EOFException();
        }
        ByteBuffer buf = ByteBuffer.allocate(size);
        return buf.put(bytes).flip();
    }

    private boolean readBoolean() throws IOException {
        return read() == 1;
    }

    private byte readByte() throws IOException {
        return (byte) read();
    }

    private int readInt() throws IOException {
        return readNumber(Integer.BYTES).getInt();
    }

    private long readLong() throws IOException {
        return readNumber(Long.BYTES).getLong();
    }

    private float readFloat() throws IOException {
        return readNumber(Float.BYTES).getFloat();
    }

    private double readDouble() throws IOException {
        return readNumber(Double.BYTES).getDouble();
    }

    private String readString() throws IOException {
        int length = readInt();
        if (length == -1) {
            return null;
        }
        return new String(readNBytes(length));
    }

    private void readFlags(Structure structure) throws IOException {
        byte bytes = (byte) read();
        boolean[] flags = new boolean[4];
        for (int i = flags.length - 1; i >= 0; i--) {
            flags[i] = (bytes & (1 << i)) != 0;
        }
        structure.setFlag1(flags[0]);
        structure.setFlag2(flags[1]);
        structure.setFlag3(flags[2]);
        structure.setFlag4(flags[3]);
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
        int countOfStructures = readInt();
        if (countOfStructures == -1) {
            return null;
        }
        SubStructure[] subStructures = new SubStructure[countOfStructures];
        for (int i = 0; i < countOfStructures; i++) {
            subStructures[i] = readSubStructure();
        }
        return subStructures;
    }
}
