package ru.mail.polis.homework.collections.structure;

import java.util.*;

/**
 * Задание оценивается в 4 тугрика.
 * Необходимо реализовать класс которы умеет хранить строки и возвращать
 * список строк состоящий из того же набора буков, что ему передали строку.
 * Напишите какая сложность операций у вас получилась для каждого метода.
 */
public class CustomDictionary {
    private final Set<String> set = new HashSet<>();

    /**
     * Сохранить строку в структуру данных
     * @param value - передаваемая строка
     * @return - успешно сохранили строку или нет.
     *
     * Сложность - [O(1)]
     */
    public boolean add(String value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException();
        }
        return set.add(value);
    }

    /**
     * Проверяем, хранится ли такая строка уже у нас
     * @param value - передаваемая строка
     * @return - есть такая строка или нет в нашей структуре
     *
     * Сложность - [O(1)]
     */
    public boolean contains(String value) {
        return set.contains(value);
    }

    /**
     * Удаляем сохраненную строку если она есть
     * @param value - какую строку мы хотим удалить
     * @return - true если удалили, false - если такой строки нет
     *
     * Сложность - [O(1)]
     */
    public boolean remove(String value) {
        return set.remove(value);
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
     * Сложность - [O(n * m * log(m)) : где n - размер словаря, m - value.length()]
     */
    public List<String> getSimilarWords(String value) {
        List<String> similarWords = new ArrayList<>();
        for (String str : set) {
            PriorityQueue<Integer> valueChars = new PriorityQueue<>();
            for (char c : value.toLowerCase().toCharArray()) {
                valueChars.add((int) c);
            }

            if (value.length() == str.length()) {
                for (char c : str.toLowerCase().toCharArray()) {
                    if (!valueChars.remove((int) c)) {
                        break;
                    }
                }

                if (valueChars.isEmpty()) {
                    similarWords.add(str);
                }
            }
        }

        return similarWords;
    }

    /**
     * Колл-во хранимых строк.
     * @return - Колл-во хранимых строк.
     *
     * Сложность - [O(1)]
     */
    public int size() {
        return set.size();
    }
}
