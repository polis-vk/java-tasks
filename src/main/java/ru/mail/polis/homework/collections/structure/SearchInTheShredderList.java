package ru.mail.polis.homework.collections.structure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

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
     * @param value - передаваемоей слово
     * @return - либо массив с реальными позициями подстрок если нашли, либо - null
     */
    public int[] positionPartString(String value) {
        List<Integer> result = new ArrayList<>();
        int index = -1;
        int cursor = 0;
        for (int i = 0; i <= value.length(); i++) {
            index = partStrings.indexOf(value.substring(cursor, i));
            if (index != -1) {
                cursor = i;
                result.add(index);
            }
        }
        if (index == -1) {
            return null;
        }
        int[] res = new int[result.size()];
        for (int i = 0; i < result.size(); i++) {
            res[i] = result.get(i);
        }
        return res;
    }
}
