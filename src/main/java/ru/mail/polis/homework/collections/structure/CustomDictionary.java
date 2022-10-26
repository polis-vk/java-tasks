package ru.mail.polis.homework.collections.structure;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * Задание оценивается в 4 тугрика.
 * Необходимо реализовать класс которы умеет хранить строки и возвращать
 * список строк состоящий из того же набора буков, что ему передали строку.
 * Напишите какая сложность операций у вас получилась для каждого метода.
 */
public class CustomDictionary {
    private final Map<Map<Character, Integer>, List<String>> data = new HashMap<>();
    private int size;

    /**
     * Сохранить строку в структуру данных
     *
     * @param value - передаваемая строка
     * @return - успешно сохранили строку или нет.
     * <p>
     * Сложность - [O(n), n = длина параметра value]
     */
    public boolean add(String value) {
        checkArgument(value);
        Map<Character, Integer> dictionaryForChars = convertToMapOfLetters(value);
        data.putIfAbsent(dictionaryForChars, new ArrayList<>());
        List<String> currentList = data.get(dictionaryForChars);
        if (currentList.isEmpty() || !currentList.contains(value)) {
            currentList.add(value);
        } else {
            return false;
        }
        size++;
        return true;
    }

    /**
     * Проверяем, хранится ли такая строка уже у нас
     *
     * @param value - передаваемая строка
     * @return - есть такая строка или нет в нашей структуре
     * <p>
     * Сложность - [O(n + m), n - длина параметра value, m - размер списка listWithThisValue]
     */
    public boolean contains(String value) {
        checkArgument(value);
        List<String> listWithThisValue = data.get(convertToMapOfLetters(value));
        return hasElements(listWithThisValue) && listWithThisValue.contains(value);
    }

    /**
     * Удаляем сохраненную строку если она есть
     *
     * @param value - какую строку мы хотим удалить
     * @return - true если удалили, false - если такой строки нет
     * <p>
     * Сложность - [O(n + m), n - длина параметра value, m - размер списка listWithThisValue]
     */
    public boolean remove(String value) {
        checkArgument(value);
        List<String> listWithThisValue = data.get(convertToMapOfLetters(value));
        if (hasElements(listWithThisValue) && listWithThisValue.removeIf(str -> (str.equals(value)))) {
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
     * Сложность - [O(n), n - длина параметра value]
     */
    public List<String> getSimilarWords(String value) {
        checkArgument(value);
        List<String> listWithThisValue = data.get(convertToMapOfLetters(value));
        if (hasElements(listWithThisValue)) {
            return listWithThisValue;
        }
        return Collections.emptyList();
    }

    /**
     * Колл-во хранимых строк.
     *
     * @return - Колл-во хранимых строк.
     * <p>
     * Сложность - [1]
     */
    public int size() {
        return size;
    }

    private static Map<Character, Integer> convertToMapOfLetters(String value) {
        Map<Character, Integer> letterCountMap = new HashMap<>();
        char[] charsFromValue = value.toLowerCase().toCharArray();
        for (char c : charsFromValue) {
            letterCountMap.merge(c, 1, Integer::sum);
        }
        return letterCountMap;
    }

    private static boolean hasElements(List<String> list) {
        return list != null && !list.isEmpty();
    }

    private static void checkArgument(String value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException();
        }
    }
}
