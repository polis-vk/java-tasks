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
 * 3 балла
 */
public class StructureOutputStream extends FileOutputStream {

    private static final int NULL_OBJECT = -1;

    public StructureOutputStream(File name) throws FileNotFoundException {
        super(name);
    }

    /**
     * Метод должен вернуть записать прочитанную структуру.
     */
    public void write(Structure structure) throws IOException {
        writeLong(structure.getId());
        writeString(structure.getName());
        writeSubstructureArray(structure.getSubStructures());
        writeFloat(structure.getCoeff());
        boolean[] flags = {structure.isFlag1(), structure.isFlag2(), structure.isFlag3(), structure.isFlag4()};
        writeFLags(flags);
        writeByte(structure.getParam());
    }

    /**
     * Метод должен вернуть записать массив прочитанных структур.
     */
    public void write(Structure[] structures) throws IOException {
        for (Structure structure : structures) {
            write(structure);
        }
    }

    private void writeInteger(int number) throws IOException {
        ByteBuffer bb = ByteBuffer.allocate(Integer.BYTES);
        bb.putInt(number);
        write(bb.array());
    }

    private void writeLong(long number) throws IOException {
        ByteBuffer bb = ByteBuffer.allocate(Long.BYTES);
        bb.putLong(number);
        write(bb.array());
    }

    private void writeFloat(float number) throws IOException {
        ByteBuffer bb = ByteBuffer.allocate(Float.BYTES);
        bb.putFloat(number);
        write(bb.array());
    }

    private void writeDouble(double number) throws IOException {
        ByteBuffer bb = ByteBuffer.allocate(Double.BYTES);
        bb.putDouble(number);
        write(bb.array());
    }

    private void writeString(String str) throws IOException {
        if (str == null) {
            writeInteger(NULL_OBJECT);
            return;
        }
        byte[] byteStr = str.getBytes(StandardCharsets.UTF_8);
        writeInteger(byteStr.length);
        write(byteStr);
    }

    private void writeBoolean(boolean bool) throws IOException {
        write(bool ? 1 : 0);
    }

    private void writeFLags(boolean[] flags) throws IOException {
        byte b = (byte) ((flags[0] ? 1 : 0) +
                (flags[1] ? 1 << 1 : 0) +
                (flags[2] ? 1 << 2 : 0) +
                (flags[3] ? 1 << 3 : 0));
        writeByte(b);
    }

    private void writeByte(byte number) throws IOException {
        write(new byte[]{number});
    }

    private void writeSubstructure(SubStructure subStructure) throws IOException {
        writeInteger(subStructure.getId());
        writeString(subStructure.getName());
        writeBoolean(subStructure.isFlag());
        writeDouble(subStructure.getScore());
    }

    private void writeSubstructureArray(SubStructure[] subStructures) throws IOException {
        if (subStructures == null) {
            writeInteger(NULL_OBJECT);
            return;
        }
        writeInteger(subStructures.length);
        for (SubStructure subStructure : subStructures) {
            writeSubstructure(subStructure);
        }
    }
}
