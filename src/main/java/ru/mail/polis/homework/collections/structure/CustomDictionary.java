package ru.mail.polis.homework.collections.structure;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
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
    private final Map<String, Set<String>> dictionary = new HashMap<>();
    private int size;

    /**
     * Сохранить строку в структуру данных
     *
     * @param value - передаваемая строка
     * @return - успешно сохранили строку или нет.
     * <p>
     * Сложность - O(k * log(k)), k - длинна строки value
     */
    public boolean add(String value) {
        if (value == null || value.equals("")) {
            throw new IllegalArgumentException();
        }
        size++;
        String key = getLowerSortedString(value);
        dictionary.computeIfAbsent(key, x -> new HashSet<>());
        return dictionary.get(key).add(value);
    }

    /**
     * Проверяем, хранится ли такая строка уже у нас
     *
     * @param value - передаваемая строка
     * @return - есть такая строка или нет в нашей структуре
     * <p>
     * Сложность - O(k * log(k)), k - длинна строки value
     */
    public boolean contains(String value) {
        String key = getLowerSortedString(value);
        return dictionary.get(key) != null && dictionary.get(key).contains(value);
    }

    /**
     * Удаляем сохраненную строку если она есть
     *
     * @param value - какую строку мы хотим удалить
     * @return - true если удалили, false - если такой строки нет
     * <p>
     * Сложность - O(k * log(k)), k - длинна строки value
     */
    public boolean remove(String value) {
        String key = getLowerSortedString(value);
        if (dictionary.get(key) == null) {
            return false;
        }
        final boolean removeFlag = dictionary.get(key).remove(value);
        if (removeFlag) {
            size--;
        }
        if (dictionary.get(key).size() == 0) {
            dictionary.remove(key);
        }
        return removeFlag;
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
     * <p>
     * Как можно заметить - регистр строки не должен влиять на поиск, при этом
     * возвращаемые строки хранятся в том виде что нам передали изначально.
     *
     * @return - список слов которые состоят из тех же букв, что и передаваемая
     * строка.
     * <p>
     * Сложность - O(k * log(k)), k - длина строки value
     */
    public List<String> getSimilarWords(String value) {
        String key = getLowerSortedString(value);
        if (!dictionary.containsKey(key)) {
            return Collections.emptyList();
        }
        return new LinkedList<>(dictionary.get(key));
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
     * @param value - строка для изменения
     * @return - отсортированный массив символов состоящий из символов нижнего регистра строки value
     * Сложность - O(k * log(k)), k - длинна строки value
     */
    private String getLowerSortedString(String value) {
        char[] charValue = value.toLowerCase().toCharArray();
        Arrays.sort(charValue);
        return String.valueOf(charValue);
    }
}
