package ru.mail.polis.homework.collections.structure;

import java.util.Collections;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Map;

/**
 * Задание оценивается в 4 тугрика.
 * Необходимо реализовать класс которы умеет хранить строки и возвращать
 * список строк состоящий из того же набора буков, что ему передали строку.
 * Напишите какая сложность операций у вас получилась для каждого метода.
 */
public class CustomDictionary {
    Map<String, Set<String>> dictionary = new HashMap<>();
    private int size;

    /**
     * Сохранить строку в структуру данных
     * @param value - передаваемая строка
     * @return - успешно сохранили строку или нет.
     *
     * n = value.length
     * Сложность - [O(n * log(n))]
     */
    public boolean add(String value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException();
        }
        String key = getKey(value);
        if (dictionary.computeIfAbsent(key, k -> new HashSet<>()).add(value)) {
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
     * n = value.length
     * Сложность - [O(n * log(n))]
     */
    public boolean contains(String value) {
        if (value == null || value.isEmpty()) {
            return false;
        }
        String key = getKey(value);
        if (dictionary.containsKey(key)) {
            return dictionary.get(key).contains(value);
        }
        return false;
    }

    /**
     * Удаляем сохраненную строку если она есть
     * @param value - какую строку мы хотим удалить
     * @return - true если удалили, false - если такой строки нет
     *
     * n = value.length
     * Сложность - [O(n * log(n))]
     */
    public boolean remove(String value) {
        if (value == null || !contains(value)) {
            return false;
        }
        String key = getKey(value);
        if (!dictionary.get(key).remove(value)) {
            return false;
        }
        if (dictionary.get(key).isEmpty()) {
            dictionary.remove(key);
        }
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
     * n = value.length
     * m - количество подходящих слов
     * Сложность - [m + O(n * log(n))]
     */
    public List<String> getSimilarWords(String value) {
        String key = getKey(value);
        if (dictionary.containsKey(key)) {
            return new ArrayList<>(dictionary.get(key));
        }
        return Collections.emptyList();
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
     * Получение ключа по значению.
     * @return - отсортированная строка символов value в нижнем регистре
     *
     * n = value.length
     * Сложность - [O(n * log(n))]
     */
    private String getKey(String value) {
        char[] charArray = value.toLowerCase().toCharArray();
        Arrays.sort(charArray);
        return Arrays.toString(charArray);
    }
}
