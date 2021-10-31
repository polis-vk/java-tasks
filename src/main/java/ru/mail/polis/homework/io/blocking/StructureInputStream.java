package ru.mail.polis.homework.io.blocking;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static ru.mail.polis.homework.io.blocking.StructureOutputStream.NULL_OBJECT;

/**
 * Вам нужно реализовать StructureInputStream, который умеет читать данные из файла.
 * Читать поля нужно ручками, с помощью массива байт и методов {@link #read()}, {@link #read(byte[])} и так далее
 * 3 балла
 */
public class StructureInputStream extends FileInputStream {

    private static Structure[] structures;

    public StructureInputStream(File fileName) throws FileNotFoundException {
        super(fileName);
        structures = new Structure[0];
    }


    /**
     * Метод должен вернуть следующую прочитанную структуру.
     * Если структур в файле больше нет, то вернуть null
     */
    public Structure readStructure() throws IOException {
        if (available() == 0) {
            return null;
        }

        long id = readLong();
        int sizeString = readInt();
        String name = readString(sizeString);
        SubStructure[] subStructures = readSubStructures();
        double coeff = readDouble();
        boolean flag1 = readBoolean();
        boolean flag2 = readBoolean();
        boolean flag3 = readBoolean();
        boolean flag4 = readBoolean();
        byte param = (byte) read();

        return addElementInStructure(new Structure(id, name, subStructures, coeff, flag1, flag2, flag3, flag4, param));
    }

    /**
     * Метод должен вернуть все структуры, которые есть в файле.
     * Если файл уже прочитан, но возвращается полный массив.
     */
    public Structure[] readStructures() throws IOException {
        if (available() == 0) {
            return structures;
        }
        while (available() != 0) {
            readStructure();
        }
        return structures;
    }

    private SubStructure readSubStructure() throws IOException {
        if (available() == 0) {
            return null;
        }
        int id = readInt();
        int sizeName = readInt();
        String name = readString(sizeName);
        boolean flag = readBoolean();
        double score = readDouble();
        return new SubStructure(id, name, flag, score);
    }

    private SubStructure[] readSubStructures() throws IOException {
        if (available() == 0) {
            return null;
        }
        int size = readInt();
        if (size == 0) {
            int sizeWord = readInt();
            if (sizeWord == NULL_OBJECT.length()) {
                readString(sizeWord);
                return null;
            }
            readString(sizeWord);
            return new SubStructure[0];
        }

        List<SubStructure> subStructuresList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            subStructuresList.add(readSubStructure());
        }

        return subStructuresList.toArray(new SubStructure[0]);
    }

    private long readLong() throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        byte[] bytes = new byte[Long.BYTES];
        read(bytes);
        buffer.put(bytes);
        buffer.flip();
        return buffer.getLong();
    }

    private int readInt() throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES);
        byte[] bytes = new byte[Integer.BYTES];
        read(bytes);
        buffer.put(bytes);
        buffer.flip();
        return buffer.getInt();
    }

    private double readDouble() throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(Double.BYTES);
        byte[] bytes = new byte[Double.BYTES];
        read(bytes);
        buffer.put(bytes);
        buffer.flip();
        return buffer.getDouble();
    }

    private String readString(int length) throws IOException {
        byte[] bytes = new byte[length];
        String inputString = new String(bytes);
        if (length == 4 && inputString.charAt(0) != '\"') {
            return null;
        }
        return inputString.substring(1, length - 1);
    }

    private boolean readBoolean() throws IOException {
        return (byte) read() == 1;
    }

    private Structure addElementInStructure(Structure structure) {
        Structure[] copyOfStructures = Arrays.copyOf(structures, structures.length);
        structures = new Structure[copyOfStructures.length + 1];
        System.arraycopy(copyOfStructures, 0, structures,
                0, copyOfStructures.length);
        structures[structures.length - 1] = structure;
        return structure;
    }

}
