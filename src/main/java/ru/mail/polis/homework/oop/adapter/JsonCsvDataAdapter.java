package ru.mail.polis.homework.oop.adapter;

import java.util.Arrays;

/**
 * После имплементации раскоментировать тест в ru.mail.polis.homework.oop.adapter.ProductPriceParsServiceTest
 * для проверки правильной имплементации
 */
public class JsonCsvDataAdapter extends CsvData {
    private final static String jsonSeparator = ":";
    private final static String csvSeparator = ",";

    public JsonCsvDataAdapter(String text) {
        super(text);
    }

    @Override
    public String getText() {
        return jsonToCsv(super.getText());
    }

    public String jsonToCsv(String jsonText) {
        jsonText = jsonText.substring(1);
        StringBuilder result = new StringBuilder();
        Arrays.stream(jsonText.split("\\r?\\n")).forEach(line -> {
            String[] items = line.split(jsonSeparator);
            result
                .append(items[0], 1, items[0].length() - 1)
                .append(csvSeparator)
                .append(items[1], 0, items[1].length() - 1)
                .append(System.getProperty("line.separator"));
        });
        return result.toString();
    }
}
