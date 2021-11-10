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

    public static byte[] long2ByteArray(long value) {
        return ByteBuffer.allocate(Long.BYTES).putLong(value).array();
    }

    public static byte[] float2ByteArray(float value) {
        return ByteBuffer.allocate(Float.BYTES).putFloat(value).array();
    }

    private byte[] int2ByteArray(int value) {
        return ByteBuffer.allocate(Integer.BYTES).putInt(value).array();
    }

    public static byte[] double2ByteArray(double value) {
        return ByteBuffer.allocate(Double.BYTES).putDouble(value).array();
    }

    /**
     * Метод должен вернуть записать прочитанную структуру.
     */
    public void write(Structure structure) throws IOException {
        writeLong(structure.getId());
        writeString(structure.getName());
        writeFloat(structure.getCoeff());
        write4BooleansAsInt(new boolean[]{structure.isFlag1(), structure.isFlag2(), structure.isFlag3(), structure.isFlag4()});
        writeInt(structure.getParam());
        writeSubStructures(structure.getSubStructures());
        flush();
    }

    private void writeString(String str) throws IOException {
        if (str != null) {
            writeInt(str.length());
            write(str.getBytes());
        } else {
            writeInt(-1);
        }
    }

    private void writeSubStructure(SubStructure subStructure) throws IOException {
        writeInt(subStructure.getId());
        writeString(subStructure.getName());
        writeDouble(subStructure.getScore());
        write(subStructure.isFlag() ? 1 : 0);
    }

    private void writeDouble(double value) throws IOException {
        write(double2ByteArray(value));
    }

    private void writeFloat(float value) throws IOException {
        write(float2ByteArray(value));
    }

    private void writeLong(long value) throws IOException {
        write(long2ByteArray(value));
    }

    private void writeInt(int value) throws IOException {
        write(int2ByteArray(value));
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

    public void write4BooleansAsInt(boolean[] value) throws IOException {
        byte res = 0;
        for (int i = 0; i < 4; i++) {
            res <<= 1;
            res |= value[i] ? 1 : 0;
        }
        write(res);
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
}
