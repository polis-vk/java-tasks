package ru.mail.polis.homework.collections.structure;

import java.util.Arrays;
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

    private final Map<String, Set<String>> data = new HashMap<>();
    private int size;

    /**
     * Сохранить строку в структуру данных
     *
     * @param value - передаваемая строка
     * @return - успешно сохранили строку или нет.
     * <p>
     * Сложность - [ O(k*log k) ] k - длина переданной строки
     */
    public boolean add(String value) {
        validateString(value);
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

    /**
     * Проверяем, хранится ли такая строка уже у нас
     *
     * @param value - передаваемая строка
     * @return - есть такая строка или нет в нашей структуре
     * <p>
     * Сложность - [ O(k*log k) ] k - длина переданной строки
     */
    public boolean contains(String value) {
        validateString(value);
        if (data.containsKey(makeKey(value))) {
            return data.get(makeKey(value)).contains(value);
        }
        return false;
    }

    /**
     * Удаляем сохраненную строку если она есть
     *
     * @param value - какую строку мы хотим удалить
     * @return - true если удалили, false - если такой строки нет
     * <p>
     * Сложность - [ O(k*log k) ] k - длина переданной строки
     */
    public boolean remove(String value) {
        validateString(value);
        if (!data.containsKey(makeKey(value))) {
            return false;
        }
        Set<String> set = data.get(makeKey(value));
        if (set.remove(value)) {
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
     * Сложность - [ O(k*log k) + O(n)] k - длина переданной строки, n - количество элементов
     */
    public List<String> getSimilarWords(String value) {
        validateString(value);
        Set<String> set = data.get(makeKey(value));
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

    private void validateString(String value) throws IllegalArgumentException {
        if (value == null || value.equals("")) {
            throw new IllegalArgumentException();
        }
    }

    private String makeKey(String value) {
        char[] temp = value.toLowerCase().toCharArray();
        Arrays.sort(temp);
        return String.valueOf(temp);
    }
}
