package ru.mail.polis.homework.collections.structure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        if(value == null || value.isEmpty()) return null;
        int [] arr = new int[]{-1,-1};
        String first_ = value.substring(0,value.length()/2),
                second = value.substring(value.length()/2, value.length());
        for(int i = 0; i < partStrings.size(); ++i)
        {
            if(arr[0] == -1 && first_.equals(partStrings.get(i))) {arr[0] = i; continue;}
            if(arr[1] == -1 && second.equals(partStrings.get(i))) {arr[1] = i; continue;}
        }
        if(arr[0] != -1 && arr[1] != -1) return arr;
        return null;
    }
}
