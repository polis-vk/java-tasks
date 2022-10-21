package ru.mail.polis.homework.collections.structure;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
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

    private static final String ILLEGAL_ARGUMENT = "String can't be empty!";

    private final Map<Map<Character, Integer>, Set<String>> map;
    private int size;

    public CustomDictionary() {
        this.map = new HashMap<>();
    }

    private static Map<Character, Integer> constructKey(String value) {
        Map<Character, Integer> key = new HashMap<>();

        for (char c : value.toLowerCase().toCharArray()) {
            key.merge(c, 1, (oldVal, val) -> oldVal + 1);
        }

        return key;
    }

    /**
     * Сохранить строку в структуру данных
     * @param value - передаваемая строка
     * @return - успешно сохранили строку или нет.
     *
     * Сложность - [O(m), где m - длина слова]
     */
    public boolean add(String value) {
        checkString(value);

        Map<Character, Integer> key = constructKey(value);

        map.putIfAbsent(key, new LinkedHashSet<>());

        Set<String> valueSet = map.get(key);

        if (valueSet.contains(value)) {
            return false;
        }

        valueSet.add(value);

        size++;

        return true;
    }

    /**
     * Проверяем, хранится ли такая строка уже у нас
     * @param value - передаваемая строка
     * @return - есть такая строка или нет в нашей структуре
     *
     * Сложность - [O(m), где m - длина слова]
     */
    public boolean contains(String value) {
        checkString(value);

        Map<Character, Integer> key = constructKey(value);

        Set<String> set = map.get(key);

        if (set == null) {
            return false;
        }

        return set.contains(value);
    }

    /**
     * Удаляем сохраненную строку если она есть
     * @param value - какую строку мы хотим удалить
     * @return - true если удалили, false - если такой строки нет
     *
     * Сложность - [O(m), где m - длина слова]
     */
    public boolean remove(String value) {
        checkString(value);

        Map<Character, Integer> key = constructKey(value);

        Set<String> set = map.get(key);

        if (set == null || !set.contains(value)) {
            return false;
        }

        set.remove(value);
        size--;

        return true;
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
     * По сути, здесь опять идёт создание ключа за O(m), где m - длина слова
     * Но в то же время, идёт помещение всех элементов из множества (значения) в лист
     * Это имеет сложность O(n), где n - количество элементов в множестве
     * Сложность - [O(n + m)]
     */
    public List<String> getSimilarWords(String value) {
        checkString(value);

        Map<Character, Integer> key = constructKey(value);

        Set<String> setByValue = map.get(key);

        if (setByValue == null) {
            return Collections.emptyList();
        }

        return new ArrayList<>(setByValue);
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

    private void checkString(String value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException(ILLEGAL_ARGUMENT);
        }
    }
}
