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
    private int size = 0;

    public StructureOutputStream(File name) throws FileNotFoundException {
        super(name);
        //writeSize();
    }

    /**
     * Метод должен вернуть записать прочитанную структуру.
     */
    public void write(Structure structure) throws IOException {
        writeLong(structure.getId());
        writeString(structure.getName());

        writeSubStructures(structure.getSubStructures());

        writeDouble(structure.getCoeff());
        writeFlags(structure.isFlag1(), structure.isFlag2(), structure.isFlag3(), structure.isFlag4());

        super.write(structure.getParam());
    }

    /**
     * Метод должен вернуть записать массив прочитанных структур.
     */
    public void write(Structure[] structures) throws IOException {
        for (Structure structure : structures) {
            write(structure);
        }
    }

    private void writeLong(long l) throws IOException {
        byte[] buffer = new byte[8];
        buffer[0] = (byte) (l >>> 56);
        buffer[1] = (byte) (l >>> 48);
        buffer[2] = (byte) (l >>> 40);
        buffer[3] = (byte) (l >>> 32);
        buffer[4] = (byte) (l >>> 24);
        buffer[5] = (byte) (l >>> 16);
        buffer[6] = (byte) (l >>> 8);
        buffer[7] = (byte) (l);
        super.write(buffer);
    }

    private void writeString(String s) throws IOException {
        if (s == null) {
            writeInt(-1);
            return;
        }
        writeInt(s.length());
        for (int i = 0; i < s.length(); i++) {
            int v = s.charAt(i);
            super.write((byte) (v >>> 8));
            super.write((byte) (v));
        }
    }

    private void writeDouble(double d) throws IOException {
        writeLong(Double.doubleToLongBits(d));
    }

    private void writeBoolean(boolean b) throws IOException {
        super.write(b ? 1 : 0);
    }

    private void writeFlags(boolean f1, boolean f2, boolean f3, boolean f4) throws IOException {
        super.write((byte) ((boolToNumber(f1) << 3) + (boolToNumber(f2) << 2) + (boolToNumber(f3) << 1) + boolToNumber(f4)));
    }

    private byte boolToNumber(boolean b) {
        return (byte) (b ? 1 : 0);
    }

    private void writeInt(int i) throws IOException {
        byte[] buffer = new byte[4];
        buffer[0] = (byte) (i >>> 24);
        buffer[1] = (byte) (i >>> 16);
        buffer[2] = (byte) (i >>> 8);
        buffer[3] = (byte) (i);
        super.write(buffer);
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
        } else {
            writeInt(subStructures.length);
            for (SubStructure subStructure : subStructures) {
                writeSubStructure(subStructure);
            }
        }
    }
}
