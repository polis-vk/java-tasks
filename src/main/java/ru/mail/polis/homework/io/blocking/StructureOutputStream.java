package ru.mail.polis.homework.io.blocking;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Вам нужно реализовать StructureOutputStream, который умеет писать данные в файл.
 * Писать поля нужно ручками, с помощью массива байт и методов {@link #write(int)}, {@link #write(byte[])} и так далее
 * 3 балла
 */
public class StructureOutputStream extends FileOutputStream {

    private final byte[] writeBuffer = new byte[8];

    public StructureOutputStream(File name) throws FileNotFoundException {
        super(name);
    }

    /**
     * Метод должен вернуть записать прочитанную структуру.
     */
    public void write(Structure structure) throws IOException {
        writeLong(structure.getId());
        writeChars(structure.getName());
        writeSubstructures(structure.getSubStructures());
        writeFloat(structure.getCoeff());
        writeFlags(structure);
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

    private void writeBoolean(boolean v) throws IOException {
        write(v ? 1 : 0);
    }

    private void writeFlags(Structure structure) throws IOException {
        int result = 0;
        result += structure.isFlag1() ? 1 : 0;
        result += structure.isFlag2() ? 2 : 0;
        result += structure.isFlag3() ? 4 : 0;
        result += structure.isFlag4() ? 8 : 0;
        writeByte(result);
    }

    private void writeByte(int v) throws IOException {
        write(v);
    }

    private void writeInt(int v) throws IOException {
        write((v >>> 24) & 0xFF);
        write((v >>> 16) & 0xFF);
        write((v >>> 8) & 0xFF);
        write((v) & 0xFF);
    }

    private void writeLong(long v) throws IOException {
        writeBuffer[0] = (byte) (v >>> 56);
        writeBuffer[1] = (byte) (v >>> 48);
        writeBuffer[2] = (byte) (v >>> 40);
        writeBuffer[3] = (byte) (v >>> 32);
        writeBuffer[4] = (byte) (v >>> 24);
        writeBuffer[5] = (byte) (v >>> 16);
        writeBuffer[6] = (byte) (v >>> 8);
        writeBuffer[7] = (byte) (v);
        write(writeBuffer, 0, 8);
    }

    private void writeFloat(float v) throws IOException {
        writeInt(Float.floatToIntBits(v));
    }

    private void writeDouble(double v) throws IOException {
        writeLong(Double.doubleToLongBits(v));
    }

    private void writeChars(String s) throws IOException {
        if (s == null) {
            writeInt(-1);
            return;
        }
        int len = s.length();
        writeInt(len);
        for (int i = 0; i < len; i++) {
            int v = s.charAt(i);
            write((v >>> 8) & 0xFF);
            write((v) & 0xFF);
        }
    }

    private void writeSubstructures(SubStructure[] subStructures) throws IOException {
        if (subStructures == null) {
            writeInt(-1);
            return;
        }
        int len = subStructures.length;
        writeInt(len);
        for (SubStructure subStructure : subStructures) {
            writeInt(subStructure.getId());
            writeChars(subStructure.getName());
            writeBoolean(subStructure.isFlag());
            writeDouble(subStructure.getScore());
        }
    }

}
