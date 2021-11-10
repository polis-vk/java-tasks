package ru.mail.polis.homework.io.blocking;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

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
        writeSub(structure.getSubStructures());
        writeFloat(structure.getCoeff());
        boolean[] flags = new boolean[] { structure.isFlag1(), structure.isFlag2(), structure.isFlag3(), structure.isFlag4() };
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

    private void writeSub(SubStructure subStructure) throws IOException {
        writeInt(subStructure.getId());
        writeString(subStructure.getName());
        writeBoolean(subStructure.isFlag());
        writeDouble(subStructure.getScore());
    }

    private void writeSub(SubStructure[] subStructures) throws IOException {
        if (subStructures == null) {
            writeInt(-1);
            return;
        }
        writeInt(subStructures.length);
        for (SubStructure subStructure : subStructures) {
            writeSub(subStructure);
        }
    }

    private void writeInt(int i) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.putInt(i);
        write(buffer.array());
    }

    private void writeLong(long i) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.putLong(i);
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

    private void writeFlags(boolean[] booleansFlags) throws IOException {
        byte value = 0;
        for (int i = 0; i < booleansFlags.length; i++) {
            value += booleansFlags[i] ? (1 << i) : 0;
        }
        write(value);
    }

    private void writeBoolean(boolean b) throws IOException {
        write(b ? 1 : 0);
    }

    private void writeDouble(double d) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.putDouble(d);
        write(buffer.array());
    }

    private void writeFloat(float f) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.putFloat(f);
        write(buffer.array());
    }
}
