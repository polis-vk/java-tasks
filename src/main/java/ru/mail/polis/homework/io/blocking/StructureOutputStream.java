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
    public static final int NULL_PTR = -1;
    private static final int BITS_COUNT_IN_BYTE = 8;

    public StructureOutputStream(File name) throws FileNotFoundException {
        super(name);
    }

    /**
     * Метод должен вернуть записать прочитанную структуру.
     */
    public void write(Structure structure) throws IOException {
        writeLong(structure.getId());
        writeString(structure.getName());
        writeSubStructures(structure.getSubStructures());
        writeFloat((float) structure.getCoeff());
        writeFlags(new boolean[]{structure.isFlag1(), structure.isFlag2(), structure.isFlag3(), structure.isFlag4()});
        write(structure.getParam());
    }

    /**
     * Метод должен вернуть записать массив прочитанных структур.
     */
    public void write(Structure[] structures) throws IOException {
        for (Structure structure : structures) {
            write(structure);
        }
    }

    private void writeSubStructures(SubStructure[] subStructures) throws IOException {
        if (subStructures == null) {
            writeInt(NULL_PTR);
            return;
        }

        writeInt(subStructures.length);
        for (SubStructure subStructure : subStructures) {
            writeSubStructure(subStructure);
        }
    }

    private void writeSubStructure(SubStructure subStructure) throws IOException {
        writeInt(subStructure.getId());
        writeString(subStructure.getName());
        writeBoolean(subStructure.isFlag());
        writeDouble(subStructure.getScore());
    }

    private void writeFlags(boolean[] flags) throws IOException {
        int buffByte = 0;
        for (boolean flag : flags) {
            buffByte = ((buffByte << 1) | (flag ? 1 : 0));
        }
        buffByte <<= (BITS_COUNT_IN_BYTE - flags.length);
        write(buffByte);
    }

    private void writeLong(long val) throws IOException {
        ByteBuffer buff = ByteBuffer.allocate(Long.BYTES);
        buff.putLong(val);
        write(buff.array());
    }

    private void writeInt(int val) throws IOException {
        ByteBuffer buff = ByteBuffer.allocate(Integer.BYTES);
        buff.putInt(val);
        write(buff.array());
    }

    private void writeString(String str) throws IOException {
        if (str == null) {
            writeInt(NULL_PTR);
            return;
        }

        int strLen = str.length();
        writeInt(strLen);
        if (strLen != 0) {
            byte[] strBuff = str.getBytes(StandardCharsets.UTF_8);
            write(strBuff);
        }
    }

    private void writeDouble(double val) throws IOException {
        ByteBuffer buff = ByteBuffer.allocate(Double.BYTES);
        buff.putDouble(val);
        write(buff.array());
    }

    private void writeFloat(float val) throws IOException {
        ByteBuffer buff = ByteBuffer.allocate(Float.BYTES);
        buff.putFloat(val);
        write(buff.array());
    }

    private void writeBoolean(boolean val) throws IOException {
        write(val ? 1 : 0);
    }
}