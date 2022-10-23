package ru.mail.polis.homework.oop.adapter;

/**
 * После имплементации раскоментировать тест в ru.mail.polis.homework.oop.adapter.ProductPriceParsServiceTest
 * для проверки правильной имплементации
 */
public class JsonCsvDataAdapter extends CsvData {

    public JsonCsvDataAdapter(String text) {
        super(text);
    }

    @Override
    public String getText() {

        String text = super.getText();

        StringBuilder keyBuilder = new StringBuilder();
        StringBuilder csvBuilder = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            switch (text.charAt(i)) {
                case ',':
                    keyBuilder.append('\n');
                    break;
                case ':':
                    keyBuilder.append(',');
                    break;
                case '{':
                case '"':
                    break;
                case '}':
                case '\n':
                    csvBuilder.append(keyBuilder);
                    keyBuilder = new StringBuilder();
                    break;
                default:
                    keyBuilder.append(text.charAt(i));
                    break;
            }
        }

        return csvBuilder.toString();
    }


}
