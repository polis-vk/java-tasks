package ru.mail.polis.homework.oop.adapter;

import java.util.Arrays;

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
        String initialText = super.getText().substring(1); // Избавляемся от открывающей фигурной скобки.
        StringBuilder result = new StringBuilder();

        Arrays.stream(initialText.split("\\r?\\n"))
                .forEach(line -> {
                    String[] items = line.split(":");

                    result
                            .append(items[0], 1, items[0].length() - 1) // Избавляемся от кавычек.
                            .append(",")
                            .append(items[1], 0, items[1].length() - 1) // Избавляемся от запятой (скобки).
                            .append("\n");
                });

        return result.toString();
    }
}
