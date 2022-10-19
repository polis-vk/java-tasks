package ru.mail.polis.homework.oop.adapter;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * После имплементации раскоментировать тест в ru.mail.polis.homework.oop.adapter.ProductPriceParsServiceTest
 * для проверки правильной имплементации
 */
public class JsonCsvDataAdapter extends CsvData {

    public JsonCsvDataAdapter(String jsonText) {
        super(parserJSON(jsonText));
    }

    public static String parserJSON(String data) {
        StringBuilder stringBuilder = new StringBuilder();
        List<String> keys = new ArrayList<>();
        List<Integer> values = new ArrayList<>();
        Matcher matcherForKey = Pattern.compile("([a-zA-Z]+)[^:\"]").matcher(data);
        Matcher matcherForValue = Pattern.compile("\\s*(\\d+)").matcher(data);
        while (matcherForKey.find()) {
            keys.add(matcherForKey.group());
        }
        while (matcherForValue.find()) {
            values.add(Integer.valueOf(matcherForValue.group()));
        }
        for (int i = 0; i < keys.size(); i++) {
            stringBuilder.append(keys.get(i)).append(",").append(values.get(i)).append('\n');
        }
        return stringBuilder.toString();
    }
}
