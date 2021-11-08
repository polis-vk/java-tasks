package ru.mail.polis.homework.collections.structure;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.TreeSet;

/**
 * Задание оценивается в 4 балла.
 * Необходимо реализовать класс которы умеет хранить строки и возвращать
 * список строк состоящий из того же набора буков, что ему передали строку.
 * Напишите какая сложность операций у вас получилась для каждого метода.
 */
public class CustomDictionary {

    private HashMap<String, HashSet<String>> data;
    private int size;

    public CustomDictionary() {
        this.data = new HashMap<String, HashSet<String>>();
        size = 0;
    }

    /**
     * Сохранить строку в структуру данных
     *
     * @param value - передаваемая строка
     * @return - успешно сохранили строку или нет.
     * <p>
     * Сложность - [ O(k*log k) ]
     */
    public boolean add(String value) {
        String key = makeKey(value);
        if (data.containsKey(key)) {
            if (data.get(key).contains(value)) {
                return false;
            }
            size++;
            data.get(key).add(value);
        } else {
            HashSet<String> set = new HashSet<>();
            set.add(value);
            data.put(key, set);
            size++;
        }
        return true;
    }


    private String makeKey(String value) {
        if (value == null || value.equals("")) {
            throw new IllegalArgumentException();
        }
        char[] temp = value.toLowerCase().toCharArray();
        Arrays.sort(temp);
        return String.valueOf(temp);
    }

    /**
     * Проверяем, хранится ли такая строка уже у нас
     *
     * @param value - передаваемая строка
     * @return - есть такая строка или нет в нашей структуре
     * <p>
     * Сложность - [ O(k*log k) ]
     */
    public boolean contains(String value) {
        return data.containsKey(value);
    }

    /**
     * Удаляем сохраненную строку если она есть
     *
     * @param value - какую строку мы хотим удалить
     * @return - true если удалили, false - если такой строки нет
     * <p>
     * Сложность - [ O(k*log k) ]
     */
    public boolean remove(String value) {
        if (!data.containsKey(makeKey(value))) {
            return false;
        }
        HashSet<String> set = data.get(makeKey(value));
        size--;
        return set.remove(value);
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
     * Сложность - [ O(k*log k) + O(n)]
     */
    public List<String> getSimilarWords(String value) {
        HashSet<String> set = data.get(makeKey(value));
        return (set == null) ? new LinkedList<>() : new LinkedList<>(set);
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
