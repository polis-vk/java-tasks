package ru.mail.polis.homework.io.blocking;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

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

        writeSubstructures(structure.getSubStructures());

        writeFloat(structure.getCoeff());

        writeFlags(structure.isFlag1(), structure.isFlag2(), structure.isFlag3(), structure.isFlag4());

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

    private void writeSubstructure(SubStructure subStructure) throws IOException {
        writeInt(subStructure.getId());
        writeString(subStructure.getName());
        writeFlags(subStructure.isFlag());
        writeDouble(subStructure.getScore());
    }

    private void writeSubstructures(SubStructure[] subStructures) throws IOException {
        if (subStructures == null) {
            writeInt(-1);
            return;
        }
        writeInt(subStructures.length);
        for (SubStructure sub : subStructures) {
            writeSubstructure(sub);
        }
    }

    private void writeLong(long in) throws IOException {
        byte[] b = new byte[]{
                (byte) in,
                (byte) (in >>> 8),
                (byte) (in >>> 16),
                (byte) (in >>> 24),
                (byte) (in >>> 32),
                (byte) (in >>> 40),
                (byte) (in >>> 48),
                (byte) (in >>> 56)
        };
        write(b);
    }


    private void writeInt(int in) throws IOException {
        byte[] b = new byte[]{
                (byte) in,
                (byte) (in >>> 8),
                (byte) (in >>> 16),
                (byte) (in >>> 24)
        };
        write(b);
    }


    private void writeFlags(boolean... flags) throws IOException {
        if (flags.length > 8) {
            throw new IllegalArgumentException();
        }
        byte out = 0;
        for (int i = flags.length - 1; i >= 0; i--) {
            out |= (flags[i]) ? 0x1 << i : 0;
        }
        write(out);
    }

    private void writeString(String in) throws IOException {
        if (in == null) {
            writeInt(-1);
            return;
        }
        writeInt(in.length());
        write(in.getBytes(StandardCharsets.UTF_8));
    }

    private void writeDouble(double in) throws IOException {
        writeLong(Double.doubleToLongBits(in));
    }

    private void writeFloat(float in) throws IOException {
        writeInt(Float.floatToIntBits(in));
    }
}
