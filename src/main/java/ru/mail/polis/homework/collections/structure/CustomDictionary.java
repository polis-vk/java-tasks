package ru.mail.polis.homework.collections.structure;

import jdk.internal.joptsimple.internal.Strings;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Задание оценивается в 4 тугрика.
 * Необходимо реализовать класс которы умеет хранить строки и возвращать
 * список строк состоящий из того же набора буков, что ему передали строку.
 * Напишите какая сложность операций у вас получилась для каждого метода.
 */
public class CustomDictionary {

    private Map<HashMap<Character, Integer>, LinkedHashSet<String>> dictionary
            = new HashMap<HashMap<Character, Integer>, LinkedHashSet<String>>();

    private static HashMap<Character, Integer> getCounter(String str) {
        HashMap<Character, Integer> counter = new HashMap<>();
        String strLowered = str.toLowerCase();
        for (int i = 0; i < strLowered.length(); i++) {
            if (counter.containsKey(strLowered.charAt(i))) {
                counter.compute(strLowered.charAt(i), (key, value) -> value + 1);
            } else {
                counter.put(strLowered.charAt(i), 1);
            }
        }
        return counter;
    }

    /**
     * Сохранить строку в структуру данных
     * @param value - передаваемая строка
     * @return - успешно сохранили строку или нет.
     *
     * Сложность - [В лучшем случае O(k), где k - длина строки]
     */

    public boolean add(String value) {
        if (value == null || value.equals("")) {
            throw new IllegalArgumentException();
        }
        HashMap<Character, Integer> valueCounter = getCounter(value);
        if (dictionary.containsKey(valueCounter)) {
            return dictionary.get(valueCounter).add(value);
        }
        LinkedHashSet<String> strings = new LinkedHashSet<>();
        strings.add(value);
        dictionary.put(valueCounter, strings);
        return true;
    }

    /**
     * Проверяем, хранится ли такая строка уже у нас
     * @param value - передаваемая строка
     * @return - есть такая строка или нет в нашей структуре
     *
     * Сложность - [В лучшем случае O(k), где k - длина строки]
     */
    public boolean contains(String value) {
        HashMap<Character, Integer> valueCounter = getCounter(value);
        return dictionary.containsKey(valueCounter)
                && dictionary.get(valueCounter).contains(value);
    }

    /**
     * Удаляем сохраненную строку если она есть
     * @param value - какую строку мы хотим удалить
     * @return - true если удалили, false - если такой строки нет
     *
     * Сложность - [В лучшем случае O(k), где k - длина строки]
     */
    public boolean remove(String value) {
        HashMap<Character, Integer> valueCounter = getCounter(value);
        if (dictionary.containsKey(valueCounter)) {
            return dictionary.get(valueCounter).remove(value);
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
     * Сложность - [В лучшем случае O(k), где k - длина строки]
     */
    public List<String> getSimilarWords(String value) {
        HashMap<Character, Integer> valueCounter = getCounter(value);
        if (!dictionary.containsKey(valueCounter)) {
            return Collections.emptyList();
        }
        return dictionary.get(valueCounter).stream().collect(Collectors.toList());
    }

    /**
     * Колл-во хранимых строк.
     * @return - Колл-во хранимых строк.
     *
     * Сложность - [В лучшем случае O(n), где n - размер мапы]
     */
    public int size() {
        int resultSize = 0;
        for (Map.Entry<HashMap<Character, Integer>, LinkedHashSet<String>> entry : dictionary.entrySet()) {
            resultSize += entry.getValue().size();
        }
        return resultSize;
    }
}
