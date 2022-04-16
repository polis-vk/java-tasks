package ru.mail.polis.homework.io.blocking;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Вам нужно реализовать StructureInputStream, который умеет читать данные из файла.
 * Читать поля нужно ручками, с помощью массива байт и методов {@link #read()}, {@link #read(byte[])} и так далее
 * 3 тугрика
 */
public class StructureInputStream extends FileInputStream {

    private Structure[] structures = new Structure[0];
    private static final int numberOfStructureParams = 9;
    private static final int numberParamsBeforeSubStructures = 2;
    private static final int numberParamsAfterSubStructures = 6;
    private static final int numberOfSubStructureParams = 4;

    public StructureInputStream(File fileName) throws FileNotFoundException {
        super(fileName);
    }


    /**
     * Метод должен вернуть следующую прочитанную структуру.
     * Если структур в файле больше нет, то вернуть null
     */
    public Structure readStructure() throws IOException {
        if (this.available() == 0) {
            return null;
        }
        String strStructure = "";
        int buf = this.read();
        int counterBraces = 0;
        while (buf != -1) {
            strStructure += (char) buf;
            buf = this.read();
            if ((char) buf == '{') {
                counterBraces++;
            }
            if ((char) buf == '}') {
                counterBraces--;
                if (counterBraces == 0) {
                    break;
                }
            }
        }

        Structure structure = new Structure();
        strStructure = strStructure.replaceFirst("Structure", "");
        String[] splitStrStructure = strStructure.split("}], | subStructures=\\[");
        for (int i = 0; i < splitStrStructure.length; i++) {
            splitStrStructure[i] = splitStrStructure[i].replaceAll("[{},']", "");
        }
        Map<String, String> structureMap = new HashMap<>();
        if (splitStrStructure.length == 1) {
            putInMap(structureMap, splitStrStructure[0].split(" ", numberOfStructureParams));
        } else {
            String[] data = splitStrStructure[0].split(" ", numberParamsBeforeSubStructures);
            putInMap(structureMap, data);

            data = splitStrStructure[2].split(" ", numberParamsAfterSubStructures);
            putInMap(structureMap, data);

            if (!splitStrStructure[1].equals("null")) {
                ArrayList<SubStructure> subStructures = new ArrayList<>();
                Map<String, String> subStructureMap = new HashMap<>();
                String str;
                data = splitStrStructure[1].split(" ");
                int countSubStructures = data.length / numberOfSubStructureParams;
                String[] strSubStructure = new String[numberOfSubStructureParams];
                for (int i = 0; i < countSubStructures; i++) {
                    System.arraycopy(data, i * numberOfSubStructureParams, strSubStructure, 0, numberOfSubStructureParams);
                    putInMap(subStructureMap, strSubStructure);
                    subStructures.add(new SubStructure(
                            (str = subStructureMap.get("SubStructureid")).equals("null") ? 0 : Integer.parseInt(str),
                            subStructureMap.get("name"),
                            Boolean.parseBoolean(subStructureMap.get("flag")),
                            (str = subStructureMap.get("score")).equals("null") ? 0 : Double.parseDouble(str)
                    ));
                }
                structure.setSubStructures(subStructures.toArray(new SubStructure[0]));
            }
        }

        String str;
        structure.setId((str = structureMap.get("id")).equals("null") ? 0 : Long.parseLong(str));
        structure.setName((str = structureMap.get("name")).equals("null") ? null : str);
        structure.setCoeff((str = structureMap.get("coeff")).equals("null") ? 0 : Float.parseFloat(str));
        structure.setFlag1(Boolean.parseBoolean(structureMap.get("flag1")));
        structure.setFlag2(Boolean.parseBoolean(structureMap.get("flag2")));
        structure.setFlag3(Boolean.parseBoolean(structureMap.get("flag3")));
        structure.setFlag4(Boolean.parseBoolean(structureMap.get("flag4")));
        structure.setParam((str = structureMap.get("param")).equals("null") ? 0 : Byte.parseByte(str));

        structures = Arrays.copyOf(structures, structures.length + 1);
        structures[structures.length - 1] = structure;
        return structure;
    }

    private void putInMap(Map<String, String> map, String[] data) {
        for (String str : data) {
            String[] splitStr = str.split("=", 2);
            map.put(splitStr[0], splitStr[1]);
        }
    }

    /**
     * Метод должен вернуть все структуры, которые есть в файле.
     * Если файл уже прочитан, но возвращается полный массив.
     */
    public Structure[] readStructures() throws IOException {
        Structure structure;
        do {
            structure = readStructure();
        } while (structure != null);
        return structures;
    }
}
