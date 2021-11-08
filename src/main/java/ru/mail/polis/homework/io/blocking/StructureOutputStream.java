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
        writeByte(Byte.parseByte(new StringBuilder()
                        .append(boolToInt(structure.isFlag1()))
                        .append(boolToInt(structure.isFlag2()))
                        .append(boolToInt(structure.isFlag3()))
                        .append(boolToInt(structure.isFlag4()))
                        .toString()
                , 2));
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

    private void writeByte(byte v) throws IOException {
        write(v);
    }

    private void writeInt(int v) throws IOException {
        write((v >>> 24) & 0xFF);
        write((v >>> 16) & 0xFF);
        write((v >>> 8) & 0xFF);
        write((v >>> 0) & 0xFF);
    }

    private void writeLong(long v) throws IOException {
        byte[] writeBuffer = new byte[Long.BYTES];
        writeBuffer[0] = (byte) (v >>> 56);
        writeBuffer[1] = (byte) (v >>> 48);
        writeBuffer[2] = (byte) (v >>> 40);
        writeBuffer[3] = (byte) (v >>> 32);
        writeBuffer[4] = (byte) (v >>> 24);
        writeBuffer[5] = (byte) (v >>> 16);
        writeBuffer[6] = (byte) (v >>> 8);
        writeBuffer[7] = (byte) (v >>> 0);
        write(writeBuffer);
    }

    private void writeFloat(float v) throws IOException {
        writeInt(Float.floatToIntBits(v));
    }

    private void writeDouble(double v) throws IOException {
        writeLong(Double.doubleToLongBits(v));
    }

    private void writeString(String v) throws IOException {
        if (v == null) {
            writeInt(-1);
            return;
        }
        writeInt(v.length());
        write(v.getBytes());
    }

    private void writeSubStructure(SubStructure subStructure) throws IOException {
        writeInt(subStructure.getId());
        writeString(subStructure.getName());
        writeBoolean(subStructure.isFlag());
        writeDouble(subStructure.getScore());
    }

    private void writeSubStructures(SubStructure[] subStructures) throws IOException {
        if (subStructures == null) {
            writeInt(-1);
            return;
        }
        writeInt(subStructures.length);
        for (SubStructure subStructure : subStructures) {
            writeSubStructure(subStructure);
        }
    }

    private int boolToInt(boolean b) {
        return b ? 1 : 0;
    }
}
