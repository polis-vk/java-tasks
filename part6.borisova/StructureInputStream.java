package ru.mail.polis.homework.io.blocking;

import com.sun.tools.javac.util.ArrayUtils;
import sun.security.util.ArrayUtil;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Вам нужно реализовать StructureInputStream, который умеет читать данные из файла.
 * Читать поля нужно ручками, с помощью массива байт и методов {@link #read()}, {@link #read(byte[])} и так далее
 * 3 балла
 */
public class StructureInputStream extends FileInputStream {

    private final List<Structure> list = new ArrayList<>();

    public StructureInputStream(File fileName) throws FileNotFoundException {
        super(fileName);
    }

    /**
     * Метод должен вернуть следующую прочитанную структуру.
     * Если структур в файле больше нет, то вернуть null
     */
    public Structure readStructure() throws IOException {
        if (available() == 0) { //Возвращает количество байтов, доступных для чтения
            return null;
        }
        Structure structure = new Structure();
        structure.setId(readLong());
        structure.setName(readString());
        structure.setSubStructures(readSubStructures());
        structure.setCoeff(readDouble());
        byte flags = (byte) read();
        structure.setFlag1(flags % 2 == 1);
        flags >>= 1;
        structure.setFlag2(flags % 2 == 1);
        flags >>= 1;
        structure.setFlag3(flags % 2 == 1);
        flags >>= 1;
        structure.setFlag4(flags % 2 == 1);
        //structure.setFlag1(readBoolean());
        //structure.setFlag2(readBoolean());
        //structure.setFlag3(readBoolean());
        //structure.setFlag4(readBoolean());
        structure.setParam((byte) read());
        list.add(structure);
        return structure;
    }

    private long readLong() throws IOException {
        byte[] inputBytes = new byte[Long.BYTES];
        read(inputBytes);
        //ArrayUtil.reverse(inputBytes);
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.put(inputBytes);
        buffer.flip();
        return buffer.getLong();
    }

    private int readInt() throws IOException {
        byte[] inputBytes = new byte[Integer.BYTES];
        read(inputBytes);
        //ArrayUtil.reverse(inputBytes);
        ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES);
        buffer.put(inputBytes);
        buffer.flip();
        return buffer.getInt();
    }

    /*private long readLong() throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        byte[] bytes = new byte[Long.BYTES];
        read(bytes);
        buffer.put(bytes);
        buffer.flip();
        return buffer.getLong();
    }*/

    private Character readCharacter() throws IOException {
        byte[] inputBytes = new byte[Character.BYTES];
        read(inputBytes);
        //ArrayUtil.reverse(inputBytes);
        ByteBuffer buffer = ByteBuffer.allocate(Character.BYTES);
        buffer.put(inputBytes);
        buffer.flip();
        return buffer.getChar();
    }

    private String readString() throws IOException { //???????????????
        StringBuilder sb = new StringBuilder();
        Character c = readCharacter();

        while (c != '\0') {
            sb.append(c);
            c = readCharacter();
        }

        if (sb.length() == 0)
            return null;

        return sb.toString();
    }

    private boolean readBoolean() throws IOException {
        return read() == 1;
    }

    private float readFloat() throws IOException {
        byte[] inputBytes = new byte[Float.BYTES];
        read(inputBytes);
        //ArrayUtil.reverse(inputBytes);
        ByteBuffer buffer = ByteBuffer.allocate(Float.BYTES); //??????
        buffer.put(inputBytes);
        buffer.flip();
        return buffer.getFloat();
    }

    private double readDouble() throws IOException {
        byte[] inputBytes = new byte[Double.BYTES];
        read(inputBytes);
        //ArrayUtil.reverse(inputBytes);
        ByteBuffer buffer = ByteBuffer.allocate(Double.BYTES); //??????
        buffer.put(inputBytes);
        buffer.flip();
        return buffer.getDouble();
    }

    private SubStructure[] readSubStructures() throws IOException {
        int subStructuresNumber = readInt();
        if (subStructuresNumber == 0) {
            return null;
        }
        SubStructure[] subStructures = new SubStructure[subStructuresNumber];
        for (int i = 0; i < subStructures.length; i++) {
            subStructures[i] = new SubStructure(
                    readInt(),
                    readString(),
                    readBoolean(),
                    readDouble()
            );
        }
        return subStructures;
    }

    /*
    private String readString() throws IOException { //???????????????
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



    private double readDouble() throws IOException {
        return Double.longBitsToDouble(readLong());
    }

    private int readInt() throws IOException { //?????????????????
        return ((read() << 24) + (read() << 16) + (read() << 8) + (read()));
    }

    private float readFloat() throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(Float.BYTES); //??????
        byte[] bytes = new byte[Float.BYTES];
        read(bytes);
        buffer.put(bytes);
        buffer.flip();
        return buffer.getFloat();
    }

    private byte readByte() throws IOException {
        return (byte) read();
    }
    */

    /**
     * Метод должен вернуть все структуры, которые есть в файле.
     * Если файл уже прочитан, но возвращается полный массив.
     */
    public Structure[] readStructures() throws IOException {
        while (available() > 0) {
            readStructure();
        }
        return list.toArray(new Structure[0]);
    }
}
