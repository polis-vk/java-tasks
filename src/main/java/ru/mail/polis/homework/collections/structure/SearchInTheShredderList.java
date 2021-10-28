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
        int notPositionInArray = -1;
        int[] result = new int[]{notPositionInArray, notPositionInArray};
        String firstPartOfWord = value.substring(0, value.length() / 2);
        String secondPartOfWord = value.substring(value.length() / 2);

        for (int i = 0; i < partStrings.size(); ++i) {
            if (firstPartOfWord.equals(partStrings.get(i))) {
                result[0] = i;
            }
            if (secondPartOfWord.equals(partStrings.get(i))) {
                result[1] = i;
            }
        }
        if (result[0] != notPositionInArray && result[1] != notPositionInArray) {
            return result;
        }
        return null;
    }
}