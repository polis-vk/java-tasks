package ru.mail.polis.homework.io.blocking;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

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
    public void write(Structure structure) {
        try {
            write(longToBytes(structure.getId()));
            if (structure.getName() != null) {
                write(intToBytes(structure.getName().length()));
                write(structure.getName().getBytes(StandardCharsets.UTF_8));
            } else {
                write(intToBytes(-1));
            }
            write(intToBytes(Float.floatToIntBits(structure.getCoeff())));
            write(new byte[]{flagsToByte(structure)});
            write(new byte[]{structure.getParam()});
            SubStructure[] subStructures = structure.getSubStructures();
            if (subStructures == null) {
                write(intToBytes(-1));
                return;
            }
            write(intToBytes(subStructures.length));
            for (SubStructure subStructure : subStructures) {
                write(intToBytes(subStructure.getId()));
                write(intToBytes(subStructure.getName().length()));
                write(subStructure.getName().getBytes(StandardCharsets.UTF_8));
                write(new byte[]{(byte) (subStructure.isFlag() ? 1 : 0)});
                write(longToBytes(Double.doubleToLongBits(subStructure.getScore())));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private byte[] longToBytes(long number) {
        return new byte[]{
                (byte) number,
                (byte) (number >>> 8),
                (byte) (number >>> 16),
                (byte) (number >>> 24),
                (byte) (number >>> 32),
                (byte) (number >>> 40),
                (byte) (number >>> 48),
                (byte) (number >>> 56)};
    }

    private byte[] intToBytes(int number) {
        return new byte[]{
                (byte) number,
                (byte) (number >>> 8),
                (byte) (number >>> 16),
                (byte) (number >>> 24)};
    }

    private byte flagsToByte(Structure structure) {
        byte flags = 0;
        flags += structure.isFlag1() ? 1 : 0;
        flags <<= 1;
        flags += structure.isFlag2() ? 1 : 0;
        flags <<= 1;
        flags += structure.isFlag3() ? 1 : 0;
        flags <<= 1;
        flags += structure.isFlag4() ? 1 : 0;
        return flags;
    }

    /**
     * Метод должен вернуть записать массив прочитанных структур.
     */
    public void write(Structure[] structures) throws IOException {
        for (Structure structure : structures) {
            write(structure);
        }
    }
}
