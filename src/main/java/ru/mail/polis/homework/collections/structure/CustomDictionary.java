package ru.mail.polis.homework.collections.structure;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Задание оценивается в 4 балла.
 * Необходимо реализовать класс которы умеет хранить строки и возвращать
 * список строк состоящий из того же набора буков, что ему передали строку.
 * Напишите какая сложность операций у вас получилась для каждого метода.
 */
public class CustomDictionary {

    private final HashMap<String, HashSet<String>> map = new HashMap<>();
    private int size = 0;

    /**
     * Сохранить строку в структуру данных
     * @param value - передаваемая строка
     * @return - успешно сохранили строку или нет.
     *
     * Сложность - [O(m * log(m)), m - длина value]
     */
    public boolean add(String value) {
        if (value == null || value.equals("")) {
            throw new IllegalArgumentException();
        }

        String key = Stream.of(value.toLowerCase().split("")).sorted()
                .collect(Collectors.joining());
        map.computeIfAbsent(key, k -> new HashSet<>());
        size++;
        return map.get(key).add(value);
    }

    /**
     * Проверяем, хранится ли такая строка уже у нас
     * @param value - передаваемая строка
     * @return - есть такая строка или нет в нашей структуре
     *
     * Сложность - [O(m * log(m)), m - длина value]
     */
    public boolean contains(String value) {
        String key = Stream.of(value.toLowerCase().split("")).sorted()
                .collect(Collectors.joining());
        if (map.get(key) == null) {
            return false;
        }
        return map.get(key).contains(value);
    }

    /**
     * Удаляем сохраненную строку если она есть
     * @param value - какую строку мы хотим удалить
     * @return - true если удалили, false - если такой строки нет
     *
     * Сложность - [O(m * log(m)), m - длина value]
     */
    public boolean remove(String value) {
        String key = Stream.of(value.toLowerCase().split("")).sorted()
                .collect(Collectors.joining());
        if (map.get(key) == null) {
            return false;
        }
        size--;
        return map.get(key).remove(value);
    }

    /**
     * Возвращает список из сохраненных ранее строк, которые состоят
     * из тех же букв что нам передали строку.
     * Примеры: сохраняем строки ["aaa", "aBa", "baa", "aaB"]
     *          При поиске по строке "AAb" нам должен вернуться следующий
     *          список: ["aBa","baa","aaB"]
     *
     * Как можно заметить - регистр строки не должен влиять на поиск, при этом
     * возвращаемые строки хранятся в том виде что нам передали изначально.
     *
     * @return - список слов которые состоят из тех же букв, что и передаваемая
     * строка.
     *
     * Сложность - [O(m * log(m)), m - длина value]
     */
    public List<String> getSimilarWords(String value) {
        String key = Stream.of(value.toLowerCase().split("")).sorted()
                .collect(Collectors.joining());
        return map.get(key) != null ? new ArrayList<>(map.get(key)) : Collections.emptyList();
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
}
