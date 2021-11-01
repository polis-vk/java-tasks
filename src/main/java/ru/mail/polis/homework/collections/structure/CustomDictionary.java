package ru.mail.polis.homework.collections.structure;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Задание оценивается в 4 балла.
 * Необходимо реализовать класс которы умеет хранить строки и возвращать
 * список строк состоящий из того же набора буков, что ему передали строку.
 * Напишите какая сложность операций у вас получилась для каждого метода.
 */
public class CustomDictionary {

    Set<String> dictionary = new HashSet<>();

    /**
     * Сохранить строку в структуру данных
     *
     * @param value - передаваемая строка
     * @return - успешно сохранили строку или нет.
     * <p>
     * Сложность - [O(1)]
     */
    public boolean add(String value) {
        if (value == null || value.equals("")) {
            throw new IllegalArgumentException();
        }
        return dictionary.add(value);
    }

    /**
     * Проверяем, хранится ли такая строка уже у нас
     *
     * @param value - передаваемая строка
     * @return - есть такая строка или нет в нашей структуре
     * <p>
     * Сложность - [O(log(n)) в худшем / O(1) в среднем]
     */
    public boolean contains(String value) {
        return dictionary.contains(value);
    }

    /**
     * Удаляем сохраненную строку если она есть
     *
     * @param value - какую строку мы хотим удалить
     * @return - true если удалили, false - если такой строки нет
     * <p>
     * Сложность - [O(1)]
     */
    public boolean remove(String value) {
        return dictionary.remove(value);
    }

    /**
     * Возвращает список из сохраненных ранее строк, которые состоят
     * из тех же букв что нам передали строку.
     * Примеры: сохраняем строки ["aaa", "aBa", "baa", "aaB"]
     * При поиске по строке "AAb" нам должен вернуться следующий
     * список: ["aBa","baa","aaB"]
     * <p>
     * Как можно заметить - регистр строки не должен влиять на поиск, при этом
     * возвращаемые строки хранятся в том виде что нам передали изначально.
     *
     * @return - список слов которые состоят из тех же букв, что и передаваемая
     * строка.
     * <p>
     * Сложность - [O(n*m) n - размер сета, m - длина строки]
     */
    public List<String> getSimilarWords(String value) {
        if (value == null || value.equals("")) {
            throw new IllegalArgumentException();
        }
        List<String> similarWords = new ArrayList<>();
        for (String word : dictionary) {
            if (checkSimilar(word, value)) {
                similarWords.add(word);
            }
        }
        return similarWords;
    }

    private boolean checkSimilar(String dictWord, String value) {
        if (value.length() != dictWord.length()) {
            return false;
        }
        String dictWordLC = dictWord.toLowerCase();
        String valueLC = value.toLowerCase();
        List<Character> list = new ArrayList<>();
        for (int i = 0; i < valueLC.length(); i++) {
            list.add(dictWordLC.charAt(i));
        }
        for (int i = 0; i < valueLC.length(); i++) {
            Character letter = valueLC.charAt(i);
            list.remove(letter);
        }
        return list.isEmpty();
    }

    /**
     * Колл-во хранимых строк.
     *
     * @return - Колл-во хранимых строк.
     * <p>
     * Сложность - [O(1)]
     */
    public int size() {
        return dictionary.size();
    }


}