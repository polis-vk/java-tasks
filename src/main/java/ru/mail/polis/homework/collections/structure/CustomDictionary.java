package ru.mail.polis.homework.collections.structure;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        if (value == null || value.equals("")) {
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
     * Сложность - [O(n * Max(Character.MAX_VALUE, value.length)) ~ O(n * value.length), где n - размер словаря ]
     */
    public List<String> getSimilarWords(String value) {
        int[] valueChars = getCharsCountArray(value);
        List<String> similarWords = new ArrayList<>();

        for (String str : set) {
            int[] strChars = getCharsCountArray(str);

            boolean charsCountsMatch = true;
            for (int i = 0; i < strChars.length; i++) {
                if (strChars[i] != valueChars[i]) {
                    charsCountsMatch = false;
                    break;
                }
            }

            if (charsCountsMatch) {
                similarWords.add(str);
            }
        }

        return similarWords;
    }

    private int[] getCharsCountArray(String s) {
        int[] charsCount = new int[Character.MAX_VALUE + 1];
        for (char c : s.toCharArray()) {
            if (c >= 'A' && c <= 'Z') {
                charsCount[c - 'A' + 'a']++;
            } else {
                charsCount[c]++;
            }
        }
        return charsCount;
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
