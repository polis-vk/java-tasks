package ru.mail.polis.homework.collections.structure;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;

/**
 * Задание оценивается в 4 балла.
 * Необходимо реализовать класс которы умеет хранить строки и возвращать
 * список строк состоящий из того же набора буков, что ему передали строку.
 * Напишите какая сложность операций у вас получилась для каждого метода.
 */
public class CustomDictionary {

    private final Map<Map<Character, Integer>, Set<String>> dict = new HashMap<>();
    private int size = 0;

    private Map<Character, Integer> genKey(String value) {
        Map<Character, Integer> key = new HashMap<>();
        for (char sym : value.toCharArray()) {
            char upperSym = Character.toUpperCase(sym);
            key.merge(upperSym, 1, Integer::sum);
        }
        return key;
    }

    /**
     * Сохранить строку в структуру данных
     *
     * @param value - передаваемая строка
     * @return - успешно сохранили строку или нет.
     * <p>
     * Сложность - O(n) n - количество символов в строке
     */

    public boolean add(String value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException();
        }
        Map<Character, Integer> key = genKey(value);
        Set<String> strGroup = dict.computeIfAbsent(key, k -> new HashSet<>());
        boolean result = strGroup.add(value);
        if (result) {
            size++;
        }
        return result;
    }

    /**
     * Проверяем, хранится ли такая строка уже у нас
     *
     * @param value - передаваемая строка
     * @return - есть такая строка или нет в нашей структуре
     * <p>
     * Сложность - O(n) n - количество символов в строке
     */
    public boolean contains(String value) {
        Map<Character, Integer> key = genKey(value);
        Set<String> strGroup = dict.get(key);
        if (strGroup == null) {
            return false;
        } else {
            return strGroup.contains(value);
        }
    }

    /**
     * Удаляем сохраненную строку если она есть
     *
     * @param value - какую строку мы хотим удалить
     * @return - true если удалили, false - если такой строки нет
     * <p>
     * Сложность - O(n) n - количество символов в строке
     */
    public boolean remove(String value) {
        Map<Character, Integer> key = genKey(value);
        Set<String> strGroup = dict.get(key);
        if (strGroup == null) {
            return false;
        } else {
            boolean result = strGroup.remove(value);
            if (strGroup.isEmpty()) {
                dict.remove(key);
            }
            if (result) {
                size--;
            }
            return result;
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
     * Сложность - O(n) n - количество символов в строке
     */
    public List<String> getSimilarWords(String value) {
        Map<Character, Integer> key = genKey(value);
        Set<String> strGroup = dict.get(key);
        if (strGroup == null) {
            return Collections.emptyList();
        } else {
            return new ArrayList<>(strGroup);
        }
    }

    /**
     * Колл-во хранимых строк.
     *
     * @return - Колл-во хранимых строк.
     * <p>
     * Сложность - O(1)
     */
    public int size() {
        return size;
    }


}
