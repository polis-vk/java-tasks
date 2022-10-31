package ru.mail.polis.homework.collections.structure;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Задание оценивается в 4 тугрика.
 * Необходимо реализовать класс которы умеет хранить строки и возвращать
 * список строк состоящий из того же набора буков, что ему передали строку.
 * Напишите какая сложность операций у вас получилась для каждого метода.
 */
public class CustomDictionary {
    List<String> stringContainer = new ArrayList<>();

    /**
     * Сохранить строку в структуру данных
     *
     * @param value - передаваемая строка
     * @return - успешно сохранили строку или нет.
     * <p>
     * Сложность - [O(1) - average, but if list.size is full it will cost O(1), because we need to increase capacity]
     */
    public boolean add(String value) {
        if (value == null || value.equals("")) {
            throw new IllegalArgumentException();
        }

        if (stringContainer.contains(value)) {
            return false;
        }

        stringContainer.add(value);
        return true;
    }

    /**
     * Проверяем, хранится ли такая строка уже у нас
     *
     * @param value - передаваемая строка
     * @return - есть такая строка или нет в нашей структуре
     * <p>
     * Сложность - [O(n) - average]
     */
    public boolean contains(String value) {
        return stringContainer.contains(value);
    }

    /**
     * Удаляем сохраненную строку если она есть
     *
     * @param value - какую строку мы хотим удалить
     * @return - true если удалили, false - если такой строки нет
     * <p>
     * Сложность - [O(n) - average]
     */
    public boolean remove(String value) {
        return stringContainer.remove(value);
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
     * Сложность - []
     */
    public List<String> getSimilarWords(String value) {
        List<String> listOfSimilarWords = new ArrayList<>();
        for (String string : stringContainer) {
            if (!isSimilarWords(string, value)) {
                continue;
            }
            listOfSimilarWords.add(string);
        }
        if (listOfSimilarWords.isEmpty()) {
            return Collections.emptyList();
        }
        return listOfSimilarWords;
    }

    private boolean isSimilarWords(String string1, String string2) {
        if (string1.length() != string2.length()) {
            return false;
        }

        if (string1.equalsIgnoreCase(string2)) {
            return true;
        }

        String temp1 = string1.toLowerCase();
        String temp2 = string2.toLowerCase(); // возможно надо создать новые строки

        int sumCharCodesStr1 = 0;
        int sumCharCodesStr2 = 0;
        for (int i = 0; i < string1.length(); i++) {
            sumCharCodesStr1 += temp1.charAt(i);
            sumCharCodesStr2 += temp2.charAt(i);
        }

        return sumCharCodesStr1 == sumCharCodesStr2;
    }

    /**
     * Колл-во хранимых строк.
     *
     * @return - Колл-во хранимых строк.
     * <p>
     * Сложность - [O(1)]
     */
    public int size() {
        return stringContainer.size();
    }


}
