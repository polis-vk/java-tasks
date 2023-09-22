package ru.mail.polis.homework.oop.adapter;

/**
 * После имплементации раскоментировать тест в ru.mail.polis.homework.oop.adapter.ProductPriceParsServiceTest
 * для проверки правильной имплементации
 */
public class JsonCsvDataAdapter extends CsvData{
    public JsonCsvDataAdapter(String text) {
        super(text);

    }

    public static String transform(String text)
    {
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < text.length(); ++i)
        {
            if(text.charAt(i) != ',' && text.charAt(i) != '"' && text.charAt(i) != '{'
            && text.charAt(i) != '}')
            {
                if(text.charAt(i) == ':') builder.append(',');
                else builder.append(text.charAt(i));
            }
        }
        return builder.toString();
    }

}
