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
        writeName(structure.getName());
        writeSubStructures(structure.getSubStructures());
        writeFloat(structure.getCoeff());
        writeFlagsByte(new boolean[]{
                structure.isFlag1(),
                structure.isFlag2(),
                structure.isFlag3(),
                structure.isFlag4()
        });
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
            writeInt(-1);
            return;
        }
        writeInt(subStructures.length);
        for (SubStructure subStructure : subStructures) {
            writeSubStructure(subStructure);
        }
    }

    private void writeSubStructure(SubStructure subStructure) throws IOException {
        writeInt(subStructure.getId());
        writeName(subStructure.getName());
        write(subStructure.isFlag() ? 1 : 0);
        writeDouble(subStructure.getScore());
    }

    private void writeName(String name) throws IOException {
        if (name == null) {
            writeInt(-1);
            return;
        }
        byte[] bytes = name.getBytes();
        writeInt(bytes.length);
        write(bytes);
    }

    private void writeDouble(double d) throws IOException {
        writeLong(Double.doubleToLongBits(d));
    }

    private void writeFloat(float value) throws IOException {
        writeInt(Float.floatToIntBits(value));
    }

    private void writeInt(int value) throws IOException {
        byte[] bytes = new byte[]{
                (byte) (value >>> 24),
                (byte) (value >>> 16),
                (byte) (value >>> 8),
                (byte) (value)
        };
        write(bytes);
    }

    private void writeLong(long value) throws IOException {
        byte[] bytes = new byte[]{
                (byte) (value >>> 56),
                (byte) (value >>> 48),
                (byte) (value >>> 40),
                (byte) (value >>> 32),
                (byte) (value >>> 24),
                (byte) (value >>> 16),
                (byte) (value >>> 8),
                (byte) (value)
        };
        write(bytes);
    }

    private void writeFlagsByte(boolean[] flags) throws IOException {
        int flagsByte = 0;
        for (int i = 0; i < flags.length; i++) {
            flagsByte |= ((flags[i] ? 1 : 0) << (flags.length - 1 - i));
        }
        write(flagsByte);
    }

}