package ru.mail.polis.homework.oop.adapter;

/**
 * После имплементации раскоментировать тест в ru.mail.polis.homework.oop.adapter.ProductPriceParsServiceTest
 * для проверки правильной имплементации
 */
public class JsonCsvDataAdapter extends CsvData {
    public JsonCsvDataAdapter(String stringifiedJsonObject) {
        super(mapJsonToCsv(stringifiedJsonObject));
    }
    private static String mapJsonToCsv(String stringifiedJsonObject) {
        StringBuilder csvBuilder = new StringBuilder();
        StringBuilder keyValueBuilder = new StringBuilder();
        for (int i = 0; i < stringifiedJsonObject.length(); i++) {
            switch (stringifiedJsonObject.charAt(i)) {
                case '}':
                case '\n':
                    csvBuilder.append(keyValueBuilder);
                    keyValueBuilder = new StringBuilder();
                    break;
                case ',':
                    keyValueBuilder.append('\n');
                    break;
                case ':':
                    keyValueBuilder.append(',');
                    break;
                case '{':
                case '"':
                    break;
                default:
                    keyValueBuilder.append(stringifiedJsonObject.charAt(i));
                    break;
            }
        }
        return csvBuilder.toString();
    }
}
