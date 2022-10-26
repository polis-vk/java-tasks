package ru.mail.polis.homework.collections.structure;

import java.util.HashMap;
import java.util.Locale;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

/**
 * Задание оценивается в 4 тугрика.
 * Необходимо реализовать класс который умеет хранить строки и возвращать
 * список строк состоящий из того же набора букв, что ему передали строку.
 * Напишите какая сложность операций у вас получилась для каждого метода.
 */
public class CustomDictionary {

    private int size;
    // Набор букв -> набор слов из этого набора букв
    private final Map<Map<Character, Integer>, Set<String>> dictionary = new HashMap<>();
    // Набор слов
    private final Set<String> strings = new HashSet<>();

    // Сложность - O(n), где n - длина value
    private HashMap<Character, Integer> reduceToLetters(String value) {
        HashMap<Character, Integer> answer = new HashMap<>();
        for (int i = 0; i < value.length(); i++) {
            answer.put(value.charAt(i), answer.getOrDefault(value.charAt(i), 0) + 1);
        }
        return answer;
    }

    /**
     * Сохранить строку в структуру данных
     * @param value - передаваемая строка
     * @return - успешно сохранили строку или нет.
     *
     * Сложность - O(n), где n - длина value
     */
    public boolean add(String value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException();
        }
        if (strings.add(value)) {
            HashMap <Character, Integer> letters = reduceToLetters(value.toLowerCase(Locale.ROOT));
            HashSet<String> requiredEntry = (HashSet<String>) dictionary.getOrDefault(letters, new HashSet<>());
            requiredEntry.add(value);
            dictionary.put(letters, requiredEntry);
            size++;
            return true;
        }
        return false;
    }

    /**
     * Проверяем, хранится ли такая строка уже у нас
     * @param value - передаваемая строка
     * @return - есть такая строка или нет в нашей структуре
     *
     * Сложность - O(1)
     */
    public boolean contains(String value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException();
        }
        return strings.contains(value);
    }

    /**
     * Удаляем сохраненную строку если она есть
     * @param value - какую строку мы хотим удалить
     * @return - true если удалили, false - если такой строки нет
     *
     * Сложность - O(n), где n - длина value
     */
    public boolean remove(String value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException();
        }
        if (strings.remove(value)) {
            HashMap <Character, Integer> letters = reduceToLetters(value.toLowerCase(Locale.ROOT));
            dictionary.get(letters).remove(value);
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
     * Сложность - O(n), где n - длина value
     */
    public List<String> getSimilarWords(String value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException();
        }
        HashMap <Character, Integer> letters = reduceToLetters(value.toLowerCase(Locale.ROOT));
        return new ArrayList<>(dictionary.getOrDefault(letters, new HashSet<>()));
    }

    /**
     * Колл-во хранимых строк.
     * @return - Колл-во хранимых строк.
     *
     * Сложность - O(1)
     */
    public int size() {
        return size;
    }


}
