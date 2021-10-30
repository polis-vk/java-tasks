package ru.mail.polis.homework.collections.structure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

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
        if(value == null || value.length() < 2) {
            return null;
        }
        int l = value.length();
        int[] array = new int[2];
        // пополам это на 2 половины по разности длин не более 1?
        array[0] = this.partStrings.indexOf(value.substring(0, l / 2));
        array[1] = this.partStrings.indexOf(value.substring(l / 2, l));
        if(array[0] == -1) { // если первой половины нет, то может быть есть вторая?
            array = new int[] {array[1]};
        } else if(array[1] == -1) { // если только второй половины нет, то передаем только первую
            array = new int[] {array[0]};
        }
        // массив может быть из 1 элемента, и если он -1, значит ничего полезного там нет
        return array[0] == -1 ? null : array;
    }
}
