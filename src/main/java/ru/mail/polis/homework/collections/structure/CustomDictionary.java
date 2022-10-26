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
    private final Map<String, Set<String>> dictionary = new HashMap<>();
    private int size;

    /**
     * Сохранить строку в структуру данных
     *
     * @param value - передаваемая строка
     * @return - успешно сохранили строку или нет.
     * <p>
     * Сложность - []
     */
    // Сложность О(m), где m - кол-во букв в передаваемом слове.
    public boolean add(String value) {
        if (contains(value)) {
            return false;
        }

        String alphabetWord = CreateAlphabetWord(value);

        if (dictionary.containsKey(alphabetWord)) {
            dictionary.get(alphabetWord).add(value);
        } else {
            Set<String> words = new HashSet<>();
            words.add(value);
            dictionary.put(alphabetWord, words);
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
     * Сложность - []
     */
    // Сложность в среднем О(m), где m - кол-во букв в передаваемом слове.
    public boolean contains(String value) {
        if (dictionary.isEmpty()) {
            return false;
        }

        String alphabetWord = CreateAlphabetWord(value);

        Set<String> words = dictionary.get(alphabetWord);
        if (words == null) {
            return false;
        }
        return words.contains(value);
    }

    /**
     * Удаляем сохраненную строку если она есть
     *
     * @param value - какую строку мы хотим удалить
     * @return - true если удалили, false - если такой строки нет
     * <p>
     * Сложность - []
     */
    // Сложность в среднем О(m), где m - кол-во букв в передаваемом слове.
    public boolean remove(String value) {
        if (dictionary.isEmpty()) {
            return false;
        }

        String alphabetWord = CreateAlphabetWord(value);
        Set<String> stringSet = dictionary.get(alphabetWord);

        if (stringSet.isEmpty()) {
            return false;
        }

        if (stringSet.contains(value)) {
            stringSet.remove(value);
            if (stringSet.isEmpty()) {
                dictionary.remove(alphabetWord);
            }
            size--;
            return true;
        }
        return false;
    }

    /**
     * Возвращает список из сохраненных ранее строк, которые состоят
     * из того же набора букв, что нам передали строку.
     * Примеры:
     * сохраняем строки ["aaa", "aBa", "baa", "aaB"]
     * При поиске по строке "AAb" нам должен вернуться следующий
     * список: ["aBa","baa","aaB"]
     * <p>
     * сохраняем строки ["aaa", "aAa", "a"]
     * поиск "aaaa"
     * результат: []
     * Как можно заметить - регистр строки не должен влиять на поиск, при этом
     * возвращаемые строки хранятся в том виде, что нам передали изначально.
     *
     * @return - список слов которые состоят из тех же букв, что и передаваемая
     * строка.
     * <p>
     * Сложность - []
     */
    // Сложность О(m), где m - кол-во букв в передаваемом слове.
    public List<String> getSimilarWords(String value) {
        if (dictionary.isEmpty()) {
            return Collections.emptyList();
        }

        String word = CreateAlphabetWord(value);
        Set<String> stringSet = dictionary.get(word);

        if (stringSet == null) {
            return Collections.emptyList();
        }
        return new ArrayList<>(stringSet);
    }

    /**
     * Колл-во хранимых строк.
     *
     * @return - Колл-во хранимых строк.
     * <p>
     * Сложность - []
     */
    // Сложность О(1).
    public int size() {
        return size;
    }

    // Сложность О(m), где m - кол-во букв в передаваемом слове.
    private String CreateAlphabetWord(String value) {
        if (value == null || value.length() == 0) {
            throw new IllegalArgumentException();
        }
        char[] alphabetWord = value.toLowerCase().toCharArray();
        Arrays.sort(alphabetWord);
        return new String(alphabetWord);
    }
}
