package ru.mail.polis.homework.collections.structure;


import ru.mail.polis.homework.objects.RepeatingCharacters.Pair;

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
    private List<String> partStrings;

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
        ArrayList<Pair<Integer, Integer>> startStringsLengthsAndIndexes = new ArrayList<>();
        ArrayList<Pair<Integer, Integer>> endStringsLengthsAndIndexes = new ArrayList<>();
        int i = 0;
        for (String s : partStrings) {
            if (s.length() == value.length() / 2 || s.length() == value.length() / 2 + 1) {
                if (value.startsWith(s)) {
                    startStringsLengthsAndIndexes.add(new Pair<>(s.length(), i));
                }
                if (value.endsWith(s)) {
                    endStringsLengthsAndIndexes.add(new Pair<>(s.length(), i));
                }
            }

            i++;
        }

        int[] result = new int[2];
        for (Pair<Integer, Integer> possibleStart : startStringsLengthsAndIndexes) {
            for (Pair<Integer, Integer> possibleEnd : endStringsLengthsAndIndexes) {
                if (possibleStart.getFirst() + possibleEnd.getFirst() == value.length()
                        && !possibleStart.getFirst().equals(possibleEnd.getFirst())) {
                    result[0] = possibleStart.getSecond();
                    result[1] = possibleEnd.getSecond();
                    return result;
                }
            }
        }
        return null;
    }
}
