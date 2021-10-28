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
    private static final int ILLEGAL_INDEX = -1;
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
        if (value == null) {
            return null;
        }
        String firstHalf = value.substring(0, value.length() / 2);
        String secondHalf = value.substring(value.length() / 2);
        int[] ans = new int[]{ILLEGAL_INDEX, ILLEGAL_INDEX};
        for (int i = 0; i < partStrings.size(); i++) {
            String onePartSting = partStrings.get(i);
            if (onePartSting.equals(firstHalf)) {
                ans[0] = i;
            } else if (onePartSting.equals(secondHalf)) {
                ans[1] = i;
            }
        }
        return ans[0] == ILLEGAL_INDEX && ans[1] == ILLEGAL_INDEX
                ? null : ans;
    }
}
