package ru.mail.polis.homework.io.blocking;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Вам нужно реализовать StructureOutputStream, который умеет писать данные в файл.
 * Писать поля нужно ручками, с помощью массива байт и методов {@link #write(int)}, {@link #write(byte[])} и так далее
 * 3 балла
 */
public class StructureOutputStream extends FileOutputStream {
    public static final String STRUCTURE_BEGIN = "Structure";
    public static final Charset STRING_CHARSET = StandardCharsets.UTF_8;

    public StructureOutputStream(File name) throws FileNotFoundException {
        super(name);
    }

    /**
     * Метод должен вернуть записать прочитанную структуру.
     */
    public void write(Structure structure) throws IOException {
        writeString(STRUCTURE_BEGIN);
        writeLong(structure.getId());
        writeString(structure.getName());
        writeSubStructures(structure.getSubStructures());
        writeDouble(structure.getCoeff());
        writeBoolean(structure.isFlag1());
        writeBoolean(structure.isFlag2());
        writeBoolean(structure.isFlag3());
        writeBoolean(structure.isFlag4());
        super.write(structure.getParam());
    }

    /**
     * Метод должен вернуть записать массив прочитанных структур.
     */
    public void write(Structure[] structures) throws IOException {
        for(Structure structure : structures) {
            write(structure);
        }
    }

    private void writeSubStructures(SubStructure[] subStructures) throws IOException {
        if(subStructures == null) {
            writeInt(0);
            return;
        }

        writeInt(subStructures.length);
        for(SubStructure subStructure : subStructures) {
            writeSubStructure(subStructure);
        }
    }

    private void writeSubStructure(SubStructure subStructure) throws IOException {
        writeInt(subStructure.getId());
        writeString(subStructure.getName());
        writeBoolean(subStructure.isFlag());
        writeDouble(subStructure.getScore());
    }

    private void writeLong(long val) throws IOException {
        ByteBuffer buff = ByteBuffer.allocate(Long.BYTES);
        buff.putLong(val);
        super.write(buff.array());
    }

    private void writeInt(int val) throws IOException {
        ByteBuffer buff = ByteBuffer.allocate(Integer.BYTES);
        buff.putInt(val);
        super.write(buff.array());
    }

    private void writeString(String str) throws IOException {
        if(str == null) {
            writeInt(0);
            return;
        }

        byte[] strBuff = str.getBytes(STRING_CHARSET);
        writeInt(strBuff.length);
        super.write(strBuff);
    }

    private void writeDouble(double val) throws IOException {
        ByteBuffer buff = ByteBuffer.allocate(Double.BYTES);
        buff.putDouble(val);
        super.write(buff.array());
    }

    private void writeBoolean(boolean val) throws IOException {
        super.write(val ? 1 : 0);
    }
}