package ru.mail.polis.homework.oop.adapter;

/**
 * После имплементации раскоментировать тест в ru.mail.polis.homework.oop.adapter.ProductPriceParsServiceTest
 * для проверки правильной имплементации
 */
public class JsonCsvDataAdapter extends CsvData {
    public JsonCsvDataAdapter(String text) {
        super(adjustJsonToCsv(text));
    }

    private static String adjustJsonToCsv(String text) {
        return text.replaceAll("[{},\"]", "")
                .replace(":", ",");
    }
}

