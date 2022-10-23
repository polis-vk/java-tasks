package ru.mail.polis.homework.oop.adapter;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class ProductPriceParsServiceTest {

    @Test
    public void testParsWithCsv() {
        String csvText = "Bananas,100\n" +
                "Strawberries,220\n" +
                "Grapes,130\n" +
                "Apples,60\n" +
                "Watermelon,30\n" +
                "Oranges,150\n" +
                "Blueberries,180\n" +
                "Lemons,20";
        CsvData csvData = new CsvData(csvText);
        ProductPriceParsService service = new ProductPriceParsService();
        Map<String, Integer> parsedData = service.pars(csvData);
        assertEquals(Integer.valueOf(100), parsedData.get("Bananas"));
        assertEquals(Integer.valueOf(220), parsedData.get("Strawberries"));
        assertEquals(Integer.valueOf(130), parsedData.get("Grapes"));
        assertEquals(Integer.valueOf(60), parsedData.get("Apples"));
        assertEquals(Integer.valueOf(30), parsedData.get("Watermelon"));
        assertEquals(Integer.valueOf(150), parsedData.get("Oranges"));
        assertEquals(Integer.valueOf(180), parsedData.get("Blueberries"));
        assertEquals(Integer.valueOf(20), parsedData.get("Lemons"));
    }
    @Test
    public void testParsWithJsonAdapter() {
        String jsonText = "{" +
                "\"Bananas\":100,\n" +
                "\"Strawberries\":220,\n" +
                "\"Grapes\":130,\n" +
                "\"Apples\":60,\n" +
                "\"Watermelon\":30,\n" +
                "\"Oranges\":150,\n" +
                "\"Blueberries\":180,\n" +
                "\"Lemons\":20" +
                "}";
        JsonCsvDataAdapter adapter = new JsonCsvDataAdapter(jsonText);
        ProductPriceParsService service = new ProductPriceParsService();
        Map<String, Integer> parsedData = service.pars(adapter);
        assertEquals(Integer.valueOf(100), parsedData.get("Bananas"));
        assertEquals(Integer.valueOf(220), parsedData.get("Strawberries"));
        assertEquals(Integer.valueOf(130), parsedData.get("Grapes"));
        assertEquals(Integer.valueOf(60), parsedData.get("Apples"));
        assertEquals(Integer.valueOf(30), parsedData.get("Watermelon"));
        assertEquals(Integer.valueOf(150), parsedData.get("Oranges"));
        assertEquals(Integer.valueOf(180), parsedData.get("Blueberries"));
        assertEquals(Integer.valueOf(20), parsedData.get("Lemons"));
    }

}