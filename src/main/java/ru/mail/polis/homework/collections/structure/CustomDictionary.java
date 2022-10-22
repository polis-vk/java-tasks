package ru.mail.polis.homework.collections.structure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

/**
 * Задание оценивается в 4 тугрика.
 * Необходимо реализовать класс которы умеет хранить строки и возвращать
 * список строк состоящий из того же набора буков, что ему передали строку.
 * Напишите какая сложность операций у вас получилась для каждого метода.
 */
public class CustomDictionary {

    HashMap<HashMap<Character, Integer>, HashSet<String>> map = new HashMap<>();

    /**
     * Сохранить строку в структуру данных
     *
     * @param value - передаваемая строка
     * @return - успешно сохранили строку или нет.
     * <p>
     * Сложность - []
     */
    public boolean add(String value) {
        if (value == null || value.equals("")) {
            throw new IllegalArgumentException();
        }
        HashMap<Character, Integer> key = countChars(value);

        if (!map.containsKey(key)) {
            map.put(key, new HashSet<>());
        }
        return map.get(key).add(value);
    }


    /**
     * Проверяем, хранится ли такая строка уже у нас
     *
     * @param value - передаваемая строка
     * @return - есть такая строка или нет в нашей структуре
     * <p>
     * Сложность - []
     */
    public boolean contains(String value) {
        HashMap<Character, Integer> key = countChars(value);
        if (!map.containsKey(key)) {
            return false;
        }
        return map.get(key).contains(value);
    }

    /**
     * Удаляем сохраненную строку если она есть
     *
     * @param value - какую строку мы хотим удалить
     * @return - true если удалили, false - если такой строки нет
     * <p>
     * Сложность - []
     */
    public boolean remove(String value) {
        HashMap<Character, Integer> key = countChars(value);
        if (!map.containsKey(key)) {
            return false;
        }
        HashSet<String> listOfSimilar = map.get(key);
        return listOfSimilar.remove(value);
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
     * Сложность - []
     */
    public List<String> getSimilarWords(String value) {
        HashMap<Character, Integer> key = countChars(value);
        if (!map.containsKey(key)) {
            return new LinkedList<>();
        }
        return new ArrayList<>(map.get(key));
    }

    /**
     * Колл-во хранимых строк.
     *
     * @return - Колл-во хранимых строк.
     * <p>
     * Сложность - []
     */
    public int size() {
        int ans = 0;
        for (HashSet<String> value : map.values()) {
            ans += value.size();
        }
        return ans;
    }

    private HashMap<Character, Integer> countChars(String value) {
        HashMap<Character, Integer> key = new HashMap<>();
        for (int i = 0; i < value.length(); i++) {
            char c = Character.toLowerCase(value.charAt(i));
            key.put(c, key.getOrDefault(c, 0) + 1);
        }
        return key;
    }
}
