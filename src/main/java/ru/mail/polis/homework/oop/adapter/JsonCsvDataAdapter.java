package ru.mail.polis.homework.oop.adapter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * После имплементации раскоментировать тест в ru.mail.polis.homework.oop.adapter.ProductPriceParsServiceTest
 * для проверки правильной имплементации
 */
public class JsonCsvDataAdapter extends CsvData {
    private final String text;

    public JsonCsvDataAdapter(String text) {
        super(text);
        this.text = text;
    }

    public String textEditorJsonToCsv(String text) {
        StringBuilder jsonToCsv = new StringBuilder(text);
        jsonToCsv.deleteCharAt(jsonToCsv.indexOf("{"));
        jsonToCsv.deleteCharAt(jsonToCsv.indexOf("}"));
        while (jsonToCsv.indexOf("\"") >= 0 || jsonToCsv.indexOf(",") >= 0) {
            if (jsonToCsv.indexOf("\"") >= 0) {
                jsonToCsv.deleteCharAt(jsonToCsv.indexOf("\""));
            }
            if (jsonToCsv.indexOf(",") >= 0) {
                jsonToCsv.deleteCharAt(jsonToCsv.indexOf(","));
            }
        }
        while (jsonToCsv.indexOf(":") >= 0) {
            if (jsonToCsv.indexOf(":") >= 0) {
                int idx = jsonToCsv.indexOf(":");
                jsonToCsv.insert(idx, ",");
                jsonToCsv.deleteCharAt(idx + 1);
            }
        }
        return jsonToCsv.toString();
    }

    @Override
    public String getText() {
        return textEditorJsonToCsv(text);
    }
}