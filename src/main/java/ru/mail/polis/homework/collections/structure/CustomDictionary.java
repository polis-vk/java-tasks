package ru.mail.polis.homework.collections.structure;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.List;

/**
 * Задание оценивается в 4 тугрика.
 * Необходимо реализовать класс которы умеет хранить строки и возвращать
 * список строк состоящий из того же набора буков, что ему передали строку.
 * Напишите какая сложность операций у вас получилась для каждого метода.
 */
public class CustomDictionary {

    private Map<Map<Character, Integer>, Set<String>> storage;

    private int size;

    public CustomDictionary() {
        storage = new HashMap<>();
        size = 0;
    }

    /**
     * Сохранить строку в структуру данных
     *
     * @param value - передаваемая строка
     * @return - успешно сохранили строку или нет.
     * <p>
     * Сложность - [O(len(value)]
     * Учитывая то, что в hashMap операция добавления за константу
     */
    public boolean add(String value) {
        if (!isValid(value)) {
            throw new IllegalArgumentException();
        }

        Map<Character, Integer> lets = getLetsMap(value);
        boolean isElementAdded = storage.computeIfAbsent(lets, k -> new HashSet<>()).add(value);
        size = (isElementAdded) ? size + 1 : size;
        return isElementAdded;
    }

    /**
     * Проверяем, хранится ли такая строка уже у нас
     *
     * @param value - передаваемая строка
     * @return - есть такая строка или нет в нашей структуре
     * <p>
     * Сложность - [O(len(value))]
     * len(value) - нам все равно нужно построить наш ключ, по которому ищем value
     */
    public boolean contains(String value) {
        if (!isValid(value)) {
            throw new IllegalArgumentException();
        }

        Map<Character, Integer> lets = getLetsMap(value);

        Set<String> set = storage.get(lets);
        return set != null && set.contains(value);
    }

    /**
     * Удаляем сохраненную строку если она есть
     *
     * @param value - какую строку мы хотим удалить
     * @return - true если удалили, false - если такой строки нет
     * <p>
     * Сложность - [O(len(value)]
     */
    public boolean remove(String value) {
        if (!isValid(value)) {
            throw new IllegalArgumentException();
        }

        Map<Character, Integer> lets = getLetsMap(value);
        Set<String> set = storage.get(lets);
        if (set != null) {
            boolean isElementRemoved = set.remove(value);
            size = (isElementRemoved) ? size - 1 : size;
            return isElementRemoved;
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
     * Сложность - [O(len(value))]
     * тут конечно спорно, но используется System.arrayCopy, который грубо считается за O(1)
     */
    public List<String> getSimilarWords(String value) {
        if (!isValid(value)) {
            return Collections.emptyList();
        }

        Set<String> set = storage.get(getLetsMap(value));
        if (set != null) {
            return new ArrayList<>(set);
        }
        return Collections.emptyList();
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

    private Map<Character, Integer> getLetsMap(String value) {
        Map<Character, Integer> lets = new HashMap<>();
        for (int i = 0; i < value.length(); ++i) {
            lets.merge(Character.toLowerCase(value.charAt(i)), 1, Integer::sum);
        }
        return lets;
    }

    private boolean isValid(String value) {
        return value != null && !value.isEmpty();
    }
}
