package ru.mail.polis.homework.collections.structure;

import java.util.*;

/**
 * Задание оценивается в 4 балла.
 * Необходимо реализовать класс которы умеет хранить строки и возвращать
 * список строк состоящий из того же набора буков, что ему передали строку.
 * Напишите какая сложность операций у вас получилась для каждого метода.
 */
public class CustomDictionary {
    private final Map<String, Set<String>> map = new HashMap<>();
    private int size = 0;

    /**
     * Сохранить строку в структуру данных
     *
     * @param value - передаваемая строка
     * @return - успешно сохранили строку или нет.
     * <p>
     * Сложность - от O(l*log(l))
     * l - value.length()
     */
    public boolean add(String value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException();
        }
        if (!map.computeIfAbsent(getChars(value), k -> new HashSet<>()).add(value)) {
            return false;
        }
        size++;
        return true;
    }

    /**
     * Проверяем, хранится ли такая строка уже у нас
     *
     * @param value - передаваемая строка
     * @return - есть такая строка или нет в нашей структуре
     * <p>
     * Сложность - O(l*log(l) + log(n))
     * l - value.lenght()
     * n - map.size()
     */
    public boolean contains(String value) {
        if (value == null || value.isEmpty()) {
            return false;
        }
        String chars = getChars(value);
        if (!map.containsKey(chars)) {
            return false;
        }
        return map.get(chars).contains(value);
    }

    /**
     * Удаляем сохраненную строку если она есть
     *
     * @param value - какую строку мы хотим удалить
     * @return - true если удалили, false - если такой строки нет
     * <p>
     * Сложность - O(l*log(l) + log(n))
     * l - value.lenght()
     * n - map.size()
     */
    public boolean remove(String value) {
        if (value == null || value.isEmpty()) {
            return false;
        }
        String chars = getChars(value);
        if (!map.containsKey(chars)) {
            return false;
        }
        if (!map.get(chars).remove(value)) {
            return false;
        }
        size--;
        return true;
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
     * Сложность - O(l*log(l) + log(n))
     * l - value.lenght()
     * n - map.size()
     */
    public List<String> getSimilarWords(String value) {
        if (value == null || value.isEmpty()) {
            return Collections.emptyList();
        }
        return new ArrayList<>(map.getOrDefault(getChars(value), Collections.emptySet()));
    }

    /**
     * Колл-во хранимых строк.
     *
     * @return - Колл-во хранимых строк.
     * <p>
     * Сложность - O[1]
     */
    public int size() {
        return size;
    }

    private static String getChars(String s) {
        char[] array = s.toLowerCase().toCharArray();
        Arrays.sort(array);
        return new String(array);
    }
}
