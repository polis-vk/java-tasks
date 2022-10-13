package ru.mail.polis.homework.oop.adapter;

import java.util.HashMap;
import java.util.Map;

/**
 * После имплементации раскоментировать тест в ru.mail.polis.homework.oop.adapter.ProductPriceParsServiceTest
 * для проверки правильной имплементации
 */
public class JsonCsvDataAdapter extends CsvData {

    private static final String ERROR_OUTPUT = "";

    public JsonCsvDataAdapter(String jsonText) {
        super(parseDataFromJsonToCsv(jsonText));
    }

    // In case of bad data input there will be an empty string returned
    private static String parseDataFromJsonToCsv(String text) {
        Map<String, Integer> dataMap = new HashMap<>();
        String data = text.trim();

        int bound = data.length() - 1;

        if (data.isEmpty() || data.charAt(0) != '{' || data.charAt(bound) != '}') {
            return ERROR_OUTPUT;
        }

        int i = 1;
        StringBuilder sb = new StringBuilder();
        String key = null;
        boolean isCollapsed = true;
        char current;

        while (i < bound) {
            current = data.charAt(i);
            switch (current) {
                case ':':
                    key = sb.toString();
                    sb = new StringBuilder();
                    break;
                case '\"':
                    isCollapsed = !isCollapsed;
                    break;
                case '\n':
                    break;
                case ',':
                    if (key == null || key.isEmpty()) {
                        return ERROR_OUTPUT;
                    } else {
                        dataMap.put(key, Integer.parseInt(sb.toString()));
                    }
                    sb = new StringBuilder();
                    break;
                default:
                    if (current == ' ' && isCollapsed) {
                        break;
                    }
                    sb.append(current);
            }
            i++;
        }

        if (key == null || key.isEmpty()) {
            return ERROR_OUTPUT;
        } else {
            dataMap.put(key, Integer.parseInt(sb.toString()));
        }

        sb = new StringBuilder();

        for (Map.Entry<String, Integer> entry : dataMap.entrySet()) {
            sb
                    .append(entry.getKey())
                    .append(',')
                    .append(entry.getValue())
                    .append('\n');
        }

        return sb.toString();
    }
}
