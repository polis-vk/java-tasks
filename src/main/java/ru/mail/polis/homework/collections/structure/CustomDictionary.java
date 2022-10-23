package ru.mail.polis.homework.collections.structure;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

/**
 * Задание оценивается в 4 тугрика.
 * Необходимо реализовать класс которы умеет хранить строки и возвращать
 * список строк состоящий из того же набора буков, что ему передали строку.
 * Напишите какая сложность операций у вас получилась для каждого метода.
 */
public class CustomDictionary {
    private final Map<Map<Character, Integer>, List<String>> dictionary = new HashMap<>();
    private int size;

    /**
     * Сохранить строку в структуру данных
     * @param value - передаваемая строка
     * @return - успешно сохранили строку или нет.
     *
     * Сложность - [O(n)]
     */
    public boolean add(String value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException();
        }
        Map<Character, Integer> charFrequencies = getCharFrequencies(value);
        List<String> similarWords = dictionary.get(charFrequencies);
        if (similarWords == null) {
            similarWords = new ArrayList<>();
            similarWords.add(value);
            dictionary.put(charFrequencies, similarWords);
            size++;
            return true;
        } else if (!similarWords.contains(value)) {
            similarWords.add(value);
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
     * Сложность - [O(n)]
     */
    public boolean contains(String value) {
        List<String> similarWords = getSimilarWords(value);
        if (similarWords == null) {
            return false;
        }
        return similarWords.contains(value);
    }

    /**
     * Удаляем сохраненную строку если она есть
     * @param value - какую строку мы хотим удалить
     * @return - true если удалили, false - если такой строки нет
     *
     * Сложность - [O(n)]
     */
    public boolean remove(String value) {
        Map<Character, Integer> charFrequencies = getCharFrequencies(value);
        List<String> similarWords = dictionary.get(charFrequencies);
        if (similarWords != null && similarWords.contains(value)) {
            similarWords.remove(value);
            size--;
            return true;
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
     * Сложность - [O(n)]
     */
    public List<String> getSimilarWords(String value) {
        Map<Character, Integer> charFrequencies = getCharFrequencies(value);
        List<String> similarWords = dictionary.get(charFrequencies);
        if (similarWords == null) {
            return new ArrayList<>();
        }
        return similarWords;
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
     * Сложность - [O(n)]
     */
    public Map<Character, Integer> getCharFrequencies(String value) {
        Map<Character, Integer> charFrequencies = new HashMap<>();
        value = value.toLowerCase();
        int length = value.length();
        for (int i = 0; i < length; i++) {
            char c = value.charAt(i);
            charFrequencies.merge(c, 1, (oldValue, newValue) -> oldValue + 1);
        }
        return charFrequencies;
    }
}
