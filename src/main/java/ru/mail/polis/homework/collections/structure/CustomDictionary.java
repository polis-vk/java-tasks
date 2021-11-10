package ru.mail.polis.homework.collections.structure;

import java.util.HashMap;
import java.util.LinkedHashSet;
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

    private final Map<Map<Character, Integer>, Set<String>> values = new HashMap<>();
    private int size = 0;

    /**
     * Получить отображение каждого символа на его количество в строке.
     *
     * @param value передаваемая строка
     * @return отображение каждого символа на его количество в строке.
     * <p>
     * Сложность - [O(n) в среднем], где n - длина value.
     */

    private Map<Character, Integer> getFrequencyMap(String value) {
        if (value == null || value.length() == 0) {
            throw new IllegalArgumentException();
        }
        Map<Character, Integer> result = new HashMap<>();
        for (Character character : value.toCharArray()) {
            character = Character.toLowerCase(character);
            result.put(character, result.containsKey(character) ? result.get(character) + 1 : 1);
        }
        return result;
    }

    /**
     * Сохранить строку в структуру данных
     *
     * @param value - передаваемая строка
     * @return - успешно сохранили строку или нет.
     * <p>
     * Сложность - [O(n) в среднем], где n - длина value.
     */
    public boolean add(String value) {
        if (value == null || value.length() == 0) {
            throw new IllegalArgumentException();
        }
        Map<Character, Integer> frequencyMap = getFrequencyMap(value);
        if (values.containsKey(frequencyMap)) {
            if (values.get(frequencyMap).add(value)) {
                size++;
                return true;
            }
            return false;
        }
        Set<String> added = new LinkedHashSet<>();
        added.add(value);
        values.put(frequencyMap, added);
        size++;
        return true;
    }

    /**
     * Проверяем, хранится ли такая строка уже у нас
     *
     * @param value - передаваемая строка
     * @return - есть такая строка или нет в нашей структуре
     * <p>
     * Сложность - [O(n) в среднем], где n - длина value.
     */
    public boolean contains(String value) {
        Map<Character, Integer> frequencyMap = getFrequencyMap(value);
        if (values.containsKey(frequencyMap)) {
            return values.get(frequencyMap).contains(value);
        }
        return false;
    }

    /**
     * Удаляем сохраненную строку если она есть
     *
     * @param value - какую строку мы хотим удалить
     * @return - true если удалили, false - если такой строки нет
     * <p>
     * Сложность - [O(n) в среднем], где n - длина value.
     */
    public boolean remove(String value) {
        Map<Character, Integer> frequencyMap = getFrequencyMap(value);
        if (!values.containsKey(frequencyMap)) {
            return false;
        }
        if (values.get(frequencyMap).remove(value)) {
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
     * Сложность - [O(n*m)], где n - длина value, m - количество подходящих слов.
     */
    public List<String> getSimilarWords(String value) {
        Map<Character, Integer> frequencyMap = getFrequencyMap(value);
        if (values.containsKey(frequencyMap)) {
            return new LinkedList<>(values.get(frequencyMap));
        }
        return new LinkedList<String>();
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
