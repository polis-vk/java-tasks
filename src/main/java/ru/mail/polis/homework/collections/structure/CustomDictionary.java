package ru.mail.polis.homework.collections.structure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Задание оценивается в 4 тугрика.
 * Необходимо реализовать класс которы умеет хранить строки и возвращать
 * список строк состоящий из того же набора буков, что ему передали строку.
 * Напишите какая сложность операций у вас получилась для каждого метода.
 */
public class CustomDictionary {
    private final Map<String, Set<String>> map = new HashMap<>();
    private int size;

    /**
     * Сохранить строку в структуру данных
     *
     * @param value - передаваемая строка
     * @return - успешно сохранили строку или нет.
     * <p>
     * Сложность - [O(n), где n - длина строки]
     */
    public boolean add(String value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException();
        }
        if (contains(value)) {
            return false;
        }
        String sortedLowerCaseValue = getSortedLowerCaseString(value);
        Set<String> set = map.get(sortedLowerCaseValue);
        if (set != null) {
            set.add(value);
        } else {
            Set<String> newSet = new HashSet<>();
            newSet.add(value);
            map.put(sortedLowerCaseValue, newSet);
        }
        size++;
        return true;
    }

    /**
     * Проверяем, хранится ли такая строка уже у нас
     *
     * @param value - передаваемая строка
     * @return - есть такая строка или нет в нашей структуре
     * <p>
     * Сложность - [O(n), где n - длина строки]
     */
    public boolean contains(String value) {
        String sortedLowerCaseValue = getSortedLowerCaseString(value);
        Set<String> set = map.get(sortedLowerCaseValue);
        return set != null && set.contains(value);
    }

    /**
     * Удаляем сохраненную строку если она есть
     *
     * @param value - какую строку мы хотим удалить
     * @return - true если удалили, false - если такой строки нет
     * <p>
     * Сложность - [O(n), где n - длина строки]
     */
    public boolean remove(String value) {
        if (!contains(value)) {
            return false;
        }
        String sortedLowerCaseValue = getSortedLowerCaseString(value);
        Set<String> set = map.get(sortedLowerCaseValue);
        if (set.size() == 1) {
            map.remove(sortedLowerCaseValue);
            size--;
            return true;
        } else {
            size--;
            return set.remove(value);
        }
    }

    /**
     * Возвращает список из сохраненных ранее строк, которые состоят
     * из того же набора букв что нам передали строку.
     * Примеры:
     * сохраняем строки ["aaa", "aBa", "baa", "aaB"]
     * При поиске по строке "AAb" нам должен вернуться следующий
     * список: ["aBa","baa","aaB"]
     * <p>
     * сохраняем строки ["aaa", "aAa", "a"]
     * поиск "aaaa"
     * результат: []
     * Как можно заметить - регистр строки не должен влиять на поиск, при этом
     * возвращаемые строки хранятся в том виде что нам передали изначально.
     *
     * @return - список слов которые состоят из тех же букв, что и передаваемая
     * строка.
     * <p>
     * Сложность - [O(n), где n - длина строки]
     */
    public List<String> getSimilarWords(String value) {
        String sortedLowerCaseValue = getSortedLowerCaseString(value);
        Set<String> set = map.get(sortedLowerCaseValue);
        return set != null ? new ArrayList<>(set) : Collections.emptyList();
    }

    /**
     * Колл-во хранимых строк.
     *
     * @return - Колл-во хранимых строк.
     * <p>
     * Сложность - [O(1)]
     */
    public int size() {
        return size;
    }

    private static String getSortedLowerCaseString(String value) {
        char[] chars = value.toLowerCase().toCharArray();
        countingSort(chars, 0, chars.length);
        return new String(chars);
    }

    /**
     * Сортировка подсчётом.
     * <p>
     * Сложность - [O(n), где n - длина массива]
     */
    private static void countingSort(char[] array, int fromInclusive, int toExclusive) {
        int maxValue = array[fromInclusive];
        int minValue = array[fromInclusive];
        for (int i = fromInclusive; i < toExclusive; i++) {
            if (array[i] > maxValue) {
                maxValue = array[i];
            }
            if (array[i] < minValue) {
                minValue = array[i];
            }
        }
        char[][] sortedArray = new char[2][maxValue - minValue + 1];
        for (int i = fromInclusive; i < toExclusive; i++) {
            sortedArray[0][array[i] - minValue] = array[i];
            sortedArray[1][array[i] - minValue]++;
        }
        int counter = 0;
        for (int i = 0; counter < array.length; i++) {
            for (int j = 0; j < (int) sortedArray[1][i]; j++) {
                array[counter++] = sortedArray[0][i];
            }
        }
    }
}
