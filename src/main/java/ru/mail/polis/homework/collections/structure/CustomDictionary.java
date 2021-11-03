package ru.mail.polis.homework.collections.structure;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Задание оценивается в 4 балла.
 * Необходимо реализовать класс, который умеет хранить строки и возвращать
 * список строк, состоящих из того же набора букв, что и переданная строка.
 * Напишите, какая сложность операций у вас получилась для каждого метода.
 */
public class CustomDictionary {

    private final Map<List<Character>, List<String>> dict = new HashMap<>();
    private int size = 0;

    /**
     * Сохранить строку в структуру данных
     *
     * @param value - передаваемая строка
     * @return - успешно сохранили строку или нет.
     * <p>
     * Сложность - O(m * log(m)), где m - количество символов в строке
     */
    public boolean add(String value) {
        if (value == null || value.equals("")) {
            throw new IllegalArgumentException ();
        }
        List<Character> key = toOrderedListOfLowerCaseChars(value);
        if (dict.containsKey(key)) {
            if (dict.get(key).contains(value)) {
                return false;
            } else {
                dict.get(key).add(value);
                size++;
            }
        } else {
            List<String> newValue = new ArrayList<>();
            newValue.add(value);
            dict.put(key, newValue);
            size++;
        }
        return true;
    }

    /**
     * Проверяем, хранится ли такая строка уже у нас
     *
     * @param value - передаваемая строка
     * @return - есть такая строка или нет в нашей структуре
     * <p>
     * Сложность - O(m * log(m)), где m - количество символов в строке
     */
    public boolean contains(String value) {
        if (value == null || value.equals("")) {
            throw new IllegalArgumentException();
        }
        List<Character> key = toOrderedListOfLowerCaseChars(value);
        return size() != 0 && dict.containsKey(key) && dict.get(key).contains(value);
    }

    /**
     * Удаляем сохраненную строку, если она есть
     *
     * @param value - какую строку мы хотим удалить
     * @return - true если удалили, false - если такой строки нет
     * <p>
     * Сложность - O(m * log(m)), где m - количество символов в строке
     */
    public boolean remove(String value) {
        if (value == null || value.equals("")) {
            throw new IllegalArgumentException();
        }
        List<Character> key = toOrderedListOfLowerCaseChars(value);
        if (contains(value)) {
            List<String> dictValue = dict.get(key);
            dictValue.remove(value);
            if (dictValue.isEmpty()) {
                dict.remove(key, dictValue);
            }
            size--;
            return true;
        }
        return false;
    }

    /**
     * Возвращает список из сохраненных ранее строк, которые состоят
     * из тех же букв, что и переданная строка.
     * Примеры: сохраняем строки ["aaa", "aBa", "baa", "aaB"]
     * При поиске по строке "AAb" нам должен вернуться следующий
     * список: ["aBa","baa","aaB"]
     * <p>
     * Как можно заметить - регистр строки не должен влиять на поиск, при этом
     * возвращаемые строки хранятся в том виде, что нам передали изначально.
     *
     * @return - список слов, которые состоят из тех же букв, что и передаваемая
     * строка.
     * <p>
     * Сложность - O(m * log(m)), где m - количество символов в строке
     */
    public List<String> getSimilarWords(String value) {
        if (value == null || value.equals("")) {
            throw new IllegalArgumentException();
        }
        List<Character> key = toOrderedListOfLowerCaseChars(value);
        return (size != 0 && dict.containsKey(key))? dict.get(key) : new ArrayList<>();
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

    /**
     * Функция, преобразующая строку в отсортированный лист символов
     *
     * @param value - строка, которую необходимо преобразовать
     * @return - отсортированный список символов
     * <p>
     * Сложность - O(m * log(m)), где m - количество символов в списке
     */
    private List<Character> toOrderedListOfLowerCaseChars(String value) {
        String lowerCaseStr = value.toLowerCase();
        List<Character> res = new ArrayList<>();
        for (int i = 0; i < lowerCaseStr.length(); i++) {
            res.add(lowerCaseStr.charAt(i));
        }
        Collections.sort(res);
        return res;
    }
}
