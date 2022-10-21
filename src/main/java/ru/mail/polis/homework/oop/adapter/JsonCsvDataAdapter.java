package ru.mail.polis.homework.oop.adapter;

/**
 * После имплементации раскоментировать тест в ru.mail.polis.homework.oop.adapter.ProductPriceParsServiceTest
 * для проверки правильной имплементации
 */
public class JsonCsvDataAdapter extends CsvData {

    public JsonCsvDataAdapter(String text) {
        super(generateCsvString(text));
    }

    private static String generateCsvString(String text) {
        StringBuilder sb = new StringBuilder();
        String[] splitCommons = text.replaceAll("[{}]", "").split(",");
        for (String currStr : splitCommons) {
            String[] currSplit = currStr.trim().split(":");
            String firstElem = currSplit[0].replaceAll("^\"|\"$", "");
            String secondElem = currSplit[1];
            sb.append(firstElem).append(",").append(secondElem).append("\n");
        }
        return sb.toString();
    }
}
