package ru.mail.polis.homework.io.blocking;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

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

    private void writeInt(int i) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES);
        write(buffer.putInt(i).array());
    }

    private void writeLong(long l) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        write(buffer.putLong(l).array());
    }

    private void writeDouble(double d) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(Double.BYTES);
        write(buffer.putDouble(d).array());
    }

    private void writeFloat(float f) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(Float.BYTES);
        write(buffer.putFloat(f).array());
    }

    private void writeBoolean(boolean bool) throws IOException {
        write(bool ? 1 : 0);
    }

    private void writeString(String str) throws IOException {
        if (str == null) {
            writeInt(-1);
            return;
        }
        writeInt(str.length());
        write(str.getBytes(StandardCharsets.UTF_8));
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
}
