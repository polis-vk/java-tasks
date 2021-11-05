package ru.mail.polis.homework.io.blocking;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Вам нужно реализовать StructureInputStream, который умеет читать данные из файла.
 * Читать поля нужно ручками, с помощью массива байт и методов {@link #read()}, {@link #read(byte[])} и так далее
 * 3 балла
 */
public class StructureInputStream extends FileInputStream {

    private Structure[] structures;
    private int currentPosition = 0;

    public StructureInputStream(File fileName) throws FileNotFoundException {
        super(fileName);
        this.structures = new Structure[0];
    }

    /**
     * Метод должен вернуть следующую прочитанную структуру.
     * Если структур в файле больше нет, то вернуть null
     */
    public Structure readStructure() throws IOException {
        if (super.available() == 0) {
            return null;
        }
        Structure readableStructure = new Structure();

        readableStructure.setId(readLong());
        readableStructure.setName(readString());
        readableStructure.setSubStructures(readSubStructures());
        readableStructure.setCoeff(readFloat());
        readableStructure.setFlag1(readBoolean());
        readableStructure.setFlag2(readBoolean());
        readableStructure.setFlag3(readBoolean());
        readableStructure.setFlag4(readBoolean());
        readableStructure.setParam(readByte());

        if (currentPosition == structures.length) {
            resize();
        }
        structures[currentPosition++] = readableStructure;
            return readableStructure;
    }

    private void resize() {
        Structure[] newArray = new Structure[structures.length + 1];
        System.arraycopy(structures, 0, newArray, 0, structures.length);
        structures = newArray;
    }

    /**
     * Метод должен вернуть все структуры, которые есть в файле.
     * Если файл уже прочитан, то возвращается полный массив.
     */
    public Structure[] readStructures() throws IOException {
        while (super.available() != 0) {
            readStructure();
        }
        return structures;
    }

    private long readLong() throws IOException{
        byte[] buffer = new byte[8];
        if (super.read(buffer) < 0) {
            throw new EOFException();
        }
        return (((long) buffer[0] << 56) +
                ((long) (buffer[1] & 255) << 48) +
                ((long) (buffer[2] & 255) << 40) +
                ((long) (buffer[3] & 255) << 32) +
                ((long) (buffer[4] & 255) << 24) +
                ((buffer[5] & 255) << 16) +
                ((buffer[6] & 255) <<  8) +
                ((buffer[7] & 255)));
    }

    private String readString() throws IOException {
        int length = readInt();
        if (length < 0) {
            return null;
        }
        char[] charsOfString = new char[length];
        for (int i = 0; i < length; i++) {
            charsOfString[i] = readChar();
        }
        return String.valueOf(charsOfString);
    }

    private SubStructure[] readSubStructures() throws IOException {
        int size = readInt();
        if (size < 0) {
            return null;
        }
        SubStructure[] subStructures = new SubStructure[size];
        for (int i = 0; i < size; i++) {
            subStructures[i] = readSubStructure();
        }
        return subStructures;
    }

    private float readFloat() throws IOException {
        return Float.intBitsToFloat(readInt());
    }

    private boolean readBoolean() throws IOException{
        int ch = super.read();
        if (ch < 0)
            throw new EOFException();
        return (ch != 0);
    }

    private int readInt() throws IOException {
        int ch1 = super.read();
        int ch2 = super.read();
        int ch3 = super.read();
        int ch4 = super.read();
        if ((ch1 | ch2 | ch3 | ch4) < 0)
            throw new EOFException();
        return ((ch1 << 24) + (ch2 << 16) + (ch3 << 8) + (ch4));
    }

    private char readChar() throws IOException {
        int ch1 = super.read();
        int ch2 = super.read();
        if ((ch1 | ch2) < 0) {
            throw new EOFException();
        }
        return (char) ((ch1 << 8) + ch2);
    }

    private SubStructure readSubStructure() throws IOException {
        return new SubStructure(readInt(), readSubStructureString(), readBoolean(), readDouble());
    }

    private String readSubStructureString() throws IOException { // Строка в SubStructure NotNull
        String res = readString();
        if (res == null) {
            throw new EOFException();
        }
        return res;
    }

    private double readDouble() throws IOException {
        return Double.longBitsToDouble(readLong());
    }

    public final byte readByte() throws IOException {
        int ch = super.read();
        if (ch < 0)
            throw new EOFException();
        return (byte) ch;
    }
}
