package ru.mail.polis.homework.collections.structure;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Задание оценивается в 4 балла.
 * Необходимо реализовать класс которы умеет хранить строки и возвращать
 * список строк состоящий из того же набора буков, что ему передали строку.
 * Напишите какая сложность операций у вас получилась для каждого метода.
 */
public class CustomDictionary {

    private final Map<String, Set<String>> map = new HashMap<>();
    private int size;

    /**
     * Сохранить строку в структуру данных
     * @param value - передаваемая строка
     * @return - успешно сохранили строку или нет.
     *
     * Сложность - [k*log(k)]
     */
    public boolean add(String value) {
        if ((value == null) || (value.length() == 0)) {
            throw new IllegalArgumentException();
        }
        String key = getSortedKey(value);
        boolean isAdded = true;
        if (map.containsKey(key)) {
            isAdded = map.get(key).add(value);
        } else {
            Set<String> set = new HashSet<>();
            set.add(value);
            map.put(key, set);
        }
        if (isAdded) {
            size++;
        }
        return isAdded;
    }

    /**
     * Проверяем, хранится ли такая строка уже у нас
     * @param value - передаваемая строка
     * @return - есть такая строка или нет в нашей структуре
     *
     * Сложность - [k*log(k)]
     */
    public boolean contains(String value) {
        String key = getSortedKey(value);
        return map.get(key).contains(value);
    }

    /**
     * Удаляем сохраненную строку если она есть
     * @param value - какую строку мы хотим удалить
     * @return - true если удалили, false - если такой строки нет
     *
     * Сложность - [k*log(k)]
     */
    public boolean remove(String value) {
        String key = getSortedKey(value);
        Set<String> set = map.get(key);
        boolean isRemoved;
        if (set == null) {
            isRemoved = false;
        } else if ((set.size() == 1) && set.contains(value)) {
            map.remove(key);
            isRemoved = true;
        } else {
            isRemoved = map.get(key).remove(value);
        }
        if (isRemoved) {
            size--;
        }
        return isRemoved;
    }

    /**
     * Возвращает список из сохраненных ранее строк, которые состоят
     * из того же набора букв что нам передали строку.
     * Примеры:
     * сохраняем строки ["aaa", "aBa", "baa", "aaB"]
     * При поиске по строке "AAb" нам должен вернуться следующий
     * список: ["aBa","baa","aaB"]
     *
     * сохраняем строки ["aaa", "aAa", "a"]
     * поиск "aaaa"
     * результат: []
     * Как можно заметить - регистр строки не должен влиять на поиск, при этом
     * возвращаемые строки хранятся в том виде что нам передали изначально.
     *
     * @return - список слов которые состоят из тех же букв, что и передаваемая
     * строка.
     *
     * Сложность - [k*log(k) + O(n)]
     */
    public List<String> getSimilarWords(String value) {
        String key = getSortedKey(value);
        Set<String> set = map.get(key);
        return (set == null) ? new ArrayList<>() : new ArrayList<>(set);
    }

    /**
     * Колл-во хранимых строк.
     * @return - Колл-во хранимых строк.
     *
     * Сложность - [O(1)]
     */
    public int size() {
        return size;
    }

    private String getSortedKey(String key) {
        byte[] array = key.toLowerCase().getBytes();
        Arrays.sort(array);
        return Arrays.toString(array);
    }
}
