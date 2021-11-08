package ru.mail.polis.homework.collections.structure;

import java.util.ArrayList;
import java.util.List;

/**
 * Задание оценивается в 2 балла.
 * Одна из самых популярных задач.
 * Наша структура хранит обрывки слов. Надо реализовать метод positionPartString,
 * который вернёт позиции, где находятся подстроки, составляющие
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
     * Ищем позиции подстрок, из которых можно составить передаваемое слово
     *
     * @param value - передаваемое слово
     * @return - либо массив с реальными позициями подстрок если нашли, либо - null
     */
    public int[] positionPartString(String value) {
        if (value == null || value.equals("")) {
            return null;
        }
        int[] res = {-1, -1};
        int center = value.length() / 2;
        String left = value.substring(0, center);
        String right = value.substring(center);
        for (int i = 0; i < partStrings.size(); i++) {
            String currentString = partStrings.get(i);
            if (currentString.equals(left)) {
                res[0] = i;
            } else if (currentString.equals(right)) {
                res[1] = i;
                if (res[0] != -1) {
                    break;
                }
            }
        }
        return (res[0] == -1 || res[1] == -1) ? null : res;
    }
}