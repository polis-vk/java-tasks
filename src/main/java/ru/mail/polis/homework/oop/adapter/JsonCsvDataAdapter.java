package ru.mail.polis.homework.oop.adapter;

/**
 * После имплементации раскоментировать тест в ru.mail.polis.homework.oop.adapter.ProductPriceParsServiceTest
 * для проверки правильной имплементации
 */
public class JsonCsvDataAdapter extends CsvData {
    public JsonCsvDataAdapter(String text) {
        super(parseJsonToCsvDataFormat(text));
    }

    private static String parseJsonToCsvDataFormat(String jsonData) {
        return jsonData.replace("{", "").replace("}", "").replace("\"", "").replace(",", "").replace(":", ",");
    }
}
