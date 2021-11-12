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
        writeFloat(structure.getCoeff());
        boolean[] flags = {structure.isFlag1(), structure.isFlag2(), structure.isFlag3(), structure.isFlag4()};
        writeFlags(flags);
        writeByte(structure.getParam());
    }

    /**
     * Метод должен вернуть записать массив прочитанных структур.
     */
    public void write(Structure[] structures) throws IOException {

    }

    private void writeByte(byte value) throws IOException {
        write(value);
    }

    private void writeInt(int value) throws IOException {
        write(ByteBuffer.allocate(4).putInt(value).array());
    }

    private void writeLong(long value) throws IOException {
        write(ByteBuffer.allocate(8).putLong(value).array());
    }

    private void writeFloat(float value) throws IOException {
        write(ByteBuffer.allocate(4).putFloat(value).array());
    }

    private void writeDouble(double value) throws IOException {
        write(ByteBuffer.allocate(8).putDouble(value).array());
    }

    private void writeString(String value) throws IOException {
        if (value == null) {
            writeInt(-1);
            return;
        }
        byte[] byteStr = value.getBytes("UTF-8");
        writeInt(byteStr.length);
        write(byteStr);
    }

    private void writeBoolean(boolean value) throws IOException {
        write(value ? 1 : 0);
    }

    private void writeFlags(boolean[] flags) throws IOException{
        for (int i = 0; i < 4; i++) {
            writeBoolean(flags[i]);
        }
    }

    private void writeSubStructures(SubStructure[] subStructs) throws IOException {
        if (subStructs == null) {
            writeInt(-1);
            return;
        }

        writeInt(subStructs.length);
        for (int i = 0; i < subStructs.length; ++i) {
            writeInt(subStructs[i].getId());
            writeString(subStructs[i].getName());
            writeBoolean(subStructs[i].isFlag());
            writeDouble(subStructs[i].getScore());
        }
    }
}
