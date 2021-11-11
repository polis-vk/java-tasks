package ru.mail.polis.homework.io.blocking;

import java.io.*;
import java.nio.ByteBuffer;

/**
 * Вам нужно реализовать StructureOutputStream, который умеет писать данные в файл.
 * Писать поля нужно ручками, с помощью массива байт и методов {@link #write(int)}, {@link #write(byte[])} и так далее
 * 3 балла
 */
public class StructureOutputStream extends FileOutputStream {

    public StructureOutputStream(File fileName) throws FileNotFoundException {
        super(fileName);
    }

    /**
     * Метод должен вернуть записать прочитанную структуру.
     */
    public void write(Structure structure) {
        if (structure == null) {
            return;
        }

        try {
            writeFlags(structure);
            writeLong(structure.getId());
            writeString(structure.getName());
            write(structure.getSubStructures());
            writeFloat(structure.getCoeff());
            write(structure.getParam());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод должен вернуть записать массив прочитанных структур.
     */
    public void write(Structure[] structures) throws IOException {
        if (structures == null) {
            return;
        }

        for (Structure structure: structures) {
            write(structure);
        }
    }

    private void writeFlags(Structure structure) throws IOException {
        if (structure == null) {
            return;
        }

        byte flags = 0b0000_0000;
        for (int i = 1; i <= 4; ++i) {
            flags += ((structure.isFlagI(i) ? 1 : 0) << (4 - i));
        }
        write(flags);
    }

    private void writeInt(int a) throws IOException {
        write(ByteBuffer.allocate(Integer.BYTES).putInt(a).array());
    }

    private void writeLong(long a) throws IOException {
        write(ByteBuffer.allocate(Long.BYTES).putLong(a).array());
    }

    private void writeFloat(float f) throws IOException {
        writeInt(Float.floatToIntBits(f));
    }

    private void writeDouble(double d) throws IOException {
        writeLong(Double.doubleToLongBits(d));
    }

    private void writeString(String s) throws IOException {
        if (s == null) {
            writeInt(-1);
            return;
        }
        writeInt(s.length());
        write(s.getBytes());
    }

    private void write(SubStructure subStructure) throws IOException {
        if (subStructure == null) {
            return;
        }

        writeInt(subStructure.getId());
        String subStructureName = subStructure.getName();
        writeString(subStructureName == null ? Structure.UNDEFINED_STRING : subStructureName);
        write(subStructure.isFlag() ? 1 : 0);
        writeDouble(subStructure.getScore());
    }

    private void write(SubStructure[] subStructures) throws IOException {
        if (subStructures == null) {
            writeInt(-1);
            return;
        }

        writeInt(subStructures.length);
        for (SubStructure subStructure: subStructures) {
            write(subStructure);
        }
    }

}