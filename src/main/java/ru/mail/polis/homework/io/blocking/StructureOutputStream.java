package ru.mail.polis.homework.io.blocking;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.io.IOException;
import java.io.FileNotFoundException;

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
        writeSubStructures(structure.getSubStructures());
        writeFloat(structure.getCoeff());
        writeBoolean(structure.isFlag1());
        writeBoolean(structure.isFlag2());
        writeBoolean(structure.isFlag3());
        writeBoolean(structure.isFlag4());
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

    private void writeBoolean(boolean b) throws IOException {
        write(b ? 1 : 0);
    }

    private void writeInt(int i) throws IOException {
        write(ByteBuffer.allocate(4).putInt(i).array());
    }

    private void writeFloat(float f) throws IOException {
        write(ByteBuffer.allocate(4).putFloat(f).array());
    }

    private void writeLong(long l) throws IOException {
        write(ByteBuffer.allocate(8).putLong(l).array());
    }

    private void writeDouble(double d) throws IOException {
        write(ByteBuffer.allocate(8).putDouble(d).array());
    }

    private void writeString(String s) throws IOException {
        if (s == null) {
            writeInt(-1);
            return;
        }

        writeInt(s.length());
        write(s.getBytes());
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
