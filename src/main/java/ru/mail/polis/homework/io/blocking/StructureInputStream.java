package ru.mail.polis.homework.io.blocking;

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
 * 3 балла
 */
public class StructureInputStream extends FileInputStream {

    private List<Structure> structures = new ArrayList<>();

    public StructureInputStream(File fileName) throws FileNotFoundException {
        super(fileName);
    }


    /**
     * Метод должен вернуть следующую прочитанную структуру.
     * Если структур в файле больше нет, то вернуть null
     */
    public Structure readStructure() throws IOException{
        if(available() == 0){return null;}

        Structure structure = new Structure()
                .setId(readLong())
                .setName(readString())
                .setSubStructures(readSubStructures())
                .setCoeff(readFloat());
        boolean[] flags = readFlags(4);
        structure
                .setFlag1(flags[0])
                .setFlag2(flags[1])
                .setFlag3(flags[2])
                .setFlag4(flags[3])
                .setParam((byte) read());
        structures.add(structure);
        return structure;
    }

    /**
     * Метод должен вернуть все структуры, которые есть в файле.
     * Если файл уже прочитан, но возвращается полный массив.
     */
    public Structure[] readStructures() throws IOException{
        while (available() > 0){
            readStructure();
        }
        return structures.toArray(new Structure[0]);
    }

    private int readInt() throws IOException{
        byte[] b = new byte[4];
        read(b);
        return ByteBuffer.wrap(b).getInt();
    }

    private long readLong() throws IOException{
        byte[] b = new byte[8];
        read(b);
        return ByteBuffer.wrap(b).getLong();
    }

    private String readString() throws IOException{
        int size = readInt();
        if (size < 0){
            return null;
        }
        byte[] b = new byte[size];
        read(b);
        return new String(b);
    }

    private double readDouble() throws IOException{
        byte[] b = new byte[8];
        read(b);
        return ByteBuffer.wrap(b).getDouble();
    }

    private float readFloat() throws IOException{
        byte[] b = new byte[4];
        read(b);
        return ByteBuffer.wrap(b).getFloat();
    }

    private boolean readBoolean() throws IOException{
        return read() == 1;
    }

    private boolean[] readFlags(int n) throws IOException{
        boolean[] res = new boolean[n];
        int tmp = read();
        for (int i = 0; i < n; i++) {
            res[i] = ((tmp >> i) & 1) == 1;
        }
        return res;
    }

    private SubStructure readSubStructure() throws IOException{
        int id = readInt();
        String name = readString();
        if (name == null) {
            throw new IOException();
        }
        return new SubStructure(id, name, readBoolean(), readDouble());
    }

    private SubStructure[] readSubStructures() throws IOException{
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
}
