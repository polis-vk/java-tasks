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
        writeSubStructures(structure.getSubStructures());
        writeDouble(structure.getCoeff());
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
        for (int i = 0; i < structures.length; i++) {
            write(structures[i]);
        }
    }

    private void writeInt(int a) throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(4);
        byteBuffer.putInt(a);
        write(byteBuffer.array());
    }

    private void writeLong(long a) throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(8);
        byteBuffer.putLong(a);
        write(byteBuffer.array());
    }

    private void writeString(String s) throws IOException {
        if (s == null) {
            writeInt(-1);
            return;
        }
        writeInt(s.length());
        write(s.getBytes());
    }

    private void writeDouble(double a) throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(8);
        byteBuffer.putDouble(a);
        write(byteBuffer.array());
    }

    private void writeBoolean(boolean a) throws IOException {
        write(a ? 1 : 0);
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
        for (int i = 0; i < subStructures.length; i++) {
            writeSubStructure(subStructures[i]);
        }
    }
}
