package ru.mail.polis.homework.collections.structure;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.List;

/**
 * Задание оценивается в 4 тугрика.
 * Необходимо реализовать класс которы умеет хранить строки и возвращать
 * список строк состоящий из того же набора буков, что ему передали строку.
 * Напишите какая сложность операций у вас получилась для каждого метода.
 */
public class CustomDictionary {

    private Map<Map<Character, Integer>, Set<String>> dictionary
            = new HashMap<>();

    private static Map<Character, Integer> getCounter(String str) {
        Map<Character, Integer> counter = new HashMap<>();
        String strLowered = str.toLowerCase();
        for (int i = 0; i < strLowered.length(); i++) {
            counter.merge(strLowered.charAt(i), 1, (value, mergeValue) -> value + mergeValue);
        }
        return counter;
    }

    /**
     * Сохранить строку в структуру данных
     * @param value - передаваемая строка
     * @return - успешно сохранили строку или нет.
     *
     * Сложность - [O(n), где n - длина строки]
     */

    public boolean add(String value) {
        if (value == null || value.equals("")) {
            throw new IllegalArgumentException();
        }
        Map<Character, Integer> valueCounter = getCounter(value);
        Set<String> similarValues = dictionary.get(valueCounter);
        if (similarValues != null) {
            return similarValues.add(value);
        }
        similarValues = new LinkedHashSet<>();
        similarValues.add(value);
        dictionary.put(valueCounter, similarValues);
        return true;
    }

    /**
     * Проверяем, хранится ли такая строка уже у нас
     * @param value - передаваемая строка
     * @return - есть такая строка или нет в нашей структуре
     *
     * Сложность - [O(n), где n - длина строки]
     */
    public boolean contains(String value) {
        Map<Character, Integer> valueCounter = getCounter(value);
        Set<String> similarValues = dictionary.get(valueCounter);
        if (similarValues == null) {
            return false;
        }
        return similarValues.contains(value);
    }

    /**
     * Удаляем сохраненную строку если она есть
     * @param value - какую строку мы хотим удалить
     * @return - true если удалили, false - если такой строки нет
     *
     * Сложность - [O(n), где n - длина строки]
     */
    public boolean remove(String value) {
        Map<Character, Integer> valueCounter = getCounter(value);
        Set<String> similarValues = dictionary.get(valueCounter);
        if (similarValues == null) {
            return false;
        }
        return similarValues.remove(value);
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
     * Сложность - [O(n), где n - длина строки]
     */
    public List<String> getSimilarWords(String value) {
        Map<Character, Integer> valueCounter = getCounter(value);
        Set<String> similarValues = dictionary.get(valueCounter);
        if (similarValues == null) {
            return Collections.emptyList();
        }
        return similarValues.stream().collect(Collectors.toList());
    }

    /**
     * Колл-во хранимых строк.
     * @return - Колл-во хранимых строк.
     *
     * Сложность - [O(n), где n - длина строки]
     */
    public int size() {
        int resultSize = 0;
        for (Map.Entry<Map<Character, Integer>, Set<String>> entry : dictionary.entrySet()) {
            resultSize += entry.getValue().size();
        }
        return resultSize;
    }
}
