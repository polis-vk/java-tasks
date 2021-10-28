package ru.mail.polis.homework.collections.structure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Задание оценивается в 4 балла.
 * Необходимо реализовать класс которы умеет хранить строки и возвращать
 * список строк состоящий из того же набора буков, что ему передали строку.
 * Напишите какая сложность операций у вас получилась для каждого метода.
 */
public class CustomDictionary {
    private final List<Word> words = new ArrayList<>();
    /**
     * Сохранить строку в структуру данных
     * @param value - передаваемая строка
     * @return - успешно сохранили строку или нет.
     *
     * Сложность - O(n)
     */
    public boolean add(String value) {
        if (value == null || value.length() == 0) {
            throw new IllegalArgumentException();
        }
        for (Word item : words) {
            if (item.getValue().equals(value)) {
                return false;
            }
        }
        return words.add(new Word(value));
    }

    /**
     * Проверяем, хранится ли такая строка уже у нас
     * @param value - передаваемая строка
     * @return - есть такая строка или нет в нашей структуре
     *
     * Сложность - O(n)
     */
    public boolean contains(String value) {
        for (Word word : words) {
            if (word.getValue().equals(value)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Удаляем сохраненную строку если она есть
     * @param value - какую строку мы хотим удалить
     * @return - true если удалили, false - если такой строки нет
     *
     * Сложность - O(n)
     */
    public boolean remove(String value) {
        int indexToRemove = -1;
        for (int i = 0; i < words.size(); i++) {
            if (words.get(i).getValue().equals(value)) {
                indexToRemove = i;
            }
        }
        if (indexToRemove == -1) {
            return false;
        }
        words.remove(indexToRemove);
        return true;
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
     * Сложность - O(n * m), m - длина слова
     */
    public List<String> getSimilarWords(String value) {
        List<String> res = new ArrayList<>();
        Word word = new Word(value);

        for (Word item : words) {
            if (word.consistOfSameLetters(item)) {
                res.add(item.value);
            }
        }
        return res;
    }

    /**
     * Колл-во хранимых строк.
     * @return - Колл-во хранимых строк.
     *
     * Сложность - O(1)
     */
    public int size() {
        return words.size();
    }

    private static class Word {
        private final String value;
        private final Map<Character, Integer> chars = new HashMap<>();

        private Word(String value) {
            this.value = value;

            String valueAtLowerCase = value.toLowerCase();
            for (int i = 0; i < valueAtLowerCase.length(); i++) {
                char currentChar = valueAtLowerCase.charAt(i);
                chars.put(currentChar, chars.getOrDefault(currentChar, 0) + 1);
            }
        }

        private String getValue() {
            return value;
        }

        private boolean consistOfSameLetters(Word other) {
            return chars.equals(other.chars);
        }
    }
}
