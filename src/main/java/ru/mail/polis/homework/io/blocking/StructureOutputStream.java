package ru.mail.polis.homework.io.blocking;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Вам нужно реализовать StructureOutputStream, который умеет писать данные в файл.
 * Писать поля нужно ручками, с помощью массива байт и методов {@link #write(int)}, {@link #write(byte[])} и так далее
 * 3 тугрика
 */
public class StructureOutputStream extends FileOutputStream {

    public StructureOutputStream(File name) throws FileNotFoundException {
        super(name);
    }

    /**
     * Метод должен вернуть записать прочитанную структуру.
     */
    public void write(Structure structure) throws IOException {
        if (structure == null) {
            return;
        }
        writeLong(structure.getId());
        writeString(structure.getName());
        writeSubstructures(structure.getSubStructures());
        writeFloat(structure.getCoeff());
        boolean[] flags = {structure.isFlag1(), structure.isFlag2(), structure.isFlag3(), structure.isFlag4()};
        writeFlags(flags);
        write(structure.getParam());
        flush();
    }

    /**
     * Метод должен вернуть записать массив прочитанных структур.
     */
    public void write(Structure[] structures) throws IOException {
        if (structures == null) {
            return;
        }
        for (Structure structure : structures) {
            write(structure);
        }
    }

    private void writeLong(long l) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(l);
        write(buffer.array());
    }

    private void writeInt(int i) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES);
        buffer.putInt(i);
        write(buffer.array());
    }

    private void writeString(String s) throws IOException {
        if (s == null) {
            writeInt(-1);
            return;
        }
        writeInt(s.length());
        write(s.getBytes());
    }

    private void writeBoolean(boolean b) throws IOException {
        write(b ? 1 : 0);
    }

    private void writeDouble(double d) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(Double.BYTES);
        buffer.putDouble(d);
        write(buffer.array());
    }

    private void writeSubstructure(SubStructure subStructure) throws IOException {
        writeInt(subStructure.getId());
        writeString(subStructure.getName());
        writeBoolean(subStructure.isFlag());
        writeDouble(subStructure.getScore());
    }

    private void writeSubstructures(SubStructure[] subStructures) throws IOException {
        if (subStructures == null) {
            writeInt(-1);
            return;
        }
        writeInt(subStructures.length);
        for (SubStructure subStructure : subStructures) {
            writeSubstructure(subStructure);
        }
    }

    private void writeFloat(Float f) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(Float.BYTES);
        buffer.putFloat(f);
        write(buffer.array());
    }

    private void writeFlags(boolean[] flags) throws IOException {
        byte flagsByte = 0;
        for (int i = 0; i < flags.length; i++) {
            flagsByte += flags[i] ? (1 << i) : 0;
        }
        write(flagsByte);
    }
}
