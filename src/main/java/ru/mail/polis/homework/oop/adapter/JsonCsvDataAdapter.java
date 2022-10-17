package ru.mail.polis.homework.oop.adapter;

/**
 * После имплементации раскоментировать тест в ru.mail.polis.homework.oop.adapter.ProductPriceParsServiceTest
 * для проверки правильной имплементации
 */
public class JsonCsvDataAdapter extends CsvData {
    public JsonCsvDataAdapter(String jsonData) {
        super(jsonToCsv(jsonData));
    }

    private static String jsonToCsv(String jsonData) {
        return jsonData.substring(1, jsonData.length() - 1)
                .replace(",", "")
                .replace(':', ',')
                .replace("\"", "");
    }
}
