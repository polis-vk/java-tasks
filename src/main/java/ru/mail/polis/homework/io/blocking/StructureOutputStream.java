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
        writeValue(structure.getId(), Long.BYTES);
        writeName(structure.getName());
        writeSubStructures(structure.getSubStructures());
        writeDouble(structure.getCoeff());
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
            writeValue(-1, Integer.BYTES);
            return;
        }
        writeValue(subStructures.length, Integer.BYTES);
        for (SubStructure subStructure : subStructures) {
            writeSubStructure(subStructure);
        }
    }

    private void writeSubStructure(SubStructure subStructure) throws IOException {
        writeValue(subStructure.getId(), Integer.BYTES);
        writeName(subStructure.getName());
        writeFlagsByte(new boolean[]{subStructure.isFlag()});
        writeDouble(subStructure.getScore());
    }

    private void writeName(String name) throws IOException {
        if (name == null) {
            writeValue(-1, Integer.BYTES);
            return;
        }
        writeValue(name.length(), Integer.BYTES);
        for (char ch : name.toCharArray()) {
            writeValue(ch, Character.BYTES);
        }
    }

    private void writeDouble(double d) throws IOException {
        writeValue(Double.doubleToLongBits(d), Double.BYTES);
    }

    private void writeValue(long value, int size) throws IOException {
        int shift = 0;
        byte[] bytes = new byte[size];
        for (int i = size - 1; i >= 0; i--) {
            bytes[i] = (byte) (value >>> shift);
            shift += 8;
        }
        write(bytes);
    }

    private void writeFlagsByte(boolean[] flags) throws IOException {
        int flagsByte = 0;
        //0 0 0 0 flags[0]...flags[3]
        for (int i = 0; i < flags.length; i++) {
            flagsByte |= ((flags[i] ? 1 : 0) << (flags.length - 1 - i));
        }
        write(flagsByte);
    }

}