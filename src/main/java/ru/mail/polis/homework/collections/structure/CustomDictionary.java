package ru.mail.polis.homework.collections.structure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Задание оценивается в 4 тугрика.
 * Необходимо реализовать класс которы умеет хранить строки и возвращать
 * список строк состоящий из того же набора буков, что ему передали строку.
 * Напишите какая сложность операций у вас получилась для каждого метода.
 */
public class CustomDictionary {

    private final Map<String, HashMap<Character, Integer>> dictionary = new HashMap<>();

    /**
     * Сохранить строку в структуру данных
     * @param value - передаваемая строка
     * @return - успешно сохранили строку или нет.
     * Сложность - [O(n), где n - длина value.]
     */
    public boolean add(String value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException();
        }
        if (contains(value)) {
            return false;
        }
        dictionary.put(value, stringToLetterMap(value));
        return true;
    }

    /**
     * Проверяем, хранится ли такая строка уже у нас
     * @param value - передаваемая строка
     * @return - есть такая строка или нет в нашей структуре
     * Сложность - [O(n), где n - количество элементов в мапе. Но O(1) в среднем.]
     */
    public boolean contains(String value) {
        return dictionary.containsKey(value);
    }

    /**
     * Удаляем сохраненную строку если она есть
     * @param value - какую строку мы хотим удалить
     * @return - true если удалили, false - если такой строки нет
     * Сложность - [O(n), где n - количество элементов в мапе. Но O(1) в среднем.]
     */
    public boolean remove(String value) {
        if (!contains(value)) {
            return false;
        }
        dictionary.remove(value);
        return true;
    }

    /**
     * Возвращает список из сохраненных ранее строк, которые состоят
     * из того же набора букв что нам передали строку.
     * Примеры:
     * сохраняем строки ["aaa", "aBa", "baa", "aaB"]
     * При поиске по строке "AAb" нам должен вернуться следующий
     * список: ["aBa","baa","aaB"]
     * сохраняем строки ["aaa", "aAa", "a"]
     * поиск "aaaa"
     * результат: []
     * Как можно заметить - регистр строки не должен влиять на поиск, при этом
     * возвращаемые строки хранятся в том виде что нам передали изначально.
     *
     * @return - список слов которые состоят из тех же букв, что и передаваемая
     * строка.
     * Сложность - [O(n + m), где n - длина слова, m - число схожих слов.]
     */
    public List<String> getSimilarWords(String value) {
        List<String> res = new ArrayList<>();
        HashMap<Character, Integer> stringLetterMap = stringToLetterMap(value);
        for (Map.Entry<String, HashMap<Character, Integer>> entry : dictionary.entrySet()) {
            HashMap<Character, Integer> dictionaryLetterMap = entry.getValue();
            boolean isSimilar = true;
            for (Map.Entry<Character, Integer> chMap : stringLetterMap.entrySet()) {
                char ch = chMap.getKey();
                int amount = chMap.getValue();
                if (!dictionaryLetterMap.containsKey(ch) || amount > dictionaryLetterMap.get(ch)) {
                    isSimilar = false;
                    break;
                }
            }
            if (isSimilar) {
                res.add(entry.getKey());
            }
        }
        return res;
    }

    /**
     * Колл-во хранимых строк.
     * @return - Колл-во хранимых строк.
     * Сложность - [O(1)]
     */
    public int size() {
        return dictionary.size();
    }

    /**
     * Возвращает строку в словарь символов с их количеством.
     *
     * @return - Словарь букв строки в качестве ключей, их количеством в качестве значения.
     * <p>
     * Сложность - [O(n), где n - длина value]
     */
    private static HashMap<Character, Integer> stringToLetterMap(String value) {
        HashMap<Character, Integer> letterMap = new HashMap<>();
        String lowerString = value.toLowerCase();
        for (int i = 0; i < lowerString.length(); i++) {
            char ch = lowerString.charAt(i);
            letterMap.put(ch, letterMap.getOrDefault(ch, 0) + 1);
        }
        return letterMap;
    }
}
