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
        int midIndex = value.length() / 2;
        String left = value.substring(0, midIndex);
        String right = value.substring(midIndex);

        int leftIndex = -1;
        int rightIndex = -1;
        int i = 0;
        while (i < partStrings.size() && (leftIndex == -1 || rightIndex == -1) ) {

            if (leftIndex == -1 && partStrings.get(i).equals(left)) {
                leftIndex = i;
            } else if (rightIndex == -1 && partStrings.get(i).equals(right)) {
                rightIndex = i;
            }
            i++;
        }
        return (leftIndex != -1 && rightIndex != -1 ?  new int[]{leftIndex, rightIndex} : null);
    }
}
