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
        if (value == null) {
            return null;
        }
        int[] result = {-1, -1};
        String first = value.substring(0, value.length() / 2);
        String second = value.substring(value.length() / 2);
        int indexCounter = 0;
        for (String string : partStrings) {
            if (string.equals(first) && result[0] == -1) {
                result[0] = indexCounter;
            } else if (string.equals(second) && result[1] == -1) {
                result[1] = indexCounter;
            }
            indexCounter++;
        }
        if ((result[0] == -1) || (result[1] == -1)) {
            return null;
        }
        return result;
    }
}
