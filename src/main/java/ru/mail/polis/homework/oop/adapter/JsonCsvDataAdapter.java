package ru.mail.polis.homework.oop.adapter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * После имплементации раскоментировать тест в ru.mail.polis.homework.oop.adapter.ProductPriceParsServiceTest
 * для проверки правильной имплементации
 */
public class JsonCsvDataAdapter extends CsvData {

    // Конструктор вызывает конструктор родителя.
    public JsonCsvDataAdapter(String text) {
        super(text);
    }

    @Override
    public String getText() {
        /*
         * Для создания конвертера json -> csv использовалось регулярное выражение, которое выделяет группу в виде
         * "nameOfItem":itemCost и подгруппы вида: подгруппа 1 -> nameOfItem, подгруппа 2 ->itemCost. Далее через
         * StringBuilder происходит конкатенация итогового csv файла.
         */

        // Создание итогового текста.
        StringBuilder jsonToCsvText = new StringBuilder();

        // Задаем шаблон через регулярное выражение.
        Pattern pattern = Pattern.compile("\"([^\"\\r\\n]+)\":(\\d+)");

        // Создаем объект "Сравниватель".
        Matcher matcher = pattern.matcher(super.getText());

        // Производим обработку текста в json и преобразование его в csv.
        while (matcher.find()) {
            jsonToCsvText.append(matcher.group(1))
                    .append(",")
                    .append(matcher.group(2))
                    .append("\n");
        }
        return jsonToCsvText.toString();
    }
}
