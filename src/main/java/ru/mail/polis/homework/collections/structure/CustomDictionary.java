package ru.mail.polis.homework.collections.structure;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * Задание оценивается в 4 балла.
 * Необходимо реализовать класс которы умеет хранить строки и возвращать
 * список строк состоящий из того же набора буков, что ему передали строку.
 * Напишите какая сложность операций у вас получилась для каждого метода.
 */
public class CustomDictionary {

    private final Map<String, HashSet<String>> dictionary;
    private int size;

    public CustomDictionary() {
        this.dictionary = new HashMap<>();
        this.size = 0;
    }

    /**
     * Сохранить строку в структуру данных
     *
     * @param value - передаваемая строка
     * @return - успешно сохранили строку или нет.
     * <p>
     * Сложность - O(k*log(k)), где k - количество букв в строке
     */
    public boolean add(String value) {
        if (value == null || value.equals("")) {
            throw new IllegalArgumentException();
        }
        String key = sortLettersInValue(value);
        dictionary.putIfAbsent(key, new HashSet<>());
        size++;
        return dictionary.get(key).add(value);
    }

    /**
     * Проверяем, хранится ли такая строка уже у нас
     *
     * @param value - передаваемая строка
     * @return - есть такая строка или нет в нашей структуре
     * <p>
     * Сложность - O(k*log(k)), где k - количество букв в строке
     */
    public boolean contains(String value) {
        if (value == null || value.equals("")) {
            throw new IllegalArgumentException();
        }
        String key = sortLettersInValue(value);
        if (!dictionary.containsKey(key)) {
            return false;
        }
        return dictionary.get(key).contains(value);
    }

    /**
     * Удаляем сохраненную строку если она есть
     *
     * @param value - какую строку мы хотим удалить
     * @return - true если удалили, false - если такой строки нет
     * <p>
     * Сложность - O(k*log(k)), где k - количество букв в строке
     */
    public boolean remove(String value) {
        String key = sortLettersInValue(value);
        if (!dictionary.containsKey(key)) {
            return false;
        }
        size--;
        return dictionary.get(key).remove(value);
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
     * <p>
     * Сложность - O(k*log(k)), где k - количество букв в строке
     */
    public List<String> getSimilarWords(String value) {
        String key = sortLettersInValue(value);
        if (dictionary.containsKey(key)) {
            return new ArrayList<>(dictionary.get(key));
        } else {
            return Collections.emptyList();
        }
    }

    /**
     * Колл-во хранимых строк.
     *
     * @return - Колл-во хранимых строк.
     * <p>
     * Сложность - O(1)
     */
    public int size() {
        return size;
    }

    /**
     * Ключ для dictionary
     *
     * @return - строка отсортированных букв исходной строки
     *
     * Сложность - O(k*log(k)), где k - количество букв в строке
     */
    private String sortLettersInValue(String value) {
        char[] arrayOfLettersInUpperCase = value.toUpperCase().toCharArray();
        Arrays.sort(arrayOfLettersInUpperCase);
        StringBuilder stringBuilder = new StringBuilder();
        for (Character letter : arrayOfLettersInUpperCase) {
            stringBuilder.append(letter);
        }
        return stringBuilder.toString();
    }
}
