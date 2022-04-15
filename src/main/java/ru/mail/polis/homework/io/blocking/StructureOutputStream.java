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
        writeForLong(structure.getId());
        writeForString(structure.getName());
        writeForSubStructures(structure.getSubStructures());
        writeForDouble(structure.getCoeff());
        writeForBoolean(structure.isFlag1());
        writeForBoolean(structure.isFlag2());
        writeForBoolean(structure.isFlag3());
        writeForBoolean(structure.isFlag4());
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

    private void writeForBoolean(boolean b) throws IOException {
        write(b ? 1 : 0);
    }

    private void writeForLong(long l) throws IOException {
        ByteBuffer buf = ByteBuffer.allocate(Long.BYTES);
        buf.putLong(l);
        write(buf.array());
    }

    private void writeForDouble(double d) throws IOException {
        ByteBuffer buf = ByteBuffer.allocate(Double.BYTES);
        buf.putDouble(d);
        write(buf.array());
    }

    private void writeForString(String str) throws IOException {
        if (str == null) {
            writeForLong(-1);
            return;
        }
        writeForLong(str.length());
        write(str.getBytes());
    }

    private void writeForSubStructure(SubStructure subStructure) throws IOException {
        writeForLong(subStructure.getId());
        writeForString(subStructure.getName());
        writeForBoolean(subStructure.isFlag());
        writeForDouble(subStructure.getScore());
    }

    private void writeForSubStructures(SubStructure[] subStructures) throws IOException {
        if (subStructures == null) {
            writeForLong(-1);
            return;
        }
        writeForLong(subStructures.length);
        for (SubStructure subStructure : subStructures) {
            writeForSubStructure(subStructure);
        }
    }
}