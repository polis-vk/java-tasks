package ru.mail.polis.homework.collections.structure;

import java.util.*;

/**
 * Задание оценивается в 4 балла.
 * Необходимо реализовать класс, умеющий хранить строки и возвращать список строк,
 * состоящий из того же набора буков, что и переданная строка.
 * Collection            |  Add   |  Remove  | Contains  |   Next   | Size |  Get  |  Put  |
 * ----------------------|--------|----------|-----------|----------|------|-------|-------|
 * ArrayList             |  O(1)  |   O(n)   |   O(n)    |   O(1)   | O(1) | O(1)  |       |
 * HashMap               |        |          |  for key  |  O(h/n)  | O(1) | O(1)  |  O(1) |
 *                       |        |          |   O(1)    |          |      |       |       |
 * для HashMap операции put и get выполняются за О(1) с учтом, что хеш-функция правильно
 * распределяет элементы по buckets, в противоположном случае требуется добавить m к
 * сложности посчитанных функций - amount of collision
 */
public final class CustomDictionary {

    private final Map<String, List<String>> relation = new HashMap<>();
    private int size;
    /**
     * Сохранить строку в структуру данных.
     * @param value - передаваемая строка
     * @return - успешно сохранили строку или нет
     * Сложность - О(nlogn), n = value.length,
     * т.к. сортировка за O(nlogn), get за О(1), contains за O(n),
     */
    public boolean add(String value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException();
        }
        String sortedString = sortedString(value);
        List<String> originalStringList = relation.getOrDefault(sortedString(value), new ArrayList<>());
        if (originalStringList.contains(value)) {
            return false;
        }
        originalStringList.add(value);
        relation.put(sortedString, originalStringList);
        size++;
        return true;
    }

    /**
     * Проверяем, хранится ли такая строка уже.
     * @param value - передаваемая строка
     * @return - есть такая строка или нет в нашей структуре
     * Сложность - О(nlogn), n = value.length,
     * т.к. get - за О(1) и сортировка за O(nlogn)
     */
    public boolean contains(String value) {
        List<String> originalStringList = relation.get(sortedString(value));
        return originalStringList != null && originalStringList.contains(value);
    }

    /**
     * Удаляем сохраненную строку, если она есть.
     * @param value - какую строку удалить
     * @return - true, если удалили, или false, если такой строки нет
     * Сложность - О(nlogn), n = value.length,
     * т.к. get - за О(1), contains за O(nlogn), remove за O(n)
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
     * из того же набора букв, что и переданая строка.
     * Регистр строки не влияет на поиск, а возвращаемые строки хранятся
     * в том же виде, что и изначально.
     * @return - список слов, состоящих из тех же букв передаваемой строки
     * Сложность - О(nlogn), n = value.length,
     * т.к. get - за О(1) и сортировка за O(nlogn)
     */
    public List<String> getSimilarWords(String value) {
        List<String> valueList = relation.getOrDefault(sortedString(value), null);
        return valueList != null ? valueList : Collections.emptyList();
    }

    /**
     * Кол-во хранимых строк.
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
        return value.toLowerCase(Locale.ROOT).trim().chars().sorted().collect(StringBuilder::new,
                StringBuilder::appendCodePoint, StringBuilder::append).toString();
    }
}

