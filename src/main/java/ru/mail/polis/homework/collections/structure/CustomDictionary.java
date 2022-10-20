package ru.mail.polis.homework.collections.structure;

import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Задание оценивается в 4 тугрика.
 * Необходимо реализовать класс которы умеет хранить строки и возвращать
 * список строк состоящий из того же набора буков, что ему передали строку.
 * Напишите какая сложность операций у вас получилась для каждого метода.
 */
public class CustomDictionary {
    private final Map<Set<Character>, List<String>> dictionary = new HashMap<>();
    private int size;

    /**
     * Сохранить строку в структуру данных
     *
     * @param value - передаваемая строка
     * @return - успешно сохранили строку или нет.
     * <p>
     * Сложность - [O(1) амортизированная]
     */
    public boolean add(String value) {
        Set<Character> characters = validate(value);
        List<String> words = dictionary.getOrDefault(characters, new ArrayList<>());
        final int fixedSize = words.size();
        boolean isAdded = false;
        if (words.isEmpty()) {
            words.add(value);
            isAdded = true;
        }
        for (int i = 0; i < fixedSize; i++) {
            if (!words.get(i).equals(value)) {
                words.add(value);
                isAdded = true;
            }
        }
        dictionary.put(characters, words);
        if (isAdded) {
            size++;
        }
        return isAdded;
    }

    /**
     * Проверяем, хранится ли такая строка уже у нас
     *
     * @param value - передаваемая строка
     * @return - есть такая строка или нет в нашей структуре
     * <p>
     * Сложность - [O(n), где n - кол-во элементов в мапе]
     */
    public boolean contains(String value) {
        Set<Character> characters = validate(value);
        List<String> words = dictionary.getOrDefault(characters, new ArrayList<>());
        return words.contains(value);
    }

    /**
     * Удаляем сохраненную строку если она есть
     *
     * @param value - какую строку мы хотим удалить
     * @return - true если удалили, false - если такой строки нет
     * <p>
     * Сложность - [O(n), где n - кол-во элементов в мапе]
     */
    public boolean remove(String value) {
        Set<Character> characters = validate(value);
        List<String> words = dictionary.getOrDefault(characters, new ArrayList<>());
        final boolean isRemoved = words.remove(value);
        if (isRemoved) {
            size--;
        }
        dictionary.put(characters, words);
        return isRemoved;
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
     * Сложность - [O(n), где n - кол-во элементов в мапе]
     */
    public List<String> getSimilarWords(String value) {
        Set<Character> characters = validate(value);
        List<String> words = dictionary.getOrDefault(characters, new ArrayList<>());
        return words.stream()
                .filter(word -> word.length() == value.length())
                .collect(Collectors.toList());
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

    private Set<Character> validate(String value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException();
        }

        return value.toLowerCase()
                .chars()
                .mapToObj(e -> (char) e)
                .collect(Collectors.toSet());
    }
}

