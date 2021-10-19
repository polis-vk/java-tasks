package ru.mail.polis.homework.collections.structure;

import java.util.*;

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
        if (value == null || value.length() == 0) {
            return null;
        }
        // Массив, на i-м элементе которого будет стоять индекс соответствующего обрывка.
        int[] indexes = new int[value.length()];
        Arrays.fill(indexes, -1);
        for (int i = 0; i < partStrings.size(); i++) {
            int matchIndex = value.indexOf(partStrings.get(i));
            if (matchIndex != -1) {
                // Соответственно заполняем все нужны элементы индексом обрывка.
                Arrays.fill(indexes, matchIndex, matchIndex + partStrings.get(i).length(), i);
            }
        }
        List<Integer> resultList = new LinkedList<>();
        int lastIndex = -1;
        // Достанем за O(n) уникальные индексы.
        for (int i = 0; i < indexes.length; i++) {
            if (indexes[i] == -1) {
                return null;
            }
            if (indexes[i] != lastIndex) {
                resultList.add(indexes[i]);
                lastIndex = indexes[i];
            }
        }
        int[] result = new int[resultList.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = resultList.remove(0);
        }
        if (result.length != 2) {
            return null;
        }
        return result;
    }
}
