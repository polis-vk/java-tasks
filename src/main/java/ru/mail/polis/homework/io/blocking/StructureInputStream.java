package ru.mail.polis.homework.io.blocking;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

/**
 * Вам нужно реализовать StructureInputStream, который умеет читать данные из файла.
 * Читать поля нужно ручками, с помощью массива байт и методов {@link #read()}, {@link #read(byte[])} и так далее
 * 3 балла
 */
public class StructureInputStream extends FileInputStream {

    private final ArrayList<Structure> structures = new ArrayList<>();

    public StructureInputStream(File fileName) throws FileNotFoundException {
        super(fileName);
    }


    /**
     * Метод должен вернуть следующую прочитанную структуру.
     * Если структур в файле больше нет, то вернуть null
     */
    public Structure readStructure() throws IOException {
        if (available() == 0) {
            return null;
        }
        long id = bytesToLong();
        int length = bytesToInt();
        String name;
        if (length == -1) {
            name = null;
        } else {
            name = bytesToString(length);
        }
        double coeff = bytesToDouble();
        boolean flag1 = bytesToBool();
        boolean flag2 = bytesToBool();
        boolean flag3 = bytesToBool();
        boolean flag4 = bytesToBool();
        byte param = (byte) read();
        int sLength = bytesToInt();
        SubStructure[] subStructures;
        if (sLength == -1) {
            subStructures = null;
        } else {
            subStructures = new SubStructure[sLength];
            for (int i = 0; i < sLength; i++) {
                subStructures[i] = readSubStructure();
            }
        }
        Structure structure = new Structure();
        structure.setId(id);
        structure.setName(name);
        structure.setCoeff((float) coeff);
        structure.setFlag1(flag1);
        structure.setFlag2(flag2);
        structure.setFlag3(flag3);
        structure.setFlag4(flag4);
        structure.setParam(param);
        structure.setSubStructures(subStructures);
        structures.add(structure);
        return structure;
    }

    /**
     * Метод должен вернуть все структуры, которые есть в файле.
     * Если файл уже прочитан, но возвращается полный массив.
     */
    public Structure[] readStructures() throws IOException {
        if (structures.isEmpty()) {
            if (available() == 0) {
                return new Structure[0];
            }
            while (available() != 0) {
                readStructure();
            }
        }
        return structures.toArray(new Structure[0]);
    }

    public SubStructure readSubStructure() throws IOException {
        int id = bytesToInt();
        int length = bytesToInt();
        String name = bytesToString(length);
        boolean flag = bytesToBool();
        double score = bytesToDouble();
        return new SubStructure(id, name, flag, score);
    }

    public long bytesToLong() throws IOException {
        byte[] bytes = new byte[Long.BYTES];
        read(bytes);
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.put(bytes);
        buffer.flip();
        return buffer.getLong();
    }

    public double bytesToDouble() throws IOException {
        byte[] bytes = new byte[Double.BYTES];
        read(bytes);
        ByteBuffer buffer = ByteBuffer.allocate(Double.BYTES);
        buffer.put(bytes);
        buffer.flip();
        return buffer.getDouble();
    }

    public int bytesToInt() throws IOException {
        byte[] bytes = new byte[Integer.BYTES];
        read(bytes);
        ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES);
        buffer.put(bytes);
        buffer.flip();
        return buffer.getInt();
    }

    public String bytesToString(int length) throws IOException {
        byte[] bytes = new byte[length];
        read(bytes);
        return new String(bytes);
    }

    private boolean bytesToBool() throws IOException {
        return read() == 1;
    }

}
