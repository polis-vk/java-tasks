package ru.mail.polis.homework.collections.structure;

import java.util.*;

/**
 * Задание оценивается в 4 балла.
 * Необходимо реализовать класс которы умеет хранить строки и возвращать
 * список строк состоящий из того же набора буков, что ему передали строку.
 * Напишите какая сложность операций у вас получилась для каждого метода.
 */
public class CustomDictionary {
    private final HashMap<Integer, HashSet<String>> dictionary = new HashMap<>();
    private int size;

    /**
     * Проверяем, хранится ли такая строка уже у нас
     *
     * @param value - передаваемая строка
     * @return - есть такая строка или нет в нашей структуре
     * <p>
     * Сложность - [O(m), m - количество букв в переданном слове]
     */
    public boolean contains(String value) {
        if (value == null) {
            return false;
        }

        int key = getKey(value);
        if (dictionary.containsKey(key)) {
            return dictionary.get(key).contains(value);
        }

        return false;
    }

    /**
     * Удаляем сохраненную строку если она есть
     *
     * @param value - какую строку мы хотим удалить
     * @return - true если удалили, false - если такой строки нет
     * <p>
     * Сложность - [O(m)]
     */
    public boolean remove(String value) {
        if (value == null) {
            return false;
        }

        if (!contains(value)) {
            return false;
        }

        int key = getKey(value);
        dictionary.get(key).remove(value);
        if(dictionary.get(key).isEmpty()) {
            dictionary.remove(key);
        }

        size--;
        return true;
    }

    /**
     * Сохранить строку в структуру данных
     *
     * @param value - передаваемая строка
     * @return - успешно сохранили строку или нет.
     * <p>
     * Сложность - [O(m)]
     */
    public boolean add(String value) {
        if (value == null || value.equals("")) {
            throw new IllegalArgumentException();
        }

        int key = getKey(value);
        if (dictionary.containsKey(key)) {
            if (!dictionary.get(key).contains(value)) {
                dictionary.get(key).add(value);
            } else {
                return false;
            }
        } else {
            HashSet<String> setForNewKey = new HashSet<>();
            setForNewKey.add(value);
            dictionary.put(key, setForNewKey);
        }
        size++;

        return true;
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
     * Сложность - [O(k), k - количество коллизий. Поскольку будет затрачено время при переносе слов из Set в List]
     */
    public List<String> getSimilarWords(String value) {
        if (value == null) {
            return Collections.emptyList();
        }

        HashSet<String> words = dictionary.get(getKey(value));
        if(words != null) {
            return new LinkedList<>(words);
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

    //Сложность - [O(m), m - количество букв]
    private static int getKey(String value) {
        char[] key = (value.toLowerCase()).toCharArray();
        int res = 0;
        for(char el : key) {
            res += el;
        }
        return res * key.length;
    }
}
