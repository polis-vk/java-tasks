package ru.mail.polis.homework.oop.adapter;

/**
 * После имплементации раскоментировать тест в ru.mail.polis.homework.oop.adapter.ProductPriceParsServiceTest
 * для проверки правильной имплементации
 */
public class JsonCsvDataAdapter extends CsvData {
    public JsonCsvDataAdapter(String text) {
        super(ConvertJsonToCsv(text));
    }

    private static String ConvertJsonToCsv(String text) {
        String res = text.substring(1, text.length() - 1);
        res = res.replace("\"", "")
                .replace(",", "")
                .replace(":", ",");
        return res;
    }
}
