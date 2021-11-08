package ru.mail.polis.homework.collections.structure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Задание оценивается в 4 балла.
 * Необходимо реализовать класс которы умеет хранить строки и возвращать
 * список строк состоящий из того же набора буков, что ему передали строку.
 * Напишите какая сложность операций у вас получилась для каждого метода.
 */
public class CustomDictionary {
    private final Map<String, List<String>> dictionary = new HashMap<>();
    private int size;

    /**
     * Сохранить строку в структуру данных
     * @param value - передаваемая строка
     * @return - успешно сохранили строку или нет.
     *
     * Сложность - [O(m*log(m) + k), где m - длина строки value, k - количество элементов в List]
     */
    public boolean add(String value) {
        if (value == null || value.equals("")) {
            throw new IllegalArgumentException();
        }
        String tmp = toSortedLowRegisterCharArray(value);
        if (dictionary.containsKey(tmp)) {
            if (dictionary.get(tmp).contains(value)) {
                return false;
            }
            size++;
            return dictionary.get(tmp).add(value);
        }
        List<String> list = new ArrayList<>();
        list.add(value);
        dictionary.put(tmp, list);
        size++;
        return true;
    }

    /**
     * Проверяем, хранится ли такая строка уже у нас
     * @param value - передаваемая строка
     * @return - есть такая строка или нет в нашей структуре
     *
     * Сложность - [O(m*log(m)), где m - длина строки value]
     */
    public boolean contains(String value) {
        String tmp = toSortedLowRegisterCharArray(value);
        return dictionary.get(tmp) != null && dictionary.get(tmp).contains(value);
    }

    /**
     * Удаляем сохраненную строку если она есть
     * @param value - какую строку мы хотим удалить
     * @return - true если удалили, false - если такой строки нет
     *
     * Сложность - [O(m*log(m) + k), где m - длина строки value, k - количество элементов в List]
     */
    public boolean remove(String value) {
        String tmp = toSortedLowRegisterCharArray(value);
        if (contains(value)) {
            if (dictionary.get(tmp).size() == 1) {
                size--;
                dictionary.remove(tmp);
                return true;
            }
            size--;
            return dictionary.get(tmp).remove(value);
        }
        return false;
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
     * Сложность - [O(m*log(m)), где m - длина строки value]
     */
    public List<String> getSimilarWords(String value) {
        if (value == null || value.equals("")) {
            return Collections.emptyList();
        }
        String tmp = toSortedLowRegisterCharArray(value);
        if (!dictionary.containsKey(tmp)) {
            return Collections.emptyList();
        }
        return dictionary.get(tmp);
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

    /**
     * Сложность - [O(m*log(m)), где m - длина строки value]
     */
    private String toSortedLowRegisterCharArray(String value) {
        char[] tmp = value.toLowerCase().toCharArray();
        Arrays.sort(tmp);
        return String.valueOf(tmp);
    }
}
