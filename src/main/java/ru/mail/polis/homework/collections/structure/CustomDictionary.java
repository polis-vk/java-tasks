package ru.mail.polis.homework.collections.structure;

import java.util.*;

/**
 * Задание оценивается в 4 тугрика.
 * Необходимо реализовать класс которы умеет хранить строки и возвращать
 * список строк состоящий из того же набора буков, что ему передали строку.
 * Напишите какая сложность операций у вас получилась для каждого метода.
 */
public class CustomDictionary {
    private final HashMap<String, Set<String>> map = new HashMap<>();
    private int length = 0;

    /**
     * Сохранить строку в структуру данных
     *
     * @param value - передаваемая строка
     * @return - успешно сохранили строку или нет.
     * <p>
     * Сложность - [O(n log(n)]
     * n - длинна стринга value
     */
    public boolean add(String value) {
        if (contains(value)) {
            return false;
        }
        String adjustedValue = adjustString(value);
        Set<String> values = map.get(adjustedValue);
        if (values == null) {
            values = new HashSet<>();
        }
        values.add(value);
        length++;
        map.put(adjustedValue, values);
        return true;
    }

    /**
     * Проверяем, хранится ли такая строка уже у нас
     *
     * @param value - передаваемая строка
     * @return - есть такая строка или нет в нашей структуре
     * <p>
     * Сложность - [O(n log(n)]
     * n - длинна стринга value
     */
    public boolean contains(String value) {
        String adjustedValue = adjustString(value);
        if (!map.containsKey(adjustedValue)) {
            return false;
        }

        return map.get(adjustedValue).contains(value);
    }

    /**
     * Удаляем сохраненную строку если она есть
     *
     * @param value - какую строку мы хотим удалить
     * @return - true если удалили, false - если такой строки нет
     * <p>
     * Сложность - [O(n log(n)]
     * n - длинна стринга value
     */
    public boolean remove(String value) {
        String adjustedValue = adjustString(value);
        if (!map.containsKey(adjustedValue)) {
            return false;
        }
        if (map.get(adjustedValue).remove(value)) {
            length--;
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
     * Сложность - [O(n log(n)]
     * n - длинна стринга value
     */
    public List<String> getSimilarWords(String value) {
        String adjustedValue = adjustString(value);
        if (!map.containsKey(adjustedValue)) {
            return Collections.emptyList();
        }
        return new ArrayList<>(map.get(adjustedValue));
    }

    /**
     * Колл-во хранимых строк.
     *
     * @return - Колл-во хранимых строк.
     * <p>
     * Сложность - [O(1)]
     */
    public int size() {
        return length;
    }

    private static String adjustString(String string) {
        if (string == null || string.isEmpty()) {
            throw new IllegalArgumentException();
        }
        char[] array = string.toLowerCase().toCharArray();
        Arrays.sort(array);
        return new String(array);
    }

}
