package ru.mail.polis.homework.oop.adapter;

/**
 * После имплементации раскомментировать тест в ru.mail.polis.homework.oop.adapter.ProductPriceParsServiceTest
 * для проверки правильной имплементации
 */
public class JsonCsvDataAdapter extends CsvData {

    private final String text;

    public JsonCsvDataAdapter(String text) {
        super(text);
        this.text = text;
    }

    private String jsonToScv(String text) {
        return text.replaceAll(",", "")
                .replaceAll(":", ",")
                .replaceAll("\"", "")
                .replaceAll("[{}]", "");
    }

    public String getText() {
        return jsonToScv(text);
    }
}
