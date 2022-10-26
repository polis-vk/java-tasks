package ru.mail.polis.homework.collections.structure;

import java.util.*;

/**
 * Задание оценивается в 4 тугрика.
 * Необходимо реализовать класс которы умеет хранить строки и возвращать
 * список строк состоящий из того же набора буков, что ему передали строку.
 * Напишите какая сложность операций у вас получилась для каждого метода.
 */
public class CustomDictionary {

    List<String> store = new ArrayList<>();

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
        if (store.contains(value)) {
            return false;
        }
        return store.add(value);
    }

    /**
     * Проверяем, хранится ли такая строка уже у нас
     * @param value - передаваемая строка
     * @return - есть такая строка или нет в нашей структуре
     *
     * Сложность - [O(n)]
     */
    public boolean contains(String value) {
        return store.contains(value);
    }

    /**
     * Удаляем сохраненную строку если она есть
     * @param value - какую строку мы хотим удалить
     * @return - true если удалили, false - если такой строки нет
     *
     * Сложность - [O(n)]
     */
    public boolean remove(String value) {
        return store.remove(value);
    }

    /**
     * Возвращает список из сохраненных ранее строк, которые состоят
     * из того же набора букв, что нам передали строку.
     * Примеры:
     * сохраняем строки ["aaa", "aBa", "baa", "aaB"]
     * При поиске по строке "AAb" нам должен вернуться следующий
     * список: ["aBa","baa","aaB"]
     *
     * Сохраняем строки ["aaa", "aAa", "a"]
     * поиск "aaaa"
     * результат: []
     * Как можно заметить - регистр строки не должен влиять на поиск, при этом
     * возвращаемые строки хранятся в том виде, что нам передали изначально.
     *
     * @return - список слов которые состоят из тех же букв, что и передаваемая
     * строка.
     *
     * Сложность - [O(n * k), где k - количество букв в слове, n - размер массива]
     */
    public List<String> getSimilarWords(String value) {

        Character[] letters = new Character[value.length()];
        List<String> result = new ArrayList<>();

        for (int i = 0; i < value.length(); i++) {
            if (value.toLowerCase(Locale.ROOT).charAt(i) >= 97 && value.toLowerCase(Locale.ROOT).charAt(i) <= 122) {
                letters[i] = value.toLowerCase(Locale.ROOT).charAt(i);
            }
        }

        // Counting unique letters in value
        int[] countOfUniqueLetterInValue = new int[26];
        int shift = 97; // Position of 'a' in ascii

        for (int i = 0; i < letters.length; i++) {
            if (letters[i] == null) {
                continue;
            }
            countOfUniqueLetterInValue[letters[i] - shift]++;
        }

        for (String element : store) {
            if (value.length() != element.length()) {
                continue;
            }

            int[] countOfUniqueLetterInElement = new int[26];
            Character[] lettersInElement = new Character[element.length()];

            for (int i = 0; i < value.length(); i++) {
                if (element.toLowerCase(Locale.ROOT).charAt(i) >= 97 && element.toLowerCase(Locale.ROOT).charAt(i) <= 122) {
                    lettersInElement[i] = element.toLowerCase(Locale.ROOT).charAt(i);
                }
            }


            for (int i = 0; i < element.length(); i++) {
                if (lettersInElement[i] == null) {
                    continue;
                }
                countOfUniqueLetterInElement[lettersInElement[i] - shift]++;
            }

            if (Arrays.equals(countOfUniqueLetterInElement, countOfUniqueLetterInValue)) {
                result.add(element);
            }
        }

        return result;
    }

    /**
     * Колл-во хранимых строк.
     * @return - Колл-во хранимых строк.
     *
     * Сложность - [O(1)]
     */
    public int size() {
        return store.size();
    }


}
