package ru.mail.polis.homework.io.blocking;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * Вам нужно реализовать StructureOutputStream, который умеет писать данные в файл.
 * Писать поля нужно ручками, с помощью массива байт и методов {@link #write(int)}, {@link #write(byte[])} и так далее
 * 3 тугрика
 */
public class StructureOutputStream extends FileOutputStream {

    public StructureOutputStream(File name) throws FileNotFoundException {
        super(name);
    }

    /**
     * Метод должен вернуть записать прочитанную структуру.
     */
    public void write(Structure structure) throws IOException {
        if (structure == null) {
            return;
        }

        write(longToByte(structure.getId()));
        writeString(structure.getName());
        writeSubStructures(structure.getSubStructures());
        write(floatToByte(structure.getCoeff()));
        boolean[] flags = {structure.isFlag1(), structure.isFlag2(), structure.isFlag3(), structure.isFlag4()};
        writeFlags(flags);
        write(structure.getParam());
        flush();
    }

    /**
     * Метод должен вернуть записать массив прочитанных структур.
     */
    public void write(Structure[] structures) throws IOException {
        for (Structure structure : structures) {
            write(structure);
        }
    }

    private byte[] intToByte(int number) {
        ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES);
        buffer.putInt(number);
        return buffer.array();
    }

    private byte[] longToByte(long number) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(number);
        return buffer.array();
    }

    private byte[] floatToByte(float number) {
        ByteBuffer buffer = ByteBuffer.allocate(Float.BYTES);
        buffer.putFloat(number);
        return buffer.array();
    }

    private byte[] doubleToByte(double number) {
        ByteBuffer buffer = ByteBuffer.allocate(Double.BYTES);
        buffer.putDouble(number);
        return buffer.array();
    }

    private byte booleanToByte(boolean value) {
        return (byte) (value ? 1 : 0);
    }

    private void writeFlags(boolean[] flags) throws IOException {
        int b = (flags[3] ? 1 : 0) << 1;
        b = b + (flags[2] ? 1 : 0) << 1;
        b = b + (flags[1] ? 1 : 0) << 1;
        b = b + (flags[0] ? 1 : 0);
        write((byte) b);
    }

    private void writeString(String str) throws IOException {
        if (str == null) {
            write(intToByte(-1));
            return;
        }
        write(intToByte(str.length()));
        write(str.getBytes(StandardCharsets.UTF_8));
    }

    private void writeSubStructures(SubStructure[] subStructures) throws IOException {
        if (subStructures == null) {
            write(intToByte(-1));
            return;
        }

        write(intToByte(subStructures.length));
        for (SubStructure subStructure : subStructures) {
            write(intToByte(subStructure.getId()));
            writeString(subStructure.getName());
            write(booleanToByte(subStructure.isFlag()));
            write(doubleToByte(subStructure.getScore()));
        }
    }
}
