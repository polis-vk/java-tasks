package ru.mail.polis.homework.collections.structure;

import java.util.*;

/**
 * Задание оценивается в 4 тугрика.
 * Необходимо реализовать класс которы умеет хранить строки и возвращать
 * список строк состоящий из того же набора буков, что ему передали строку.
 * Напишите какая сложность операций у вас получилась для каждого метода.
 */
public class CustomDictionary {

    private final HashMap<String, Set<String>> pairs = new HashMap<>();

    /**
     * Сохранить строку в структуру данных
     *
     * @param value - передаваемая строка
     * @return - успешно сохранили строку или нет.
     * <p>
     * Сложность - [O(k*log(k), где k - длина строки value]
     */
    public boolean add(String value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException();
        }
        if (contains(value)) {
            return false;
        }

        String alteredValue = alterString(value);
        Set<String> values = pairs.get(alteredValue);
        if (values == null) {
            values = new HashSet<>();
        }
        values.add(value);
        pairs.put(alteredValue, values);
        return true;
    }

    // Сложность - [O(k*log(k), где k - длина строки value]
    private String alterString(String value) {
        char[] ch = value.toLowerCase().toCharArray();
        Arrays.sort(ch);
        return new String(ch);
    }

    /**
     * Проверяем, хранится ли такая строка уже у нас
     *
     * @param value - передаваемая строка
     * @return - есть такая строка или нет в нашей структуре
     * <p>
     * Сложность - [O(k*log(k), где k - длина строки value]
     */
    public boolean contains(String value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException();
        }

        String alteredValue = alterString(value);
        if (!pairs.containsKey(alteredValue)) {
            return false;
        }

        return pairs.get(alteredValue).contains(value);
    }

    /**
     * Удаляем сохраненную строку если она есть
     *
     * @param value - какую строку мы хотим удалить
     * @return - true если удалили, false - если такой строки нет
     * <p>
     * Сложность - [O(k*log(k), где k - длина строки value]
     */
    public boolean remove(String value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException();
        }

        String alteredString = alterString(value);
        if (!pairs.containsKey(alteredString)) {
            return false;
        }

        return pairs.get(alteredString).remove(value);
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
     * Сложность - [O(k*log(k), где k - длина строки value]
     */
    public List<String> getSimilarWords(String value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException();
        }

        String alteredValue = alterString(value);
        if (!pairs.containsKey(alteredValue)) {
            return Collections.emptyList();
        }

        return new ArrayList<>(pairs.get(alteredValue));
    }

    /**
     * Колл-во хранимых строк.
     *
     * @return - Колл-во хранимых строк.
     * <p>
     * Сложность - [Θ(n), где n - количество ключей в словаре]
     */
    public int size() {
        return pairs.values().stream().mapToInt(Set::size).sum();
    }
}
