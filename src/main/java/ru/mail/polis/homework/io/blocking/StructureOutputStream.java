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
        writeFlags(new boolean[]{structure.isFlag1(),
                structure.isFlag2(),
                structure.isFlag3(),
                structure.isFlag4()});
        write(structure.getParam());
        flush();
    }


    /**
     * Метод должен вернуть записать массив прочитанных структур.
     */
    public void write(Structure[] structures) throws IOException {
        for (Structure structure : structures) {
            write(structure);
        }
    }

    private void writeInt(int x) throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(Integer.BYTES);
        byteBuffer.putInt(x);
        write(byteBuffer.array());
    }

    private void writeDouble(double x) throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(Double.BYTES);
        byteBuffer.putDouble(x);
        write(byteBuffer.array());
    }

    private void writeLong(long x) throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(Long.BYTES);
        byteBuffer.putLong(x);
        write(byteBuffer.array());
    }

    private void writeFloat(float x) throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(Float.BYTES);
        byteBuffer.putFloat(x);
        write(byteBuffer.array());
    }

    private void writeBool(boolean x) throws IOException {
        write(x ? 1 : 0);
    }

    private void writeString(String x) throws IOException {
        if (x == null) {
            writeInt(-1);
            return;
        }
        writeInt(x.length());
        write(x.getBytes(StandardCharsets.UTF_8));
    }

    private void writeFlags(boolean[] flags) throws IOException {
        byte b = 0;
        for (int i = 0; i < flags.length; i++) {
            b += flags[i] ? (1 << i) : 0;
        }
        write(b);
    }

    private void writeSubStructure(SubStructure subStructure) throws IOException {
        writeInt(subStructure.getId());
        writeString(subStructure.getName());
        writeBool(subStructure.isFlag());
        writeDouble(subStructure.getScore());
    }

    private void writeSubstructures(SubStructure[] subStructures) throws IOException {
        if (subStructures == null) {
            writeInt(-1);
            return;
        }
        writeInt(subStructures.length);
        for (SubStructure substructure : subStructures) {
            writeSubStructure(substructure);
        }
    }
}
