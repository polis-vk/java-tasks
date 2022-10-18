package ru.mail.polis.homework.oop.adapter;

/**
 * После имплементации раскоментировать тест в ru.mail.polis.homework.oop.adapter.ProductPriceParsServiceTest
 * для проверки правильной имплементации
 */
public class JsonCsvDataAdapter extends CsvData{
    private final String text;

    public JsonCsvDataAdapter(String text) {
        super(text);
        this.text = text;
    }
    @Override
    public String getText() {
        return convertToCsv(text);
    }

    private final String convertToCsv(String text) {
        return text.replaceAll("[{}]", "")
                .replaceAll("\"", "")
                .replaceAll(",", "")
                .replaceAll(":", ",");
    }
}
