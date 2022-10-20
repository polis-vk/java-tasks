package ru.mail.polis.homework.oop.adapter;

import java.util.Arrays;
import java.util.Map;

/**
 * После имплементации раскоментировать тест в ru.mail.polis.homework.oop.adapter.ProductPriceParsServiceTest
 * для проверки правильной имплементации
 */
public class JsonCsvDataAdapter extends CsvData {
    public JsonCsvDataAdapter(String jsonText) {
        super(jsonText);
    }

    @Override
    public String getText() {
        return super.getText().replaceAll(",|\"|[{}]", "").replaceAll(":", ",");
    }

}
