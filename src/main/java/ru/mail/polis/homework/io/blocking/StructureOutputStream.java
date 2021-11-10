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
        writeFloat((float)structure.getCoeff());
        writeBooleans(structure.isFlag1(),
                structure.isFlag2(),
                structure.isFlag3(),
                structure.isFlag4());
        writeByte(structure.getParam());
    }

    /**
     * Метод должен вернуть записать массив прочитанных структур.
     */
    public void write(Structure[] structures) throws IOException {
        for (Structure structure : structures) {
            write(structure);
        }
    }

    private void writeLong(long id) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        write(buffer.putLong(id).array());
    }

    private void writeInt(int meta) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES);
        write(buffer.putInt(meta).array());
    }

    private void writeString(String name) throws IOException {
        if (name == null) {
            writeInt(-1);
            return;
        }

        writeInt(name.length());
        write(name.getBytes(StandardCharsets.UTF_8));
    }

    private void writeFloat(float coeff) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(Float.BYTES);
        write(buffer.putFloat(coeff).array());
    }

    private void writeDouble(double coeff) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(Double.BYTES);
        write(buffer.putDouble(coeff).array());
    }

    private void writeBooleans(boolean... flags) throws IOException {
        byte result = 0;
        for (int i = 0; i < flags.length; i++) {
            result += flags[i] ? 1 << i : 0;
        }
        write(result);
    }

    private void writeByte(byte value) throws IOException {
        write(value);
    }

    private void writeSubStructures(SubStructure[] structures) throws IOException {
        if (structures == null) {
            writeInt(-1);
            return;
        }

        writeInt(structures.length);
        for (SubStructure structure : structures) {
            writeSubStructure(structure);
        }
    }

    private void writeSubStructure(SubStructure structure) throws IOException {
        writeInt(structure.getId());
        writeString(structure.getName());
        writeBooleans(structure.isFlag());
        writeDouble(structure.getScore());
    }
}
