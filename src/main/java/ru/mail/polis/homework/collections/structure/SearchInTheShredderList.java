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
        int midIndex = value.length() / 2;
        String leftPart = value.substring(0, midIndex);
        String rightPart = value.substring(midIndex);
        int leftPartIndex = -1;
        int rightPartIndex = -1;

        for (int i = 0; i < partStrings.size(); i++) {
            if (leftPartIndex == -1 && partStrings.get(i).equals(leftPart)) {
                leftPartIndex = i;
            }
            if (i != leftPartIndex && partStrings.get(i).equals(rightPart)) {
                rightPartIndex = i;
            }
            if (leftPartIndex != -1 && rightPartIndex != -1) {
                return new int[]{leftPartIndex, rightPartIndex};
            }
        }
        return null;
    }
}
