package ru.mail.polis.homework.oop.adapter;

import java.util.Arrays;

/**
 * После имплементации раскоментировать тест в ru.mail.polis.homework.oop.adapter.ProductPriceParsServiceTest
 * для проверки правильной имплементации
 */
public class JsonCsvDataAdapter extends CsvData {

    public JsonCsvDataAdapter(String text) {
        super(text);
    }

    @Override
    public String getText() {
        StringBuilder sb = new StringBuilder();
        String jsonText = super.getText().trim();
        jsonText = jsonText.substring(1, jsonText.length() - 1);

        Arrays.stream(jsonText.split(",")).forEach(line -> {
            String[] items = line.trim().replaceAll("\"", "").split(":");
            sb.append(items[0].trim());
            sb.append(",");
            sb.append(items[1].trim());
            sb.append("\n");
        });

        return sb.toString();
    }
}
