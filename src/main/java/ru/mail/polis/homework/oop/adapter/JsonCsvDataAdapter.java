package ru.mail.polis.homework.oop.adapter;

/**
 * После имплементации раскоментировать тест в ru.mail.polis.homework.oop.adapter.ProductPriceParsServiceTest
 * для проверки правильной имплементации
 */
public class JsonCsvDataAdapter extends CsvData {
    private final String csvText;

    public JsonCsvDataAdapter(String text) {
        super(text);
        StringBuilder newText = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char charInText = text.charAt(i);
            if (charInText == ':') {
                newText.append(',');
            } else if (charInText == ',') {
                newText.append('\n');
            } else if (charInText != '\"' && charInText != '{' && charInText != '}' && charInText != '\n') {
                newText.append(charInText);
            }
        }
        this.csvText = newText.toString();
    }

    @Override
    public String getText() {
        return csvText;
    }

}
