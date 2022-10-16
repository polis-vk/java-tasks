package ru.mail.polis.homework.oop.adapter;

/**
 * После имплементации раскоментировать тест в ru.mail.polis.homework.oop.adapter.ProductPriceParsServiceTest
 * для проверки правильной имплементации
 */
public class JsonCsvDataAdapter extends CsvData {
    private final String csvText;

    public JsonCsvDataAdapter(String text) {
        super(text);
        StringBuilder plainStringBuilder = new StringBuilder();
        char c;
        for (int i = 0; i < text.length(); i++) {
            c = text.charAt(i);
            switch (c) {
                case '\"':
                case '{':
                case '}':
                case '\n':
                    // ignore
                    break;
                case ':':
                    plainStringBuilder.append(',');
                    break;
                case ',':
                    plainStringBuilder.append('\n');
                    break;
                default:
                    plainStringBuilder.append(c);
                    break;
            }
        }
        this.csvText = plainStringBuilder.toString();
    }

    @Override
    public String getText() {
        return csvText;
    }
}
