package ru.mail.polis.homework.oop.adapter;

/**
 * После имплементации раскомментировать тест в ru.mail.polis.homework.oop.adapter.ProductPriceParsServiceTest
 * для проверки правильной имплементации
 */
public class JsonCsvDataAdapter extends CsvData {

    private final String cvsData;

    public JsonCsvDataAdapter(String text) {
        super(text);
        StringBuilder jsonToCvs = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c == ':') {
                jsonToCvs.append(',');
            } else if (c == ',') {
                jsonToCvs.append('\n');
            } else if (c != '{' && c != '}' && c != '\"' && c != '\n') {
                jsonToCvs.append(c);
            }
        }
        this.cvsData = jsonToCvs.toString();
    }

    @Override
    public String getText() {
        return cvsData;
    }
}
