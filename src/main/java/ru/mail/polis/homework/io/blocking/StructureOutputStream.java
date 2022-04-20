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
            this.write(longToBytes(structure.getId()));
            if (structure.getName() != null) {
                this.write(intToBytes(structure.getName().length()));
                this.write(structure.getName().getBytes(StandardCharsets.UTF_8));
            } else {
                this.write(intToBytes(-1));
            }
            this.write(intToBytes(Float.floatToIntBits(structure.getCoeff())));
            this.write(new byte[]{flagsToByte(structure)});
            this.write(new byte[]{structure.getParam()});
            SubStructure[] subStructures = structure.getSubStructures();
            if (subStructures == null) {
                this.write(intToBytes(-1));
                return;
            }
            this.write(intToBytes(subStructures.length));
            for (SubStructure subStructure : subStructures) {
                this.write(intToBytes(subStructure.getId()));
                this.write(intToBytes(subStructure.getName().length()));
                this.write(subStructure.getName().getBytes(StandardCharsets.UTF_8));
                this.write(new byte[]{(byte) (subStructure.isFlag() ? 1 : 0)});
                this.write(longToBytes(Double.doubleToLongBits(subStructure.getScore())));
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
    public void write(Structure[] structures) {
        for (Structure structure : structures) {
            write(structure);
        }
    }
}
