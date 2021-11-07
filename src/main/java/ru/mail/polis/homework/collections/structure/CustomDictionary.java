package ru.mail.polis.homework.collections.structure;

import java.util.*;

/**
 * Задание оценивается в 4 балла.
 * Необходимо реализовать класс, умеющий хранить строки и возвращать список строк,
 * состоящий из того же набора буков, что и переданная строка.
 *
 * Collection  |  Add   |  Remove  | Contains   |   Next   | Size |  Get  |  Put  |
 * ------------|--------|----------|------------|----------|------|-------|-------|
 * HashSet     |  O(1)  |   O(1)   |   O(1)     |  O(h/n)  | O(1) |       |       |
 * HashMap     |        |          | key - O(1) |  O(h/n)  | O(1) | O(1)  |  O(1) |
 *
 * для HashMap операции put и get выполняются за О(1) с учетом, что хеш-функция правильно
 * распределяет элементы по buckets, в противоположном случае требуется добавить m к
 * сложности посчитанных функций - amount of collision
 */
public final class CustomDictionary {

    private final Map<String, Set<String>> relation = new HashMap<>();
    private int size;

    /**
     * Сохранить строку в структуру данных.
     *
     * @param value - передаваемая строка
     * @return - успешно сохранили строку или нет
     * Сложность - О(nlogn), n = value.length,
     * т.к. сортировка за O(nlogn), get за О(1), add за О(1)
     */
    public boolean add(String value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException();
        }
        String sortedString = sortedString(value);
        relation.putIfAbsent(sortedString, new HashSet<>());
        if (!relation.get(sortedString).add(value)) {
            return false;
        }
        ++size;
        return true;
    }

    /**
     * Проверяем, хранится ли такая строка уже.
     *
     * @param value - передаваемая строка
     * @return - есть такая строка или нет в нашей структуре
     * Сложность - О(nlogn), n = value.length,
     * т.к. get - за О(1), сортировка за O(nlogn), contains - O(1)
     */
    public boolean contains(String value) {
        return relation.get(sortedString(value)) != null && relation.get(sortedString(value)).contains(value);
    }

    /**
     * Удаляем сохраненную строку, если она есть.
     *
     * @param value - какую строку удалить
     * @return - true, если удалили, или false, если такой строки нет
     * Сложность - О(nlogn), n = value.length,
     * т.к. get - за О(1), contains за O(nlogn), remove за O(1)
     */
    public boolean remove(String value) {
        if (!contains(value)) {
            return false;
        }
        size--;
        return relation.get(sortedString(value)).remove(value);
    }

    /**
     * Возвращает список из сохраненных ранее строк, которые состоят
     * из того же набора букв, что и переданная строка.
     * Регистр строки не влияет на поиск, а возвращаемые строки хранятся
     * в том же виде, что и изначально.
     *
     * @return - список слов, состоящих из тех же букв передаваемой строки
     * Сложность - О(nlogn), n = value.length,
     * т.к. get - за О(1) и сортировка за O(nlogn)
     */
    public List<String> getSimilarWords(String value) {
        return relation.get(sortedString(value)) != null ?
                Collections.unmodifiableList(new ArrayList<>(relation.get(sortedString(value))))
                    : Collections.emptyList();
    }

    /**
     * Кол-во хранимых строк.
     *
     * @return - Кол-во хранимых строк
     * Сложность - O(1)
     */
    public int size() {
        return size;
    }

    /**
     * Cортировка строки в алфавитном порядке посимвольно.
     * Сложность - О(nlogn), n = value.length,
     * т.к. JDK использует quicksort для примитивов
     */
    private String sortedString(String value) {
        return value.toLowerCase(Locale.ROOT).trim().chars().filter(Character::isLetter).sorted()
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
    }
}

