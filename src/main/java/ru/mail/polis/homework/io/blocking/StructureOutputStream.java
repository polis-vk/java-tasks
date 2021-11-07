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
        stringToBytes(structure.getName());
        write(doubleToBytes(structure.getCoeff()));
        write(boolToBytes(structure.isFlag1()));
        write(boolToBytes(structure.isFlag2()));
        write(boolToBytes(structure.isFlag3()));
        write(boolToBytes(structure.isFlag4()));
        write(structure.getParam());
        if (structure.getSubStructures() == null) {
            write(intToBytes(-1));
        } else {
            write(intToBytes(structure.getSubStructures().length));
            for (SubStructure subStructure : structure.getSubStructures()) {
                write(subStructure);
            }
        }
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
        stringToBytes(subStructure.getName());
        write(boolToBytes(subStructure.isFlag()));
        write(doubleToBytes(subStructure.getScore()));
    }

    private byte[] longToBytes(long x) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(x);
        return buffer.array();
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

    private byte boolToBytes(boolean b) {
        return b ? (byte) 1 : 0;
    }

    private void stringToBytes(String str) throws IOException {
        if (str == null) {
            write(intToBytes(-1));
            return;
        }
        write(intToBytes(str.length()));
        write(str.getBytes());
    }

}
