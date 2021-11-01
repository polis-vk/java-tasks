package ru.mail.polis.homework.collections.structure;

import java.util.ArrayList;
import java.util.Arrays;
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
        int[] poses = new int[]{-1, -1}; // инициализация невозможными индексами
        if (partStrings == null
                || partStrings.isEmpty()
                || value == null
                || value.equals("")) {
            return null;
        }
        for (int i = 0; i < partStrings.size(); i++) {
            String part = partStrings.get(i);
            if (value.contains(part)) {
                if (getFirstLetter(value).equals(getFirstLetter(part))) {
                    poses[0] = i; // first word
                } else if (getLastLetter(value).equals(getLastLetter(part))) {
                    poses[1] = i; // last word
                }
            }
        }
        if (poses[0] == -1 || poses[1] == -1) {
            return null;
        } else {
            return poses;
        }
    }

    private String getFirstLetter(String value) {
        return value.substring(0, 1);
    }

    private String getLastLetter(String value) {
        return value.substring(value.length() - 1);
    }
}