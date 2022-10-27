package ru.mail.polis.homework.collections.structure;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Задание оценивается в 4 тугрика.
 * Необходимо реализовать класс которы умеет хранить строки и возвращать
 * список строк состоящий из того же набора буков, что ему передали строку.
 * Напишите какая сложность операций у вас получилась для каждого метода.
 */
public class CustomDictionary {
    private final Map<List<Integer>, List<String>> map = new HashMap<>();
    int size;

    /**
     * Сохранить строку в структуру данных
     * @param value - передаваемая строка
     * @return - успешно сохранили строку или нет.
     *
     * n - число литер в value; m - кол-во строк с таким же набором букв, как и value
     * Сложность - [O(n) - лучшее; O(n * m) - худшее]
     */
    public boolean add(String value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException();
        }
        List<Integer> valueList = getIntegersFromString(value);
        if (map.containsKey(valueList)) {
            if (map.get(valueList).contains(value)) {
                return false;
            }
            map.get(valueList).add(value);
        } else {
            ArrayList<String> list = new ArrayList<>(Collections.singletonList(value));
            map.put(valueList, list);
        }
        size++;
        return true;
    }

    /**
     * Проверяем, хранится ли такая строка уже у нас
     * @param value - передаваемая строка
     * @return - есть такая строка или нет в нашей структуре
     *
     * n - число литер в value; m - кол-во строк с таким же набором букв, как и value
     * Сложность - [O(n) - лучшее; O(n * m) - худшее]
     */
    public boolean contains(String value) {
        if (value == null) {
            throw new IllegalArgumentException();
        }
        List<Integer> valueList = getIntegersFromString(value);
        if (map.containsKey(valueList)) {
            return map.get(valueList).contains(value);
        }
        return false;
    }

    /**
     * Удаляем сохраненную строку если она есть
     * @param value - какую строку мы хотим удалить
     * @return - true если удалили, false - если такой строки нет
     *
     * n - число литер в value; m - кол-во строк с таким же набором букв, как и value
     * Сложность - [O(n) - лучшее; O(n * m) - худшее]
     */
    public boolean remove(String value) {
        if (value == null) {
            throw new IllegalArgumentException();
        }
        List<Integer> valueList = getIntegersFromString(value);
        if (map.containsKey(valueList) && map.get(valueList).remove(value)) {
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
     * n - число литер в value;
     * Сложность - [O(n)]
     */
    public List<String> getSimilarWords(String value) {
        if (value == null) {
            throw new IllegalArgumentException();
        }
        return map.getOrDefault(
                getIntegersFromString(value),
                Collections.emptyList());
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

    private List<Integer> getIntegersFromString(String value) {
        return value.toLowerCase()
                .chars()
                .boxed()
                .sorted()
                .collect(Collectors.toList());
    }
}
