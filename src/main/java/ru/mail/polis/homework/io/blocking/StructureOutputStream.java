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

        writeFloat(structure.getCoeff());
        writeFlags(structure);

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
        write(buffer);
    }

    private void writeString(String s) throws IOException {
        if (s == null) {
            writeInt(-1);
            return;
        }

        byte[] bytes = s.getBytes();
        writeInt(bytes.length);
        write(bytes);
    }

    private void writeDouble(double d) throws IOException {
        writeLong(Double.doubleToLongBits(d));
    }

    private void writeFloat(float f) throws IOException {
        writeInt(Float.floatToIntBits(f));
    }

    private void writeBoolean(boolean b) throws IOException {
        write(b ? 1 : 0);
    }

    private void writeFlags(Structure structure) throws IOException {
        write((byte) ((boolToNumber(structure.isFlag1()) << 3) + (boolToNumber(structure.isFlag2()) << 2)
                + (boolToNumber(structure.isFlag3()) << 1) + boolToNumber(structure.isFlag4())));
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
        write(buffer);
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
}
