package ru.mail.polis.homework.oop.adapter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Дан сервис который умеет парсить данные из CSV формата.
 * Необходимо реализовать Адаптер (JsonCsvDataAdapter) к нему, который позволит ему обрабатывать и JSON.
 * Код данного класса трогать ЗАПРЕЩЕНО. Воспринимайте его как буд-то он от сторооней подключенной бибилиотеке.
 */
public class ProductPriceParsService {
    public Map<String, Integer> pars(CsvData csvData) {
        Map<String, Integer> productPriceMap = new HashMap<>();
        String text = csvData.getText();
        Arrays.stream(text.split("\\r?\\n")).forEach(line -> {
            String[] items = line.split(",");
            productPriceMap.put(items[0], Integer.parseInt(items[1]));
        });
        return productPriceMap;
    }
}
