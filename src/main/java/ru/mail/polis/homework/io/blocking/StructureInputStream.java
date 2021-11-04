package ru.mail.polis.homework.io.blocking;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * Вам нужно реализовать StructureInputStream, который умеет читать данные из файла.
 * Читать поля нужно ручками, с помощью массива байт и методов {@link #read()}, {@link #read(byte[])} и так далее
 * 3 балла
 */
public class StructureInputStream extends FileInputStream {
    private int index;
    private Structure[] structures;

    public StructureInputStream(File fileName) throws FileNotFoundException {
        super(fileName);
        structures = new Structure[0];
        index = 0;
    }

    private void structuresBuilder() {
        structures = Arrays.copyOf(structures, structures.length + 1);
    }

    /**
     * Метод должен вернуть следующую прочитанную структуру.
     * Если структур в файле больше нет, то вернуть null
     */
    public Structure readStructure() throws IOException {
        if (isEnd()) {
            return null;
        }
        structuresBuilder();
        Structure structure = new Structure();
        structure.setId(readLong());
        structure.setName(readString());
        structure.setSubStructures(readSubstructures());
        structure.setCoeff(readDouble());
        structure.setFlag1(readBoolean());
        structure.setFlag2(readBoolean());
        structure.setFlag3(readBoolean());
        structure.setFlag4(readBoolean());
        structure.setParam((byte) super.read());
        structures[index++] = structure;
        return structure;
    }

    private boolean isEnd() throws IOException {
        return super.read() == -1;
    }

    /**
     * Метод должен вернуть все структуры, которые есть в файле.
     * Если файл уже прочитан, но возвращается полный массив.
     */
    public Structure[] readStructures() throws IOException {
        Structure readStructure = readStructure();
        while (readStructure != null) {
            readStructure = readStructure();
        }
        return structures;
    }

    private long readLong() throws IOException {
        byte[] b = super.readNBytes(8);
        return ((long) b[7] << 56)
                | ((long) b[6] & 0xff) << 48
                | ((long) b[5] & 0xff) << 40
                | ((long) b[4] & 0xff) << 32
                | ((long) b[3] & 0xff) << 24
                | ((long) b[2] & 0xff) << 16
                | ((long) b[1] & 0xff) << 8
                | ((long) b[0] & 0xff);
    }

    private int readInt() throws IOException {
        byte[] b = super.readNBytes(4);
        return ((int) b[3] & 0xff) << 24
                | ((int) b[2] & 0xff) << 16
                | ((int) b[1] & 0xff) << 8
                | ((int) b[0] & 0xff);
    }

    private String readString() throws IOException {
        int length = readInt();
        if (length == -1) {
            return null;
        }
        return new String(readNBytes(length), StandardCharsets.UTF_8);
    }

    private SubStructure[] readSubstructures() throws IOException {
        int subsAmount = readInt();
        if (subsAmount == -1) {
            return null;
        }
        SubStructure[] subStructures = new SubStructure[subsAmount];
        for (int i = 0; i < subsAmount; i++) {
            subStructures[i] = readSubstructure();
        }
        return subStructures;
    }

    private boolean readBoolean() throws IOException {
        return super.read() == 1;
    }

    private double readDouble() throws IOException {
        return Double.longBitsToDouble(readLong());
    }

    private SubStructure readSubstructure() throws IOException {
        return new SubStructure(readInt(), readString(), readBoolean(), readDouble());
    }
}
