package ru.mail.polis.homework.collections.structure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Задание оценивается в 2 балла.
 * Одна из самых популярных задач.
 * Наша структура хранит обрывки слов. Надо реализовать метод positionPartString
 * который вернет: позиции, где находятся подстроки, из которых можно составить
 * переданное слово. Так же известно, что слова, которые писались в структуру, изначально
 * делились пополам для записи в нее.
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

    /**
     * Ищем позиции подстрок из которых можно составить передаваемое слово
     *
     * @param value - передаваемое слово
     * @return - либо массив с реальными позициями подстрок если нашли, либо - null
     */
    public int[] positionPartString(String value) {
        if (value == null) {
            return null;
        }
        String start = value.substring(0, value.length() / 2);
        String end = value.substring(value.length() / 2);
        int startIndex = -1;
        int endIndex = -1;
        for (int i = 0; i < partStrings.size(); i++) {
            if (partStrings.get(i).equals(start)) {
                startIndex = i;
            } else if (partStrings.get(i).equals(end)) {
                endIndex = i;
            }
            if (startIndex != -1 && endIndex != -1) {
                return new int[]{startIndex, endIndex};
            }
        }
        return null;
    }

}
