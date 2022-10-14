package ru.mail.polis.homework.oop.adapter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * После имплементации раскоментировать тест в ru.mail.polis.homework.oop.adapter.ProductPriceParsServiceTest
 * для проверки правильной имплементации
 */
public class JsonCsvDataAdapter extends CsvData {

    public JsonCsvDataAdapter(String text) {
        super(convertJsonToCsv(text));
    }

    private static String convertJsonToCsv(String jsonText) {
        Map<String, Integer> productPriceMap = new HashMap<>();
        jsonText = jsonText.replace("{", "").replace("}", "");
        Arrays.stream(jsonText.split("\\r?\\n|,\\n?")).forEach(line -> {
            String[] items = line.replace(",", "").replace("\"", "").split(":");
            productPriceMap.put(items[0].trim(), Integer.parseInt(items[1].trim()));
        });
        StringBuilder sb = new StringBuilder();
        productPriceMap.forEach((product, price) -> sb.append(product).append(',').append(price).append("\n"));
        return sb.toString();
    }
}
