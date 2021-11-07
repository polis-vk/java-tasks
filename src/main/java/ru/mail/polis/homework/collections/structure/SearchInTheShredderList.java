package ru.mail.polis.homework.collections.structure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Задание оценивается в 2 балла.
 * Наша структура хранит обрывки слов. Надо реализовать метод positionPartString,
 * который вернет: позиции, где находятся подстроки, из которых можно составить
 * переданное слово. Также известно, что слова, которые писались в структуру,
 * изначально делились пополам для записи в нее.
 * Отрабатывать метод должен за О(n).
 */
public class SearchInTheShredderList {

    private List<String> partStrings = new ArrayList<>();

    public SearchInTheShredderList() {
    }

    public SearchInTheShredderList(List<String> partStrings) {
        this.partStrings = partStrings;
    }

    public void add(String value) {
        partStrings.add(value);
    }

    public String get(int index) {
        return partStrings.get(index);
    }

    public int[] positionPartString(String value) {
        if (value == null || value.isEmpty() || partStrings == null || partStrings.isEmpty()) {
            return null;
        }
        String first = value.substring(0, value.length() / 2);
        String second = value.substring(value.length() / 2);
        int[] result = new int[] {Integer.MIN_VALUE, Integer.MIN_VALUE};
        int i = 0;
        int j = 0;
        for (String string: partStrings) {
            if (first.equals(string)) {
                result[0] = i;
                j++;
            }
            if (second.equals(string)) {
                result[1] = i;
                j++;
            }
            if (j == result.length) {
                break;
            }
            ++i;
        }
        return result[0] == Integer.MIN_VALUE || result[1] == Integer.MIN_VALUE ? null : result;
    }
}
