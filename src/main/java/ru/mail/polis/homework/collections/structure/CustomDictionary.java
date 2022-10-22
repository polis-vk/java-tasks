package ru.mail.polis.homework.collections.structure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

/**
 * Задание оценивается в 4 тугрика.
 * Необходимо реализовать класс которы умеет хранить строки и возвращать
 * список строк состоящий из того же набора буков, что ему передали строку.
 * Напишите какая сложность операций у вас получилась для каждого метода.
 */
public class CustomDictionary {
    private final HashMap<String, HashSet<String>> hashMap = new HashMap<>();
    private int size;

    /**
     * Сохранить строку в структуру данных
     *
     * @param value - передаваемая строка
     * @return - успешно сохранили строку или нет.
     * <p>
     * Сложность - [O(n log(n))], n = value.length()
     */
    public boolean add(String value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException();
        }
        String key = buildKey(value); //O(n log(n)), n = value.length()
        if (hashMap.containsKey(key)) { //O(1) in average case, and O(n = hashMap.size()) in worst case
            HashSet<String> valueSet = hashMap.get(key); //O(1)
            if (valueSet.contains(value)) { //O(1)
                return false;
            } else {
                valueSet.add(value);//O(1)
                size++;
                return true;
            }
        }
        HashSet<String> valueSet = new HashSet<>();
        valueSet.add(value); //O(1)
        hashMap.put(key, valueSet); //O(1)
        size++;
        return true;
    }

    //complexity O(n log(n)), n = value.length()
    private String buildKey(String value) {
        //toLowerCase O(n = value.length()), toCharArray O(n = value.length())
        char[] chars = value.toLowerCase(Locale.ROOT).toCharArray();
        Arrays.sort(chars); //O(n log(n)), n = value.length()
        return Arrays.toString(chars); //O(n = value.length())
    }

    /**
     * Проверяем, хранится ли такая строка уже у нас
     *
     * @param value - передаваемая строка
     * @return - есть такая строка или нет в нашей структуре
     * <p>
     * Сложность - [O(n log(n))], n = value.length()
     */
    public boolean contains(String value) {
        String key = buildKey(value);//O(n log(n)), n = value.length()
        //containsKey O(1) in average, O(n = hashMap.size()) in worst case
        //get O(1)
        //contains O(1)
        return hashMap.containsKey(key) && hashMap.get(key).contains(value);
    }

    /**
     * Удаляем сохраненную строку если она есть
     *
     * @param value - какую строку мы хотим удалить
     * @return - true если удалили, false - если такой строки нет
     * <p>
     * Сложность - [O(n log(n))], n = value.length()
     */
    public boolean remove(String value) {
        String key = buildKey(value); //O(n log(n))
        if (hashMap.containsKey(key)) { //O(1) in average, O(n = map.size()) in worst case
            HashSet<String> valueSet = hashMap.get(key); //O(1)
            if (!valueSet.contains(value)) { //O(1)
                return false;
            }
            valueSet.remove(value); //O(1)
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
     * Сложность - [O(n log(n))]
     */
    public List<String> getSimilarWords(String value) {
        String key = buildKey(value); //O(n log(n)), n = value.length()
        if (hashMap.containsKey(key)) { //O(1) in average, O(n = hashMap.size()) in worst case
            return new ArrayList<>(hashMap.get(key)); //O(1)
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

}
