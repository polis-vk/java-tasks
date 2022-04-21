package ru.mail.polis.homework.io.blocking;

import java.io.*;
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
        writeSubStructures(structure.getSubStructures());
        writeDouble(structure.getCoeff());
        boolean[] flags = {structure.isFlag1(), structure.isFlag2(), structure.isFlag3(), structure.isFlag4()};
        writeSeveralBoolean(flags);
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

    private void writeSeveralBoolean(boolean[] b) throws IOException {
        byte buf = 0;
        for (int i = 0; i < b.length; i++) {
            buf = (byte) (b[i] ? 1 << i : 0);
        }
        write(buf);
    }

    private void writeInt(int i) throws IOException {
        ByteArrayOutputStream buf = new ByteArrayOutputStream(Integer.BYTES);
        buf.write(i);
        write(buf.toByteArray());
    }

    private void writeLong(long l) throws IOException {
        ByteBuffer buf = ByteBuffer.allocate(Long.BYTES);
        buf.putLong(l);
        write(buf.array());
    }

    private void writeDouble(double d) throws IOException {
        ByteBuffer buf = ByteBuffer.allocate(Double.BYTES);
        buf.putDouble(d);
        write(buf.array());
    }

    private void writeString(String str) throws IOException {
        if (str == null) {
            writeInt(-1);
            return;
        }
        writeInt(str.length());
        write(str.getBytes());
    }

    private void writeSubStructure(SubStructure subStructure) throws IOException {
        writeLong(subStructure.getId());
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