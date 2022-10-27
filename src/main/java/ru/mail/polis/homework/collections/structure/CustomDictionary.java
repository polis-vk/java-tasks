package ru.mail.polis.homework.collections.structure;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Задание оценивается в 4 тугрика.
 * Необходимо реализовать класс, который умеет хранить строки и возвращать
 * список строк состоящий из того же набора букв, что хранится в переданной строке.
 * Напишите какая сложность операций у вас получилась для каждого метода.
 */
public class CustomDictionary {
    private final Map<Set<Character>, Set<String>> dictionary = new HashMap<>();
    private int size = 0;

    /**
     * Сохранить строку в структуру данных
     *
     * @param value - передаваемая строка
     * @return - успешно сохранили строку или нет.
     * <p>
     * Сложность - [О(n)] - n длина value
     */
    public boolean add(String value) {
        Set<Character> letters = getCharKey(value);
        Set<String> strings = new HashSet<>();
        strings.add(value);

        Set<String> words = dictionary.putIfAbsent(letters, strings);

        if (words == null) {
            size++;
            return true;
        } else {
            if (words.add(value)) {
                size++;
                return true;
            }
        }
        return false;
    }

    /**
     * Проверяем, хранится ли такая строка уже у нас
     *
     * @param value - передаваемая строка
     * @return - есть такая строка или нет в нашей структуре
     * <p>
     * Сложность - [О(n)] - n длина value
     */
    public boolean contains(String value) {
        Set<Character> letters = getCharKey(value);
        Set<String> strings = dictionary.get(letters);
        if (strings == null) {
            return false;
        } else {
            return strings.contains(value);
        }
    }

    /**
     * Удаляем сохраненную строку если она есть
     *
     * @param value - какую строку мы хотим удалить
     * @return - true если удалили, false - если такой строки нет
     * <p>
     * Сложность - [О(n)] - n длина value
     */
    public boolean remove(String value) {
        Set<Character> letters = getCharKey(value);
        Set<String> strings = dictionary.get(letters);

        if (strings == null) {
            return false;
        } else {
            if (strings.remove(value)) {
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
     * Сложность - [О(n)] - n длина value
     */
    public List<String> getSimilarWords(String value) {
        Set<Character> letters = getCharKey(value);

        Set<String> strings = dictionary.getOrDefault(letters, new HashSet<>());
        return strings.stream().
                filter(word -> word.length() == value.length())
                .collect(Collectors.toList());
    }

    /**
     * Колл-во хранимых строк.
     *
     * @return - Колл-во хранимых строк.
     * <p>
     * Сложность - [O(1)] - значение хранится в поле класса - доступ за константу
     */
    public int size() {
        return size;
    }

    private Set<Character> getCharKey(String value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException();
        }

        Set<Character> letters = new HashSet<>();
        for (Character c : value.toCharArray()) {
            letters.add(Character.toLowerCase(c));
        }

        return letters;
    }

}
