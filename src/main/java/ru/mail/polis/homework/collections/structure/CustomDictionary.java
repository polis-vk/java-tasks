package ru.mail.polis.homework.collections.structure;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Задание оценивается в 4 тугрика.
 * Необходимо реализовать класс которы умеет хранить строки и возвращать
 * список строк состоящий из того же набора буков, что ему передали строку.
 * Напишите какая сложность операций у вас получилась для каждого метода.
 */
public class CustomDictionary {
    private final Map<Map<Character, Integer>, LinkedHashSet<String>> map = new HashMap<>();

    /**
     * Сохранить строку в структуру данных
     *
     * @param value - передаваемая строка
     * @return - успешно сохранили строку или нет.
     * <p>
     * Сложность - []
     */
    public boolean add(String value) {
        checkString(value);
        Map<Character, Integer> key = getKey(value);
        map.putIfAbsent(key, new LinkedHashSet<>());
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
        Map<Character, Integer> key = getKey(value);
        if (map.get(key) == null) {
            return false;
        }
        LinkedHashSet<String> set = map.get(key);
        return set.contains(value);
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
        Map<Character, Integer> key = getKey(value);
        if (map.get(key) == null) {
            return false;
        }
        LinkedHashSet<String> set = map.get(key);
        return set.removeIf(value::equals);
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
        Map<Character, Integer> key = getKey(value);
        if (map.get(key) == null) {
            return Collections.emptyList();
        }
        Set<String> set = map.get(key);
        return new ArrayList<>(set);
    }

    /**
     * Колл-во хранимых строк.
     *
     * @return - Колл-во хранимых строк.
     * <p>
     * Сложность - []
     */
    public int size() {
        AtomicInteger count = new AtomicInteger();
        map.forEach((k, v) -> count.addAndGet(v.size()));
        return count.get();
    }

    private void checkString(String str) {
        if (str == null || str.isEmpty()) {
            throw new IllegalArgumentException();
        }
    }

    private Map<Character, Integer> getKey(String value) {
        Map<Character, Integer> letters = new HashMap<>();
        for (char c : value.toLowerCase().toCharArray()) {
            letters.merge(c, 1, Integer::sum);
        }
        return letters;
    }

}
