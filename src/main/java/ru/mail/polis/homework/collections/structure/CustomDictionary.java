package ru.mail.polis.homework.collections.structure;

import java.util.Collections;
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

    private final Map<Map<Character, Integer>, Set<String>> data = new HashMap<>();
    private int size = 0;

    /**
     * Сохранить строку в структуру данных
     *
     * @param value - передаваемая строка
     * @return - успешно сохранили строку или нет.
     * <p>
     * Сложность - [o(k)]
     */
    public boolean add(String value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException();
        }
        Map<Character, Integer> parse = getHashMap(value);
        Set<String> strings = data.get(parse);
        if (strings != null) {
            if (strings.add(value)) {
                size++;
                return true;
            }
        } else {
            size++;
            strings = new HashSet<>();
            strings.add(value);
            data.put(parse, strings);
            return true;
        }
        return false;
    }

    /**
     * Проверяем, хранится ли такая строка уже у нас
     *
     * @param value - передаваемая строка
     * @return - есть такая строка или нет в нашей структуре
     * <p>
     * Сложность - [o(k)], где k - длина строки
     */
    public boolean contains(String value) {
        Set<String> strings = data.get(getHashMap(value));
        if (strings != null) {
            return strings.contains(value);
        }
        return false;
    }

    /**
     * Удаляем сохраненную строку если она есть
     *
     * @param value - какую строку мы хотим удалить
     * @return - true если удалили, false - если такой строки нет
     * <p>
     * Сложность - [o(k)]
     */
    public boolean remove(String value) {
        Map<Character, Integer> key = getHashMap(value);
        Set<String> strings = data.get(key);
        if (strings != null) {
            if (strings.remove(value)) {
                if (strings.isEmpty()) {
                    data.remove(key);
                }
                size--;
                return true;
            }
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
     * Сложность - [o(k)]
     */
    public List<String> getSimilarWords(String value) {
        Set<String> strings = data.get(getHashMap(value));
        if (strings != null) {
            return new LinkedList<>(strings);
        }
        return Collections.emptyList();
    }

    /**
     * Колл-во хранимых строк.
     *
     * @return - Колл-во хранимых строк.
     * <p>
     * Сложность - [o(1)]
     */
    public int size() {
        return size;
    }

    private HashMap<Character, Integer> getHashMap(String value) {
        Map<Character, Integer> characters = new HashMap<>();
        for (int i = 0; i < value.length(); i++) {
            char character = Character.toUpperCase(value.charAt(i));
            characters.put(character, characters.getOrDefault(character, 0) + 1);
        }
        return (HashMap<Character, Integer>) characters;
    }

}
