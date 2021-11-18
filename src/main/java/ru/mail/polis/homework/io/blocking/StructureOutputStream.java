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
        long id = structure.getId();
        String name = structure.getName();
        float coeff = structure.getCoeff();
        byte param = structure.getParam();
        boolean flag1 = structure.isFlag1();
        boolean flag2 = structure.isFlag2();
        boolean flag3 = structure.isFlag3();
        boolean flag4 = structure.isFlag4();
        SubStructure[] subStructures = structure.getSubStructures();
        write(id);
        write(name);
        write(coeff);
        write(param);
        write(flag1, flag2, flag3, flag4);
        if (subStructures == null) {
            writeInt(-1);
        } else {
            writeInt(subStructures.length);
            for (SubStructure subStructure : subStructures) {
                write(subStructure);
            }
        }
    }

    /**
     * Метод должен вернуть записать массив прочитанных структур.
     */
    public void write(Structure[] structures) throws IOException {
        for (Structure structure : structures) {
            write(structure);
        }
    }

    private void write(SubStructure subStructure) throws IOException {
        int id = subStructure.getId();
        String name = subStructure.getName();
        boolean flag = subStructure.isFlag();
        double score = subStructure.getScore();
        writeInt(id);
        write(name);
        write(flag ? 1 : 0);
        write(score);
    }

    private void writeInt(int intData) throws IOException {
        byte[] buffer = ByteBuffer.allocate(Integer.SIZE / 8).putInt(intData).array();
        write(buffer);
    }

    private void write(String stringData) throws IOException {
        int length;
        if (stringData == null) {
            length = -1;
            writeInt(length);
        } else {
            length = stringData.length();
            writeInt(length);
            write(stringData.getBytes(StandardCharsets.UTF_8));
        }
    }

    private void write(long longData) throws IOException {
        byte[] buffer = ByteBuffer.allocate(Long.SIZE / 8).putLong(longData).array();
        write(buffer);
    }

    private void write(float floatData) throws IOException {
        byte[] buffer = ByteBuffer.allocate(Float.SIZE / 8).putFloat(floatData).array();
        write(buffer);
    }

    private void write(double doubleData) throws IOException {
        byte[] buffer = ByteBuffer.allocate(Double.SIZE / 8).putDouble(doubleData).array();
        write(buffer);
    }

    private void write(boolean... flags) throws IOException {
        int buffer = 0;
        for (boolean flag : flags) {
            buffer = buffer << 1;
            buffer += flag ? 1 : 0;
        }
        write(buffer);
    }
}
