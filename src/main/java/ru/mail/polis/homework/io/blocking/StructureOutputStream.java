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
        writeLong(structure.getId());
        writeString(structure.getName());
        writeSubStructures(structure.getSubStructures());
        writeFloat(structure.getCoeff());
        writeBoolean(structure.isFlag1());
        writeBoolean(structure.isFlag2());
        writeBoolean(structure.isFlag3());
        writeBoolean(structure.isFlag4());
        write(structure.getParam());
    }

    /**
     * Метод должен вернуть записать массив прочитанных структур.
     */
    public void write(Structure[] structures) throws IOException {
        for (Structure structure:structures) {
           write(structure);
        }

    }

    private void writeLong(long l) throws IOException {
        ByteBuffer b = ByteBuffer.allocate(8);
        b.putLong(l);
        write(b.array());
    }

    private void writeString(String s) throws IOException {
        if (s == null) {
            writeInt(-1);
            return;
        }
        write(s.getBytes());
    }

    private void writeInt(int i) throws IOException{
        ByteBuffer b = ByteBuffer.allocate(4);
        b.putInt(i);
        write(b.array());
    }

    private void writeBoolean(boolean f) throws  IOException{
        write((byte)(f?1:0));
    }

    private void writeFloat(float f) throws  IOException{
        ByteBuffer b = ByteBuffer.allocate(4);
        b.putFloat(f);
        write(b.array());
    }

    private void writeDouble(double d) throws  IOException{
        ByteBuffer b = ByteBuffer.allocate(8);
        b.putDouble(d);
        write(b.array());
    }

    private void writeSubStructures(SubStructure[] subStructures) throws IOException{
        writeInt(subStructures.length);
        for (SubStructure subStructure: subStructures) {
            writeInt(subStructure.getId());
            writeString(subStructure.getName());
            writeBoolean(subStructure.isFlag());
            writeDouble(subStructure.getScore());
        }
    }


}
