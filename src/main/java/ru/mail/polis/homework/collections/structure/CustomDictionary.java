package ru.mail.polis.homework.collections.structure;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Задание оценивается в 4 тугрика.
 * Необходимо реализовать класс которы умеет хранить строки и возвращать
 * список строк состоящий из того же набора буков, что ему передали строку.
 * Напишите какая сложность операций у вас получилась для каждого метода.
 */
public class CustomDictionary {

    private final Map<String, char[]> dictionary;

    private int size;

    public CustomDictionary() {
        dictionary = new HashMap<>();
        size = 0;
    }

    /**
     * Сохранить строку в структуру данных
     *
     * @param value - передаваемая строка
     * @return - успешно сохранили строку или нет.
     * <p>
     * Сложность - [O(n) - в худшем случае, O(1) - в лучшем случае; n - количество элементов коллекции]
     */
    public boolean add(String value) {
        checkStringForValid(value);

        char[] strCharArr = value.toLowerCase().toCharArray();
        Arrays.sort(strCharArr);

        if (dictionary.containsKey(value)) {
            return false;
        } else {
            dictionary.put(value, strCharArr);
            size++;
            return true;
        }
    }

    /**
     * Проверяем, хранится ли такая строка уже у нас
     *
     * @param value - передаваемая строка
     * @return - есть такая строка или нет в нашей структуре
     * <p>
     * Сложность - [O(n); n - количество элементов коллекции]
     */
    public boolean contains(String value) {
        checkStringForValid(value);
        return dictionary.containsKey(value);
    }

    /**
     * Удаляем сохраненную строку если она есть
     *
     * @param value - какую строку мы хотим удалить
     * @return - true если удалили, false - если такой строки нет
     * <p>
     * Сложность - [O(1)]
     */
    public boolean remove(String value) {
        checkStringForValid(value);

        if (!dictionary.containsKey(value)) {
            return false;
        }
        dictionary.remove(value);
        if (!dictionary.containsKey(value)) {
            size--;
            return true;
        } else {
            return false;
        }
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
     * Сложность - [O(n); n - количество элементов коллекции]
     */
    public List<String> getSimilarWords(String value) {
        List<String> returnList = new LinkedList<>();

        dictionary.forEach((k, v) -> {
            char[] strCharArr = value.toLowerCase().toCharArray();
            Arrays.sort(strCharArr);
            if (Arrays.equals(v, strCharArr)) {
                returnList.add(k);
            }
        });
        return returnList;
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

    private void checkStringForValid(String value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException();
        }
    }

}
