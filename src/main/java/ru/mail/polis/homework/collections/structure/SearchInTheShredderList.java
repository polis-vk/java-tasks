package ru.mail.polis.homework.collections.structure;

import java.util.ArrayList;
import java.util.List;

/**
 * Задание оценивается в 2 балла.
 * Одна из самых популярных задач.
 * Наша структура хранит обрывки слов. Надо реализовать метод positionPartString
 * который вернет: позиции где находятся подстроки, из которых можно составить
 * переданное слово. Так же известно что слова, которые писались в структуру, изначально
 * делились пополам для записи в нее.
 * Отрабатывать метод должен за О(n).
 */
public class SearchInTheShredderList {
    private List<String> partStrings = new ArrayList<>();

    public SearchInTheShredderList() {}

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
     * ищем позиции подстрок из которых можно составить передаваемое слово
     *
     * @param value - передаваемоей слово
     * @return - либо массив с реальными позициями подстрок если нашли, либо - null
     */
    public int[] positionPartString(String value) {
        if (value == null || value.equals("") || partStrings.isEmpty()) {
            return null;
        }
        int[] twoParts = new int[2];
        twoParts[0] = -1;
        twoParts[1] = -1;
        String value1 = value.substring(0, value.length() / 2);
        String value2 = value.substring(value.length() / 2);
        for (int i = 0; i < partStrings.size(); i++) {
            if (partStrings.get(i).equals(value1) && twoParts[0] == -1) {
                twoParts[0] = i;
            }
            if (partStrings.get(i).equals(value2)) {
                twoParts[1] = i;
            }
        }
        if (twoParts[0] == -1 || twoParts[1] == -1) {
            return null;
        }
        return twoParts;
    }
}
