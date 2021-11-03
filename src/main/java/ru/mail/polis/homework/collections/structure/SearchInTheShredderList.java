package ru.mail.polis.homework.collections.structure;

import java.util.*;
import java.util.function.*;
import java.util.stream.IntStream;

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
    private final List<String> partStrings;

    public SearchInTheShredderList() {
        this.partStrings = new ArrayList<>();
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
     * @return - либо массив с реальными позициями подстрок если нашли, либо null
     */
    public int[] positionPartString(String value) {
        if (value == null) {
            return null;
        }
        int prefixIndex = -1;
        int suffixIndex = -1;
        for (int i = 0; i < partStrings.size() && (prefixIndex == -1 || suffixIndex == -1); i++) {
            if (value.startsWith(get(i))) {
                prefixIndex = i;
            } else if (value.endsWith(get(i))) {
                suffixIndex = i;
            }
        }
        if (prefixIndex == -1 || suffixIndex == -1) {
            return null;
        }
        return new int[] {prefixIndex, suffixIndex};
    }
}
