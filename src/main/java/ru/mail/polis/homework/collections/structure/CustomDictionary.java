package ru.mail.polis.homework.collections.structure;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Задание оценивается в 4 тугрика.
 * Необходимо реализовать класс которы умеет хранить строки и возвращать
 * список строк состоящий из того же набора буков, что ему передали строку.
 * Напишите какая сложность операций у вас получилась для каждого метода.
 */
public class CustomDictionary {

    private final Map<Map<Character, Integer>, Set<String>> values = new HashMap<>();
    private int size = 0;

    /**
     * Сохранить строку в структуру данных
     * @param value - передаваемая строка
     * @return - успешно сохранили строку или нет.
     *
     * Сложность - [O(n)], где n - длина value.
     */
    public boolean add(String value) {
        if (value == null || value.length() == 0) {
            throw new IllegalArgumentException();
        }

        Map<Character, Integer> letterCountMap = getLetterCountMap(value);
        Set<String> set = values.get(letterCountMap);
        if (set != null) {
            if (set.add(value)) {
                size++;
                return true;
            }
            return false;
        }

        Set<String> words = new LinkedHashSet<>();
        words.add(value);
        values.put(letterCountMap, words);
        size++;
        return true;
    }

    /**
     * Проверяем, хранится ли такая строка уже у нас
     * @param value - передаваемая строка
     * @return - есть такая строка или нет в нашей структуре
     *
     * Сложность - [O(n)], где n - длина value.
     */
    public boolean contains(String value) {
        Map<Character, Integer> letterCountMap = getLetterCountMap(value);

        Set<String> set = values.get(letterCountMap);
        if (set != null) {
            return set.contains(value);
        }
        return false;
    }

    /**
     * Удаляем сохраненную строку если она есть
     * @param value - какую строку мы хотим удалить
     * @return - true если удалили, false - если такой строки нет
     *
     * Сложность - [O(n)], где n - длина value.
     */
    public boolean remove(String value) {
        Map<Character, Integer> letterCountMap = getLetterCountMap(value);

        if (!values.containsKey(letterCountMap)) {
            return false;
        }
        if (values.get(letterCountMap).remove(value)) {
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
     *
     * сохраняем строки ["aaa", "aAa", "a"]
     * поиск "aaaa"
     * результат: []
     * Как можно заметить - регистр строки не должен влиять на поиск, при этом
     * возвращаемые строки хранятся в том виде что нам передали изначально.
     *
     * @return - список слов которые состоят из тех же букв, что и передаваемая
     * строка.
     *
     * Сложность - [O(n + m)], где n - длина value, m - количество подходящих слов.
     * Думаю, что это стоит пояснить: подходящие слова надо занести в список-результат.
     * При создании LinkedList'а каждое найденное слово будет в него заноситься, соответственно,
     * к сложности еще добавляется m (число подходящих слов).
     */
    public List<String> getSimilarWords(String value) {
        Map<Character, Integer> letterCountMap = getLetterCountMap(value);

        if (values.containsKey(letterCountMap)) {
            return new LinkedList<>(values.get(letterCountMap));
        }
        return Collections.emptyList();
    }

    /**
     * Колл-во хранимых строк.
     * @return - Колл-во хранимых строк.
     *
     * Сложность - [O(1)]
     */
    public int size() {
        return size;
    }

    /**
     * Получаем отображение каждого символа строки, на его количество.
     * При этом регистр символа не учитывается.
     *
     * @param value строка, в которой хотим подсчитать количество символов.
     * @return мапа, отображающая символ на его количество в строке.
     *
     * Сложность - [O(n)], где n - длина value.
     */
    private Map<Character, Integer> getLetterCountMap(String value) {
        if (value == null || value.length() == 0) {
            throw new IllegalArgumentException();
        }

        Map<Character, Integer> result = new HashMap<>();
        for (char ch : value.toCharArray()) {
            ch = Character.toLowerCase(ch);
            result.merge(ch, 1, Integer::sum);
        }

        return result;
    }
}
