package ru.mail.polis.homework.io.blocking;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

/**
 * Вам нужно реализовать StructureInputStream, который умеет читать данные из файла.
 * Читать поля нужно ручками, с помощью массива байт и методов {@link #read()}, {@link #read(byte[])} и так далее
 * 3 тугрика
 */
public class StructureInputStream extends FileInputStream {

    private Structure[] structures = new Structure[0];

    public StructureInputStream(File fileName) throws FileNotFoundException {
        super(fileName);
    }

    /**
     * Метод должен вернуть следующую прочитанную структуру.
     * Если структур в файле больше нет, то вернуть null
     */
    public Structure readStructure() {
        Structure structure = new Structure();
        try {
            if (this.available() == 0) {
                return null;
            }
            readId(structure);
            readName(structure);
            readCoeff(structure);
            readFlags(structure);
            readParam(structure);
            readSubStructures(structure);
        } catch (IOException e) {
            e.printStackTrace();
        }
        structures = Arrays.copyOf(structures, structures.length + 1);
        structures[structures.length - 1] = structure;
        return structure;
    }

    private long bytesToLong(byte[] bytes) {
        return ((long) bytes[7] << 56)
                | ((long) bytes[6] & 0xff) << 48
                | ((long) bytes[5] & 0xff) << 40
                | ((long) bytes[4] & 0xff) << 32
                | ((long) bytes[3] & 0xff) << 24
                | ((long) bytes[2] & 0xff) << 16
                | ((long) bytes[1] & 0xff) << 8
                | ((long) bytes[0] & 0xff);
    }

    private int bytesToInt(byte[] bytes) {
        return ((int) bytes[3] << 24)
                | ((int) bytes[2] & 0xff) << 16
                | ((int) bytes[1] & 0xff) << 8
                | ((int) bytes[0] & 0xff);
    }

    private void readId(Structure structure) throws IOException {
        byte[] buffer = new byte[8];
        this.read(buffer);
        structure.setId(bytesToLong(buffer));
    }

    private void readName(Structure structure) throws IOException {
        byte[] buffer = new byte[4];
        this.read(buffer);
        int nameLength = bytesToInt(buffer);
        if (nameLength == 0) {
            structure.setName(null);
            return;
        }
        buffer = new byte[nameLength];
        this.read(buffer);
        structure.setName(new String(buffer, StandardCharsets.UTF_8));
    }

    private void readCoeff(Structure structure) throws IOException {
        byte[] buffer = new byte[4];
        this.read(buffer);
        structure.setCoeff(Float.intBitsToFloat(bytesToInt(buffer)));
    }

    private void readFlags(Structure structure) throws IOException {
        byte flags = (byte) this.read();
        structure.setFlag1((flags & 8) >> 3 == 1);
        structure.setFlag2((flags & 4) >> 2 == 1);
        structure.setFlag3((flags & 2) >> 1 == 1);
        structure.setFlag4((flags & 1) == 1);
    }

    private void readParam(Structure structure) throws IOException {
        byte param = (byte) this.read();
        structure.setParam(param);
    }

    private void readSubStructures(Structure structure) throws IOException {
        byte[] buffer4bytes = new byte[4];
        this.read(buffer4bytes);
        int subStructuresCount = bytesToInt(buffer4bytes);
        if (subStructuresCount == 0) {
            return;
        }
        SubStructure[] subStructures = new SubStructure[subStructuresCount];
        byte[] buffer8bytes = new byte[8];
        byte buffer1byte;
        for (int i = 0; i < subStructuresCount; i++) {
            this.read(buffer4bytes);
            int id = bytesToInt(buffer4bytes);
            this.read(buffer4bytes);
            int nameLengthSub = bytesToInt(buffer4bytes);
            byte[] bufferForName = new byte[nameLengthSub];
            this.read(bufferForName);
            String name = new String(bufferForName, StandardCharsets.UTF_8);
            buffer1byte = (byte) this.read();
            boolean flag = (buffer1byte == 1);
            this.read(buffer8bytes);
            double score = Double.longBitsToDouble(bytesToLong(buffer8bytes));
            subStructures[i] = new SubStructure(id, name, flag, score);
        }
        structure.setSubStructures(subStructures);
    }

    /**
     * Метод должен вернуть все структуры, которые есть в файле.
     * Если файл уже прочитан, но возвращается полный массив.
     */
    public Structure[] readStructures() {
        Structure structure;
        do {
            structure = readStructure();
        } while (structure != null);
        return structures;
    }
}
