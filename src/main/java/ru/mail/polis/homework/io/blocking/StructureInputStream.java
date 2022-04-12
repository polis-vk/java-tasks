package ru.mail.polis.homework.io.blocking;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Вам нужно реализовать StructureInputStream, который умеет читать данные из файла.
 * Читать поля нужно ручками, с помощью массива байт и методов {@link #read()}, {@link #read(byte[])} и так далее
 * 3 тугрика
 */
public class StructureInputStream extends FileInputStream {

    private List<Structure> structures = new ArrayList<>();

    public StructureInputStream(File fileName) throws FileNotFoundException {
        super(fileName);
    }


    /**
     * Метод должен вернуть следующую прочитанную структуру.
     * Если структур в файле больше нет, то вернуть null
     */
    public Structure readStructure() throws IOException {
        byte[] bytes = new byte[this.available()];
        if (this.read(bytes) == 0) {
            return null;
        }
        Structure structure = new Structure();
        String strStructure = new String(bytes, StandardCharsets.UTF_8);
        strStructure = strStructure.replaceFirst("Structure", "");
        strStructure = strStructure.replaceAll("[\\[\\]{},']", "");
        strStructure = strStructure.replaceAll("=", " ");
        String[] dataStructure = strStructure.split(" ");
//        Map<String, String> dataStructureMap = new HashMap<>();
//        for(String str: dataStructureStrs){
//            String[]
//            dataStructureMap.put(str.split("="));
//
//        }
        int i = 1;
        structure.setId(Long.parseLong(dataStructure[i]));
        i += 2;
        if(dataStructure[i].equals("null")){
            structure.setName(null);
        }
        else{
            structure.setName(dataStructure[i]);
        }
        i += 2;
        if (dataStructure[i].equals("null")) {
            structure.setSubStructures(null);
            i++;
        } else {
            List<SubStructure> subStructures = new ArrayList<>();
            do {
                i += 1;
                int id = Integer.parseInt(dataStructure[i]);
                i += 2;
                String name = dataStructure[i];
                i += 2;
                boolean flag = Boolean.parseBoolean(dataStructure[i]);
                i += 2;
                double score = Double.parseDouble(dataStructure[i]);
                i += 1;
                subStructures.add(new SubStructure(id, name, flag, score));
            } while (!dataStructure[i].equals("coeff"));
            structure.setSubStructures(subStructures.toArray(new SubStructure[0]));
        }
        i++;
        if(!dataStructure[i].equals("null")) {
            structure.setCoeff(Float.parseFloat(dataStructure[i]));
        }
        i += 2;
        structure.setFlag1(Boolean.parseBoolean(dataStructure[i]));
        i += 2;
        structure.setFlag2(Boolean.parseBoolean(dataStructure[i]));
        i += 2;
        structure.setFlag3(Boolean.parseBoolean(dataStructure[i]));
        i += 2;
        structure.setFlag4(Boolean.parseBoolean(dataStructure[i]));
        i += 2;
        structure.setParam(Byte.parseByte(dataStructure[i]));
        structures.add(structure);
        return structure;
    }

    /**
     * Метод должен вернуть все структуры, которые есть в файле.
     * Если файл уже прочитан, но возвращается полный массив.
     */
    public Structure[] readStructures() throws IOException {
        Structure currentStructure = readStructure();
        while(currentStructure != null){
            structures.add(currentStructure);
            currentStructure = readStructure();
        }
        return structures.toArray(new Structure[0]);
    }
}
