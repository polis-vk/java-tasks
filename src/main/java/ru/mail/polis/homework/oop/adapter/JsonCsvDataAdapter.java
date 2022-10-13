package ru.mail.polis.homework.oop.adapter;

/**
 * После имплементации раскоментировать тест в ru.mail.polis.homework.oop.adapter.ProductPriceParsServiceTest
 * для проверки правильной имплементации
 */
public class JsonCsvDataAdapter extends CsvData {

    public JsonCsvDataAdapter(String jsonText) {
        super(parseJsonToCsv(jsonText));
    }

    private static String parseJsonToCsv(String jsonText) {
        return jsonText.replaceAll(",", "")
                .replaceAll(":", ",")
                .replaceAll("\"", "")
                .replace("{", "")
                .replace("}", "");
    }
}
