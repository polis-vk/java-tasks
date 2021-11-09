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
 * Читать поля нужно ручками, с помощью массива байт и методов {@link #this.read()}, {@link #this.read(byte[])} и так далее
 * 3 балла
 */
public class StructureInputStream extends FileInputStream {

    private List<Structure> structures = new ArrayList();

    public StructureInputStream(File fileName) throws FileNotFoundException {
        super(fileName);
    }

    /**
     * Метод должен вернуть следующую прочитанную структуру.
     * Если структур в файле больше нет, то вернуть null
     */
    public Structure readStructure() throws IOException {
        if (this.available() == 0) {
            return null;
        }
        Structure struct = new Structure();
        struct.setId(this.readLong());
        struct.setName(this.readString());
        struct.setSubStructures(this.readSubStructures());
        struct.setCoeff(this.readFloat());
        byte flags = (byte) this.read();
        struct.setFlag1((flags & 1) == 1);
        struct.setFlag2(((flags >> 1) & 1) == 1);
        struct.setFlag3(((flags >> 2) & 1) == 1);
        struct.setFlag4(((flags >> 3) & 1) == 1);
        struct.setParam((byte) this.read());
        this.structures.add(struct);
        return struct;
    }
    
    /**
     * Метод должен вернуть все структуры, которые есть в файле.
     * Если файл уже прочитан, то возвращается полный массив.
     */
    public Structure[] readStructures() throws IOException {
        while (this.available() > 0) {
            this.readStructure();
        }
        return this.structures.toArray(new Structure[this.structures.size()]);
    }

    private SubStructure readSubStructure() throws IOException {
        int id = readInt();
        String name = readString();
        if (name == null) { // файл сломан
            throw new EOFException();
        }
        boolean flag = readBoolean();
        double score = readDouble();
        return new SubStructure(id, name, flag, score);
    }

    private SubStructure[] readSubStructures() throws IOException {
        int size = readInt();
        if (size == -1) {
            return null;
        }
        SubStructure[] subs = new SubStructure[size];
        for (int i = 0; i < size; ++i) {
            subs[i] = readSubStructure();
        }
        return subs;
    }

    private boolean readBoolean() throws IOException {
        return this.read() == 1;
    }

    private int readInt() throws IOException {
        byte[] bytes = new byte[Integer.BYTES];
        this.read(bytes);
        return ((ByteBuffer) ByteBuffer.allocateDirect(Integer.BYTES).put(bytes).flip()).getInt();
    }

    private long readLong() throws IOException {
        byte[] bytes = new byte[Long.BYTES];
        this.read(bytes);
        return ((ByteBuffer) ByteBuffer.allocateDirect(Long.BYTES).put(bytes).flip()).getLong();

    }

    private float readFloat() throws IOException {
        byte[] bytes = new byte[Float.BYTES];
        this.read(bytes);
        return ((ByteBuffer) ByteBuffer.allocateDirect(Float.BYTES).put(bytes).flip()).getFloat();
    }

    private double readDouble() throws IOException {
        byte[] bytes = new byte[Double.BYTES];
        this.read(bytes);
        return ((ByteBuffer) ByteBuffer.allocateDirect(Double.BYTES).put(bytes).flip()).getDouble();
    }

    private String readString() throws IOException {
        int len = this.readInt();
        if (len == -1) {
            return null;
        }

        byte[] bytes = new byte[len];
        this.read(bytes);
        return new String(bytes).intern();
    }
    
}
