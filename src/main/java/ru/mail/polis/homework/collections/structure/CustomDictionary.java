package ru.mail.polis.homework.collections.structure;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.ArrayList;

/**
 * Задание оценивается в 4 тугрика.
 * Необходимо реализовать класс которы умеет хранить строки и возвращать
 * список строк состоящий из того же набора буков, что ему передали строку.
 * Напишите какая сложность операций у вас получилась для каждого метода.
 */
public class CustomDictionary {

    private Map<Map<Character, Integer>, Set<String>> dictionary = new HashMap<>();

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
        Map<Character, Integer> hash = getHash(value);
        dictionary.putIfAbsent(hash, new LinkedHashSet<>());
        return dictionary.get(hash).add(value);
    }

    public Map<Character, Integer> getHash(String str) {
        Map<Character, Integer> symbols = new HashMap<>();
        for (char symbol : str.toLowerCase().toCharArray()) {
            symbols.merge(symbol, 1, (value, addedValue) -> value + addedValue);
        }
        return symbols;
    }

    /**
     * Проверяем, хранится ли такая строка уже у нас
     * @param value - передаваемая строка
     * @return - есть такая строка или нет в нашей структуре
     *
     * Сложность - [O(n)]
     */
    public boolean contains(String value) {
        Map<Character, Integer> hash = getHash(value);
        if (dictionary.get(hash) == null) {
            return false;
        }
        return dictionary.get(hash).contains(value);
    }

    /**
     * Удаляем сохраненную строку если она есть
     * @param value - какую строку мы хотим удалить
     * @return - true если удалили, false - если такой строки нет
     *
     * Сложность - [O(n)]
     */
    public boolean remove(String value) {
        Map<Character, Integer> hash = getHash(value);
        if (dictionary.get(hash) == null) {
            return false;
        }
        return dictionary.get(hash).remove(value);
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
        Map<Character, Integer> hash = getHash(value);
        if (dictionary.get(hash) == null) {
            return new ArrayList();
        }
        return new ArrayList(dictionary.get(hash));
    }

    /**
     * Колл-во хранимых строк.
     * @return - Колл-во хранимых строк.
     *
     * Сложность - [O(n)]
     */
    public int size() {
        int size = 0;
        for (Set<String> value: dictionary.values()) {
            size += value.size();
        }
        return size;
    }

}

