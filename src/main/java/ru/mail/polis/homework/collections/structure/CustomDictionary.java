package ru.mail.polis.homework.collections.structure;

import java.util.*;

/**
 * Задание оценивается в 4 балла.
 * Необходимо реализовать класс которы умеет хранить строки и возвращать
 * список строк состоящий из того же набора буков, что ему передали строку.
 * Напишите какая сложность операций у вас получилась для каждого метода.
 */
public class CustomDictionary {
    private Map<String, Set<String>> dictionary = new HashMap<>();
    private int size = 0;

    private String toKey(String value) {
        if (value == null || value.equals("")) {
            throw new IllegalArgumentException();
        }
        char[] temp = value.toLowerCase(Locale.ROOT).toCharArray();
        Arrays.sort(temp);
        return new String(temp);
    }

    /**
     * Сохранить строку в структуру данных
     *
     * @param value - передаваемая строка
     * @return - успешно сохранили строку или нет.
     * <p>
     * Сложность - O(1)
     */

    public boolean add(String value) {
        if (value == null || value.equals("")) {
            throw new IllegalArgumentException();
        }
        boolean flag = dictionary.computeIfAbsent(toKey(value), k -> new HashSet<>()).add(value);
        if (flag) {
            ++size;
        }
        return flag;
    }

    /**
     * Проверяем, хранится ли такая строка уже у нас
     *
     * @param value - передаваемая строка
     * @return - есть такая строка или нет в нашей структуре
     * <p>
     * Сложность - O(1)
     */

    public boolean contains(String value) {
        if (value == null || value.equals("")) {
            throw new IllegalArgumentException();
        } else {
            if (!dictionary.isEmpty()) {
                if (dictionary.get(toKey(value)) != null) {
                    return dictionary.get(toKey(value)).contains(value);
                }
            }
            return false;
        }
    }

    /**
     * Удаляем сохраненную строку если она есть
     *
     * @param value - какую строку мы хотим удалить
     * @return - true если удалили, false - если такой строки нет
     * <p>
     * Сложность - O(1)
     */

    public boolean remove(String value) {
        if (value == null || value.equals("")) {
            throw new IllegalArgumentException();
        }
        if (contains(value)) {
            --size;
            return dictionary.get(toKey(value)).remove(value);
        } else {
            return false;
        }
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
     * Сложность - O(1)
     */

    public List<String> getSimilarWords(String value) {
        return (dictionary.containsKey(toKey(value)) ? new ArrayList<>(dictionary.get(toKey(value))) : Collections.emptyList());
    }

    /**
     * Колл-во хранимых строк.
     *
     * @return - Колл-во хранимых строк.
     * <p>
     * Сложность - O(1)
     */
    public int size() {
        return size;
    }
}
