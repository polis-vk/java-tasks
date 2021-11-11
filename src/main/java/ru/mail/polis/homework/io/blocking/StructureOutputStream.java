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
     * Метод должен записать прочитанную структуру.
     */
    public void write(Structure structure) throws IOException {
        if (structure == null) {
            return;
        }
        writeLong(structure.getId());
        writeString(structure.getName());
        writeSubStructures(structure.getSubStructures());
        writeFloat(structure.getCoeff());
        write(flagsToByte(structure.isFlag1(), structure.isFlag2(),
                structure.isFlag3(), structure.isFlag4()));
        write(structure.getParam());
        flush();
    }

    /**
     * Метод должен записать массив прочитанных структур.
     */
    public void write(Structure[] structures) throws IOException {
        if (structures == null) {
            return;
        }
        for (Structure temp : structures) {
            write(temp);
        }
    }

    private byte flagsToByte(boolean... flags) throws IOException {
        byte b = 0;
        for (int i = 0; i < flags.length; ++i) {
            b += (flags[i] ? 1 : 0) << i;
        }
        return b;
    }

    private void writeSubStructure(SubStructure sub) throws IOException {
        writeInt(sub.getId());
        writeString(sub.getName());
        writeBoolean(sub.isFlag());
        writeDouble(sub.getScore());
    }

    private void writeSubStructures(SubStructure[] subs) throws IOException {
        if (subs == null) {
            writeInt(-1);
            return;
        }
        writeInt(subs.length);
        for (SubStructure sub : subs) {
            writeSubStructure(sub);
        }
    }

    private void writeBoolean(boolean b) throws IOException {
        write(b ? 1 : 0);
    }

    private void writeInt(int i) throws IOException {
        write(ByteBuffer.allocate(Integer.BYTES).putInt(i).array());
    }

    private void writeLong(long l) throws IOException {
        write(ByteBuffer.allocate(Long.BYTES).putLong(l).array());
    }

    private void writeFloat(float f) throws IOException {
        write(ByteBuffer.allocate(Float.BYTES).putFloat(f).array());
    }

    private void writeDouble(double d) throws IOException {
        write(ByteBuffer.allocate(Double.BYTES).putDouble(d).array());
    }

    private void writeString(String s) throws IOException {
        if (s == null) {
            writeInt(-1);
            return;
        }

        writeInt(s.length());
        write(s.getBytes());
    }

}
