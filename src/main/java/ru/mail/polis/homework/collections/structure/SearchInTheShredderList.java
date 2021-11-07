package ru.mail.polis.homework.collections.structure;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
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
        if (value == null || value.equals("")) {
            return null;
        }

        int mid = value.length() >> 1;
        String leftPart = value.substring(0, mid);
        String rightPart = value.substring(mid);

        int leftPartInd = -1;
        int rightPartInd = -1;

        int ind = 0;
        for (String el : partStrings) {
            if (el.equals(leftPart)) {
                leftPartInd = ind;
            } else if (el.equals(rightPart)) {
                rightPartInd = ind;
            }

            if (leftPartInd != -1 && rightPartInd != -1) {
                break;
            }

            ind++;
        }

        if (leftPartInd != -1 && rightPartInd != -1) {
            return new int[]{leftPartInd, rightPartInd};
        }

        return null;
    }
}
