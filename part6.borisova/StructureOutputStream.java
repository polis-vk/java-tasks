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
        write(longToBytes(structure.getId()));
        write(stringToBytes(structure.getName()));
        write(structure.getSubStructures());
        write(doubleToBytes(structure.getCoeff()));
        write(flagsToByte(structure));
        write(structure.getParam());
    }

    private byte flagsToByte(Structure structure) {
        byte flags = 0;
        flags += (structure.isFlag4() ? (byte) 1 : (byte) 0);
        flags <<= 1;
        flags += (structure.isFlag3() ? (byte) 1 : (byte) 0);
        flags <<= 1;
        flags += (structure.isFlag2() ? (byte) 1 : (byte) 0);
        flags <<= 1;
        flags += (structure.isFlag1() ? (byte) 1 : (byte) 0);
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

    private void write(SubStructure[] subStructures) throws IOException {
       if (subStructures == null || subStructures.length == 0) {
            write(intToBytes(0));
            return;
       }
       write(intToBytes(subStructures.length));
       for(int i = 0; i < subStructures.length; i++) {
           write(subStructures[i]);
       }
    }

    private void write(SubStructure subStructure) throws IOException {
        write(intToBytes(subStructure.getId()));
        write(stringToBytes(subStructure.getName()));
        write(subStructure.isFlag() ? (byte) 1 : (byte) 0);
        write(doubleToBytes(subStructure.getScore()));
    }

    private byte[] charToBytes(char input) {
        ByteBuffer buffer = ByteBuffer.allocate(Character.BYTES);
        buffer.putChar(input);
        return buffer.array();
    }

    private byte[] doubleToBytes(double input) {
        ByteBuffer buffer = ByteBuffer.allocate(Double.BYTES);
        buffer.putDouble(input);
        return buffer.array();
    }

    private byte[] intToBytes(int input) {
        ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES);
        buffer.putInt(input);
        return buffer.array();
    }

    private byte[] longToBytes(long input) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(input);
        return buffer.array();
    }

    private byte[] stringToBytes(String input) {
        if (input == null || input.equals("")) {
            return charToBytes('\0');
        }
        ByteBuffer buffer = ByteBuffer.allocate((input.length() + 1) * Character.BYTES);
        for(int i = 0; i < input.length(); i++) {
            buffer.put(charToBytes(input.charAt(i)));
        }
        buffer.put(charToBytes('\0'));
        return buffer.array();
    }


}
