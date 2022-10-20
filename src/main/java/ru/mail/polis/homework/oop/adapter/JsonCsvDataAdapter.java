package ru.mail.polis.homework.oop.adapter;

/**
 * После имплементации раскоментировать тест в ru.mail.polis.homework.oop.adapter.ProductPriceParsServiceTest
 * для проверки правильной имплементации
 */
public class JsonCsvDataAdapter extends CsvData {
    public JsonCsvDataAdapter(String text) {
        super(JsonToCsv(text));
    }

    private static String JsonToCsv(String text) {
        char[] charText = text.toCharArray();
        StringBuilder result = new StringBuilder();

        for (int i = 1; i < text.length() - 1; i++) {
            char charIn = text.charAt(i);

            if (charIn == ':') {
                result.append(',');
            } else if (charIn == ',') {
                result.append('\n');
            } else if (charIn != '\"' && charIn != '\n') {
                result.append(charIn);
            }
        }

        return result.toString();
    }
}
