package ru.mail.polis.homework.collections.structure;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Задание оценивается в 4 тугрика.
 * Необходимо реализовать класс которы умеет хранить строки и возвращать
 * список строк состоящий из того же набора буков, что ему передали строку.
 * Напишите какая сложность операций у вас получилась для каждого метода.
 */
public class CustomDictionary {

    HashMap<String, char[]> pairs = new HashMap<>();

    /**
     * Сохранить строку в структуру данных
     *
     * @param value - передаваемая строка
     * @return - успешно сохранили строку или нет.
     * <p>
     * Сложность - [O(nlog(n)]
     */
    public boolean add(String value) {
        if (value == null || value.length() == 0) {
            throw new IllegalArgumentException();
        }
        if (pairs.containsKey(value)) {
            return false;
        }
        char[] ch = value.toLowerCase().toCharArray();
        Arrays.sort(ch);
        pairs.put(value, ch);
        return true;
    }

    /**
     * Проверяем, хранится ли такая строка уже у нас
     *
     * @param value - передаваемая строка
     * @return - есть такая строка или нет в нашей структуре
     * <p>
     * Сложность - [O(1), O(n) worst case]
     */
    public boolean contains(String value) {
        return pairs.containsKey(value);
    }

    /**
     * Удаляем сохраненную строку если она есть
     *
     * @param value - какую строку мы хотим удалить
     * @return - true если удалили, false - если такой строки нет
     * <p>
     * Сложность - [O(n) worst case]
     */
    public boolean remove(String value) {
        if (pairs.isEmpty()) {
            return false;
        }
        return pairs.remove(value) != null;
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
     * Сложность - [O(nlog(n)]
     */
    public List<String> getSimilarWords(String value) {
        char[] ch = value.toLowerCase().toCharArray();
        Arrays.sort(ch);

        List<String> result = new LinkedList<>();

        for (String n : pairs.keySet()) {
            if (Arrays.equals(pairs.get(n), ch)) {
                result.add(n);
            }
        }
        return result;
    }

    /**
     * Колл-во хранимых строк.
     *
     * @return - Колл-во хранимых строк.
     * <p>
     * Сложность - [Θ(1)]
     */
    public int size() {
        return pairs.size();
    }


}
