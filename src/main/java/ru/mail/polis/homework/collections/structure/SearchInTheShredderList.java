package ru.mail.polis.homework.collections.structure;


import ru.mail.polis.homework.objects.RepeatingCharacters.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        Map<String, Integer> stringsToCompleteWord = new HashMap<>();

        int i = 0;
        int[] result = null;
        for (String s : partStrings) {
            if (stringsToCompleteWord.containsKey(s)) {
                if (value.startsWith(s)) {
                    result = new int[]{i, stringsToCompleteWord.get(s)};
                } else {
                    result = new int[]{stringsToCompleteWord.get(s), i};
                }
                break;
            }
            if (value.startsWith(s)) {
                stringsToCompleteWord.put(value.substring(s.length()), i);
            }
            if (value.endsWith(s)) {
                stringsToCompleteWord.put(value.substring(0, s.length() - 1), i);
            }
            i++;
        }

        return result;
    }
}
