package ru.mail.polis.homework.collections.structure;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * Задание оценивается в 4 балла.
 * Необходимо реализовать класс которы умеет хранить строки и возвращать
 * список строк состоящий из того же набора буков, что ему передали строку.
 * Напишите какая сложность операций у вас получилась для каждого метода.
 */
public class CustomDictionary {
    public List<String> strgs = new ArrayList<>();

    /**
     * Сохранить строку в структуру данных
     *
     * @param value - передаваемая строка
     * @return - успешно сохранили строку или нет.
     * <p>
     * Сложность - O(n)
     */
    public boolean add(String value) {
        if (value == null || value.equals("")) {
            throw new IllegalArgumentException();
        }
        if (strgs.contains(value)) {
            return false;
        }
        strgs.add(value);
        return true;
    }

    /**
     * Проверяем, хранится ли такая строка уже у нас
     *
     * @param value - передаваемая строка
     * @return - есть такая строка или нет в нашей структуре
     * <p>
     * Сложность - O(n)
     */
    public boolean contains(String value) {
        if (value == null || value.equals("")) {
            throw new IllegalArgumentException();
        } else return strgs.contains(value);
    }

    /**
     * Удаляем сохраненную строку если она есть
     *
     * @param value - какую строку мы хотим удалить
     * @return - true если удалили, false - если такой строки нет
     * <p>
     * Сложность - O(n)
     */
    public boolean remove(String value) {
        if (value == null || value.equals("")) {
            throw new IllegalArgumentException();
        }
        if (!strgs.contains(value)) {
            return false;
        }
        strgs.remove(value);
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
     * Сложность - O(N^2)
     */
    public List<String> getSimilarWords(String value) {
        List<String> founded = new ArrayList<>();
        for (String strg : strgs) {
            if (findSimilars(strg, value)) {
                founded.add(strg);
            }
        }
        return founded;
    }

    public boolean findSimilars(String word, String str) {
        List<Character> letters = new ArrayList<>();
        List<Character> similarLetters = new ArrayList<>();
        for (char ch : word.toLowerCase().toCharArray()) {
            letters.add(ch);
        }
        for (char ch : str.toLowerCase().toCharArray()) {
            similarLetters.add(ch);
        }

        for (int i = 0; i < letters.size(); i++) {
            for (int j = 0; j < similarLetters.size(); j++) {
                if (letters.get(i) == similarLetters.get(j)) {
                    letters.remove(i);
                    similarLetters.remove(j);
                    i = -1;
                    break;
                }
            }
        }
        return letters.size() == 0 && similarLetters.size() == 0;
    }

    /**
     * Колл-во хранимых строк.
     *
     * @return - Колл-во хранимых строк.
     * <p>
     * Сложность - O(1)
     */
    public int size() {
        return strgs.size();
    }


}
