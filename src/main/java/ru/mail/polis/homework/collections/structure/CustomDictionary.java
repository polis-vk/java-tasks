package ru.mail.polis.homework.collections.structure;

import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Задание оценивается в 4 тугрика.
 * Необходимо реализовать класс которы умеет хранить строки и возвращать
 * список строк состоящий из того же набора буков, что ему передали строку.
 * Напишите какая сложность операций у вас получилась для каждого метода.
 */
public class CustomDictionary {
    private final Map<Map<Character, Integer>, Set<String>> dictionary = new HashMap<>();
    private int size;

    /**
     * Сохранить строку в структуру данных
     *
     * @param value - передаваемая строка
     * @return - успешно сохранили строку или нет.
     * <p>
     * Сложность - [O(n), где n - длина передаваемой строки]
     */
    public boolean add(String value) {
        Map<Character, Integer> letterCounter = getLetterCountMap(value);
        dictionary.putIfAbsent(letterCounter, new LinkedHashSet<>());
        Set<String> similarWords = dictionary.getOrDefault(letterCounter, Collections.emptySet());
        if (similarWords.contains(value)) {
            return false;
        }
        similarWords.add(value);
        size++;
        return true;
    }

    /**
     * Проверяем, хранится ли такая строка уже у нас
     *
     * @param value - передаваемая строка
     * @return - есть такая строка или нет в нашей структуре
     * <p>
     * Сложность - [O(n), где n - длина передаваемой строки]
     */
    public boolean contains(String value) {
        Map<Character, Integer> letterCounter = getLetterCountMap(value);
        Set<String> similarWords = dictionary.getOrDefault(letterCounter, Collections.emptySet());
        return similarWords.contains(value);
    }

    /**
     * Удаляем сохраненную строку если она есть
     *
     * @param value - какую строку мы хотим удалить
     * @return - true если удалили, false - если такой строки нет
     * <p>
     * Сложность - [O(n), где n - длина передаваемой строки]
     */
    public boolean remove(String value) {
        Map<Character, Integer> letterCounter = getLetterCountMap(value);
        Set<String> similarWords = dictionary.getOrDefault(letterCounter, Collections.emptySet());
        if (similarWords.remove(value)) {
            size--;
            if (similarWords.isEmpty()){
                dictionary.remove(letterCounter);
            }
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
     * Сложность - [O(n+m), где n - длина передаваемой строки, m - кол-во элементов в мапе]
     */
    public List<String> getSimilarWords(String value) {
        Map<Character, Integer> letterCounter = getLetterCountMap(value);
        Set<String> similarWords = dictionary.getOrDefault(letterCounter, Collections.emptySet());

        if (!similarWords.isEmpty()) {
            return new ArrayList<>(similarWords);
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

    private Map<Character, Integer> getLetterCountMap(String value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException();
        }

        Map<Character, Integer> letterCountMap = new HashMap<>();
        for (char c : value.toLowerCase().toCharArray()) {
            letterCountMap.merge(c, 1, Integer::sum);
        }
        return letterCountMap;
    }
}

