package ru.mail.polis.homework.io.blocking;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Вам нужно реализовать StructureOutputStream, который умеет писать данные в файл.
 * Писать поля нужно ручками, с помощью массива байт и методов {@link #write(int)}, {@link #write(byte[])} и так далее
 * 3 балла
 */
public class StructureOutputStream extends FileOutputStream {

    public StructureOutputStream(File name) throws FileNotFoundException {
        super(name);
    }

    /**
     * Метод должен вернуть записать прочитанную структуру.
     */
    public void write(Structure structure) throws IOException {
        write(longToBytes(structure.getId()));
        write(structure.getName());
        write(floatToBytes(structure.getCoeff()));
        write(boolsToByte(new boolean[]{structure.isFlag1(), structure.isFlag2(), structure.isFlag3(), structure.isFlag4()}));
        write(structure.getParam());
        write(structure.getSubStructures());
    }

    /**
     * Метод должен вернуть записать массив прочитанных структур.
     */
    public void write(Structure[] structures) throws IOException {
        for (Structure structure : structures) {
            write(structure);
        }
    }

    private void write(SubStructure subStructure) throws IOException {
        write(intToBytes(subStructure.getId()));
        write(subStructure.getName());
        write(boolsToByte(new boolean[]{subStructure.isFlag()}));
        write(doubleToBytes(subStructure.getScore()));
    }

    private void write(SubStructure[] subStructures) throws IOException {
        if (subStructures == null) {
            write(intToBytes(-1));
            return;
        }
        write(intToBytes(subStructures.length));
        for (SubStructure subStructure : subStructures) {
            write(subStructure);
        }
    }

    private byte[] longToBytes(long x) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(x);
        return buffer.array();
    }

    private byte[] floatToBytes(float number) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(Float.BYTES);
        byteBuffer.putFloat(number);
        return byteBuffer.array();
    }

    private byte[] doubleToBytes(double number) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(Double.BYTES);
        byteBuffer.putDouble(number);
        return byteBuffer.array();
    }

    private byte[] intToBytes(int number) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(Integer.BYTES);
        byteBuffer.putInt(number);
        return byteBuffer.array();
    }

    private byte boolsToByte(boolean[] bools) throws IOException {
        if (bools.length > 4) {
            throw new IOException();
        }
        byte result = 0;
        byte curr = 0;
        for (int i = 0; i < bools.length; i++) {
            curr = (byte) Math.pow(2, i);
            curr = bools[i] ? curr : 0;
            result = (byte) (result | curr);
        }
        return result;
    }

    private void write(String str) throws IOException {
        if (str == null) {
            write(intToBytes(-1));
            return;
        }
        write(intToBytes(str.length()));
        write(str.getBytes());
    }

}
