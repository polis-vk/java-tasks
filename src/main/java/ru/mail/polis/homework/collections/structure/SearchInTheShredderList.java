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
    private final Map<String, Integer> mapOfStringToIndex = new HashMap<>();
    private final Map<Integer, String> maoOfIndexToString = new HashMap<>();
    private int size;

    public SearchInTheShredderList(List<String> partStrings) {
        for (String value : partStrings) {
            this.mapOfStringToIndex.put(value, size);
            maoOfIndexToString.put(size++, value);
        }
    }

    public void add(String value) {
        mapOfStringToIndex.put(value, size++);
    }

    public String get(int index) {
        return maoOfIndexToString.get(index);
    }

    /**
     * Ищем позиции подстрок из которых можно составить передаваемое слово
     *
     * @param value - передаваемоей слово
     * @return - либо массив с реальными позициями подстрок если нашли, либо - null
     */
    public int[] positionPartString(String value) {
        if (value == null) {
            return null;
        }
        int positionFirstPart = -1;
        int positionSecondPart = -1;

        String firstPart;
        String secondPart;

        for (int i = 0; i < size; i++) {
            firstPart = maoOfIndexToString.get(i);
            if (value.startsWith(firstPart)) {
                positionFirstPart = i;
                secondPart = value.substring(firstPart.length());
                positionSecondPart = mapOfStringToIndex.getOrDefault(secondPart, -1);
                if (positionSecondPart != -1) {
                    break;
                }
            }
        }

        return positionFirstPart == -1 || positionSecondPart == -1 ? null
                : new int[] {positionFirstPart, positionSecondPart};
    }
}
