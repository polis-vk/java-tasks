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
    private final int BREAK_POINT = 2;

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

        int[] result = new int[2];
        String first = value.substring(0, (value.length() - 1) / 2);
        String second = value.substring((value.length() - 1) / 2);

        int stop = 0;
        for (int i = 0; i < partStrings.size(); i++) {
            if (partStrings.get(i).equals(first)) {
                result[0] = i;
                stop++;
            }
            if (partStrings.get(i).equals(second)) {
                result[1] = i;
                stop++;
            }

            if (stop == BREAK_POINT) {
                break;
            }
        }
        return result[0] == 0 && result[1] == 0 ? null : result;
    }
}
