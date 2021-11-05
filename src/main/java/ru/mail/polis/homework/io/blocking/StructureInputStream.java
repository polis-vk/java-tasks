package ru.mail.polis.homework.io.blocking;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Вам нужно реализовать StructureInputStream, который умеет читать данные из файла.
 * Читать поля нужно ручками, с помощью массива байт и методов {@link #read()}, {@link #read(byte[])} и так далее
 * 3 балла`
 */
public class StructureInputStream extends FileInputStream {

    private ArrayList<Structure> structures = new ArrayList<>();

    public StructureInputStream(File fileName) throws FileNotFoundException {
        super(fileName);
    }


    /**
     * Метод должен вернуть следующую прочитанную структуру.
     * Если структур в файле больше нет, то вернуть null
     */
    public Structure readStructure() throws IOException {
        Structure resultStructure = new Structure();

        byte[] buffer = new byte[18];
        if (super.read(buffer) != 18) {
            return null;
        } else {
            resultStructure.setId(toLong(buffer, 0));
            resultStructure.setCoeff(Double.longBitsToDouble(toLong(buffer, 8)));
            resultStructure.setFlags(byteToFlags(buffer[16]));
            resultStructure.setParam(buffer[17]);
        }
        resultStructure.setName(readString());

        if (super.read() == 1) {
            resultStructure.setSubStructures(null);
        } else {
            int subStructuresAmount;
            try {
                subStructuresAmount = readInt();
            } catch (IOException exception) {
                return null;
            }

            SubStructure[] subStructures = new SubStructure[subStructuresAmount];

            for (int i = 0; i < subStructuresAmount; i++) {
                try {
                    subStructures[i] = readSubStructure();
                } catch (IOException exception) {
                    return null;
                }
            }
            resultStructure.setSubStructures(subStructures);
        }

        structures.add(resultStructure);
        return resultStructure;
    }

    /**
     * Метод должен вернуть все структуры, которые есть в файле.
     * Если файл уже прочитан, но возвращается полный массив.
     */
    public Structure[] readStructures() throws IOException {
        while ((readStructure()) != null) {
        }

        Structure[] resultStructures = new Structure[structures.size()];
        for (int i = 0; i < structures.size(); i++) {
            resultStructures[i] = structures.get(i);
        }

        return resultStructures;
    }

    private static long toLong(byte[] data, int offset) {
        return (long) (0xff & data[offset]) << 56 |
                (long) (0xff & data[offset + 1]) << 48 |
                (long) (0xff & data[offset + 2]) << 40 |
                (long) (0xff & data[offset + 3]) << 32 |
                (long) (0xff & data[offset + 4]) << 24 |
                (long) (0xff & data[offset + 5]) << 16 |
                (long) (0xff & data[offset + 6]) << 8 |
                (long) (0xff & data[offset + 7]);
    }

    private static boolean[] byteToFlags(byte data) {
        boolean[] result = new boolean[4];

        if ((data >> 7 & 1) == 1) {
            result[0] = true;
        }
        if ((data >> 6 & 1) == 1) {
            result[1] = true;
        }
        if ((data >> 5 & 1) == 1) {
            result[2] = true;
        }
        if ((data >> 4 & 1) == 1) {
            result[3] = true;
        }
        return result;
    }

    private int readInt() throws IOException {
        byte[] buffer = new byte[4];
        if (super.read(buffer) != 4) {
            throw new IOException();
        }

        return (0xff & buffer[0]) << 24 |
                (0xff & buffer[1]) << 16 |
                (0xff & buffer[2]) << 8 |
                (0xff & buffer[3]);
    }

    private SubStructure readSubStructure() throws IOException {
        int id;
        String name;
        double coefficient;
        boolean flag = false;

        id = readInt();
        name = readString();

        byte[] buffer = new byte[9];
        if (super.read(buffer) != 9) {
            return null;
        }
        coefficient = Double.longBitsToDouble(toLong(buffer, 0));
        if ((buffer[8] >> 7 & 1) == 1) {
            flag = true;
        }
        return new SubStructure(id, name, flag, coefficient);
    }

    private String readString() throws IOException {
        if (super.read() == 1) {
            return null;
        } else {
            int length = readInt();
            byte[] buffer = new byte[length];
            if (super.read(buffer) != length) {
                throw new IOException();
            }
            return new String(buffer);
        }
    }
}
