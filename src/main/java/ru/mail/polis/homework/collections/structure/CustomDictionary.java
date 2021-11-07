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
 * Задание оценивается в 4 балла.
 * Необходимо реализовать класс которы умеет хранить строки и возвращать
 * список строк состоящий из того же набора буков, что ему передали строку.
 * Напишите какая сложность операций у вас получилась для каждого метода.
 */
public class CustomDictionary {

    private final Map<String, Set<String>> data = new HashMap<>();
    private int size;

    /**
     * Сохранить строку в структуру данных
     *
     * @param value - передаваемая строка
     * @return - успешно сохранили строку или нет.
     * <p>
     * Сложность - O(m * log(m)) в среднем случае, O(m^2) в худшем, где m - длинна строки
     */
    public boolean add(String value) {
        if (value == null || value.equals("")) {
            throw new IllegalArgumentException();
        }
        String splitted = splitByLetter(value);
        data.putIfAbsent(splitted, new HashSet<>());
        if (data.get(splitted).add(value)) {
            size++;
            return true;
        }
        return false;
    }

    /**
     * Проверяем, хранится ли такая строка уже у нас
     *
     * @param value - передаваемая строка
     * @return - есть такая строка или нет в нашей структуре
     * <p>
     * Сложность - O(m * log(m)) в среднем случае, O(m^2) в худшем, где m - длинна строки
     */
    public boolean contains(String value) {
        if (value == null || value.equals("")) {
            return false;
        }
        String splitted = splitByLetter(value);
        if (!data.containsKey(splitted)) {
            return false;
        }
        return data.get(splitted).contains(value);
    }

    /**
     * Удаляем сохраненную строку если она есть
     *
     * @param value - какую строку мы хотим удалить
     * @return - true если удалили, false - если такой строки нет
     * <p>
     * Сложность - O(m * log(m)) в среднем случае, O(m^2) в худшем, где m - длинна строки
     */
    public boolean remove(String value) {
        if (value == null) {
            return false;
        }
        String splitted = splitByLetter(value);
        if (!data.containsKey(splitted)) {
            return false;
        }
        if (data.get(splitted).remove(value)) {
            size--;
            if (data.get(splitted).isEmpty()) {
                data.remove(splitted);
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
     * Сложность - O(k + O(m * log(m))) в среднем, где k - количество слов подходящий под даный шаблон, m - длинна строки
     */
    public List<String> getSimilarWords(String value) {
        if (value == null) {
            throw new NullPointerException();
        }
        String key = splitByLetter(value);
        if (data.containsKey(key)) {
            return new ArrayList<>(data.get(key));
        }
        return Collections.emptyList();
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
     * Вспомогательный метод
     * <p>
     * Used in: add(), getSimilarWords(), remove(), contains()
     *
     * @param value - разбиваемая на символы строка
     * @return String упорядоченных букв из которых состоит слово
     * <p>
     * Сложность - в среднем O(m * log(m)), O(m^2) в худшем, где m - длинна строки
     */
    private String splitByLetter(String value) {
        char[] loweredCased = value.toLowerCase().toCharArray();
        Arrays.sort(loweredCased);
        return new String(loweredCased);
    }

}
