package ru.mail.polis.homework.collections.structure;

import java.util.*;

/**
 * Задание оценивается в 4 балла.
 * Необходимо реализовать класс которы умеет хранить строки и возвращать
 * список строк состоящий из того же набора буков, что ему передали строку.
 * Напишите какая сложность операций у вас получилась для каждого метода.
 */
public class CustomDictionary {
    private final Map<Map<Character, Integer>, Set<String>> words = new HashMap<>();
    private int size = 0;

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
        Map<Character, Integer> charsToNumbers = convertWord(value);
        Set<String> similarValues = words.get(charsToNumbers);

        if (similarValues == null) {
            similarValues = new HashSet<>();
            similarValues.add(value);
            words.put(charsToNumbers, similarValues);
            size++;
            return true;
        }

        if (similarValues.contains(value)) {
            return false;
        }

        similarValues.add(value);
        size++;
        return true;
    }

    /**
     * Проверяем, хранится ли такая строка уже у нас
     * @param value - передаваемая строка
     * @return - есть такая строка или нет в нашей структуре
     *
     * Сложность - O(n)
     */
    public boolean contains(String value) {
        Map<Character, Integer> charsToNumbers = convertWord(value);
        Set<String> similarValues = words.get(charsToNumbers);
        return similarValues != null && similarValues.contains(value);
    }

    /**
     * Удаляем сохраненную строку если она есть
     * @param value - какую строку мы хотим удалить
     * @return - true если удалили, false - если такой строки нет
     *
     * Сложность - O(n)
     */
    public boolean remove(String value) {
        Map<Character, Integer> charsToNumbers = convertWord(value);
        Set<String> similarValues = words.get(charsToNumbers);
        if (similarValues == null || !similarValues.contains(value)) {
            return false;
        }

        size--;
        if (similarValues.size() == 1) {
            return words.remove(charsToNumbers, similarValues);
        }

        return similarValues.remove(value);
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
        Map<Character, Integer> charsToNumbers = convertWord(value);
        Set<String> similarValues = words.get(charsToNumbers);

        if (similarValues == null) {
            return Collections.emptyList();
        }

        return new ArrayList<>(words.get(convertWord(value)));
    }

    /**
     * Колл-во хранимых строк.
     * @return - Колл-во хранимых строк.
     *
     * Сложность - O(1)
     */
    public int size() {
        return size;
    }


    /**
     * Преобразует строку в Map, где к символу в нижнем регистре
     * сопоставляется число, сколько раз он встречается в слове.
     * <p>
     *  @param value - исходная строка.
     *  @return - Map символ -> его количество в слове.
     * Сложность - O(m), m - длина слова.
     */
    private Map<Character, Integer> convertWord(String value) {
        Map<Character, Integer> chars = new HashMap<>();
        String valueAtLowerCase = value.toLowerCase();

        for (int i = 0; i < valueAtLowerCase.length(); i++) {
            char currentChar = valueAtLowerCase.charAt(i);
            chars.put(currentChar, chars.getOrDefault(currentChar, 0) + 1);
        }

        return chars;
    }
}
