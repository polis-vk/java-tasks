package ru.mail.polis.homework.collections.structure;

import java.util.*;

/**
 * Задание оценивается в 4 тугрика.
 * Необходимо реализовать класс, который умеет хранить строки и возвращать
 * список строк, состоящий из того же набора букв, что ему передали строку.
 * Напишите какая сложность операций у вас получилась для каждого метода.
 */
public class CustomDictionary {

    private static final String ILLEGAL_ARGUMENT = "String cannot be empty or null.";

    private final Map<Map<Character, Integer>, Set<String>> dictionary;
    private int size;

    public CustomDictionary() {
        dictionary = new HashMap<>();
    }

    /**
     * Сохранить строку в структуру данных
     * @param value - передаваемая строка
     * @return - успешно сохранили строку или нет.
     *
     * Сложность - [O(n), где n - длина строки]
     */
    public boolean add(String value) {
        checkString(value);

        Map<Character, Integer> key = convertStringToMap(value);
        dictionary.putIfAbsent(key, new LinkedHashSet<>());

        boolean isAdded = dictionary.get(key).add(value);
        if (isAdded) {
            size++;
            return true;
        }

        return false;
    }

    /**
     * Проверяем, хранится ли такая строка уже у нас
     * @param value - передаваемая строка
     * @return - есть такая строка или нет в нашей структуре
     *
     * Сложность - [O(n), где n - длина строки]
     */
    public boolean contains(String value) {
        checkString(value);

        Map<Character, Integer> key = convertStringToMap(value);
        Set<String> set = dictionary.get(key);

        return set != null && set.contains(value);
    }

    /**
     * Удаляем сохраненную строку, если она есть
     * @param value - какую строку мы хотим удалить
     * @return - true - если удалили, false - если такой строки нет
     *
     * Сложность - [O(n), где n - длина строки]
     */
    public boolean remove(String value) {
        checkString(value);

        Map<Character, Integer> key = convertStringToMap(value);
        Set<String> set = dictionary.get(key);
        if (set == null) {
            return false;
        }

        boolean isRemoved = set.remove(value);
        if (isRemoved) {
            size--;
        }

        return isRemoved;
    }

    /**
     * Возвращает список из сохраненных ранее строк, которые состоят
     * из того же набора букв, что нам передали строку.
     * Примеры:
     * сохраняем строки ["aaa", "aBa", "baa", "aaB"]
     * При поиске по строке "AAb" нам должен вернуться следующий
     * список: ["aBa","baa","aaB"]
     *
     * сохраняем строки ["aaa", "aAa", "a"]
     * поиск "aaaa"
     * результат: []
     * Как можно заметить - регистр строки не должен влиять на поиск, при этом
     * возвращаемые строки хранятся в том виде, что нам передали изначально.
     *
     * @return - список слов которые состоят из тех же букв, что и передаваемая
     * строка.
     *
     * Сложность - [O(n), где n - длина строки]
     */
    public List<String> getSimilarWords(String value) {
        checkString(value);

        Map<Character, Integer> key = convertStringToMap(value);
        Set<String> set = dictionary.get(key);
        if (set == null) {
            return new ArrayList<>();
        }

        return new ArrayList<>(set);
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
     * Преобразует строку в представление вида Map<Character, Integer>,
     * где хранятся символы строки и их количество.
     *
     * @param value - передаваемая строка
     * @return - map, хранящая символы строки и их количество
     *
     * Сложность - [O(n), где n - длина строки]
     */
    private Map<Character, Integer> convertStringToMap(String value) {
        Map<Character, Integer> map = new HashMap<>();

        char[] chars = value.toLowerCase().toCharArray();
        for (char symbol : chars) {
            map.merge(symbol, 1, Integer::sum);
        }

        return map;
    }

    private void checkString(String value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException(ILLEGAL_ARGUMENT);
        }
    }
}
