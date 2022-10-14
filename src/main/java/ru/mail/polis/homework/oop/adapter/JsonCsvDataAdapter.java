package ru.mail.polis.homework.oop.adapter;

/**
 * После имплементации раскоментировать тест в ru.mail.polis.homework.oop.adapter.ProductPriceParsServiceTest
 * для проверки правильной имплементации
 */
public class JsonCsvDataAdapter extends CsvData {
    public JsonCsvDataAdapter(String text) {
        super(normalizeText(text));
    }

    private static String normalizeText(String text) {
        return text.substring(1, text.length() - 1)
                .replaceAll("\"", "")
                .replace(",", "")
                .replace(":", ",");
    }
}
