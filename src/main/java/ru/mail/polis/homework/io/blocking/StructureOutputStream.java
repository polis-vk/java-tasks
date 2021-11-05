package ru.mail.polis.homework.io.blocking;

import java.io.*;

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
        super.write(toByte(structure.getId()));
        super.write(toByte(structure.getCoeff()));
        super.write(toByte(structure.isFlag1(),
                structure.isFlag2(),
                structure.isFlag3(),
                structure.isFlag4()));
        super.write(structure.getParam());
        writeString(structure.getName());

        if (structure.getSubStructures() != null) {
            super.write(0);
            writeInt(structure.getSubStructures().length);
            for (SubStructure subStructure : structure.getSubStructures()) {
                writeSubStructure(subStructure);
            }
        } else {
            super.write(1);
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

    private void writeSubStructure(SubStructure subStructure) throws IOException {
        writeInt(subStructure.getId());
        writeString(subStructure.getName());
        super.write(toByte(subStructure.getScore()));
        super.write(toByte(subStructure.isFlag()));
    }

    private void writeString(String string) throws IOException {
        if (string == null) {
            super.write(1);
        } else {
            super.write(0);
            writeInt(string.getBytes().length);
            super.write(string.getBytes());
        }
    }

    private static byte[] toByte(int data) {
        return new byte[]{
                (byte) ((data >> 24) & 0xff),
                (byte) ((data >> 16) & 0xff),
                (byte) ((data >> 8) & 0xff),
                (byte) ((data) & 0xff),
        };
    }

    private void writeInt(int data) throws IOException {
        byte[] buffer = toByte(data);
        super.write(buffer);
    }

    private static byte[] toByte(boolean flag1, boolean flag2, boolean flag3, boolean flag4) {
        byte[] result = new byte[]{
                (byte) (0),
        };
        if (flag1) {
            result[0] |= 1 << 7;
        }
        if (flag2) {
            result[0] |= 1 << 6;
        }
        if (flag3) {
            result[0] |= 1 << 5;
        }
        if (flag4) {
            result[0] |= 1 << 4;
        }
        return result;
    }


    private static byte[] toByte(boolean flag) {
        byte[] result = new byte[]{
                (byte) (0),
        };
        if (flag) {
            result[0] |= 1 << 7;
        }
        return result;
    }

    private static byte[] toByte(long data) {
        return new byte[]{
                (byte) ((data >> 56) & 0xff),
                (byte) ((data >> 48) & 0xff),
                (byte) ((data >> 40) & 0xff),
                (byte) ((data >> 32) & 0xff),
                (byte) ((data >> 24) & 0xff),
                (byte) ((data >> 16) & 0xff),
                (byte) ((data >> 8) & 0xff),
                (byte) (data & 0xff),
        };
    }

    private static byte[] toByte(double data) {
        return toByte(Double.doubleToRawLongBits(data));
    }


}
