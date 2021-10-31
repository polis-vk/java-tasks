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
        int mid = value.length() >> 1;
        String left = value.substring(0, mid);
        String right = value.substring(mid);
        int indexOfLeft = -1;
        int indexOfRight = -1;
        for (int i = 0; i < partStrings.size(); i++) {
            if (partStrings.get(i).equals(left)) {
                indexOfLeft = i;
                if (indexOfRight != -1) break;
            }
            if (partStrings.get(i).equals(right)) {
                indexOfRight = i;
                if (indexOfLeft != -1) break;
            }
        }
        if (indexOfLeft == -1 || indexOfRight == -1) {
            return null;
        }
        return new int[]{indexOfLeft, indexOfRight};
    }

}
