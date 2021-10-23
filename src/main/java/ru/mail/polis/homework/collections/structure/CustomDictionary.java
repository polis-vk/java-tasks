package ru.mail.polis.homework.collections.structure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Задание оценивается в 4 балла.
 * Необходимо реализовать класс которы умеет хранить строки и возвращать
 * список строк состоящий из того же набора буков, что ему передали строку.
 * Напишите какая сложность операций у вас получилась для каждого метода.
 */
public class CustomDictionary {

    private final HashMap<String, ArrayList<String>> data = new HashMap<>();
    private int size = 0;

    /**
     * Сохранить строку в структуру данных
     *
     * @param value - передаваемая строка
     * @return - успешно сохранили строку или нет.
     * <p>
     * Сложность - O(1) аммортизационная
     */
    public boolean add(String value) {
        if (value == null) {
            throw new NullPointerException();
        }
        String splitted = splitByLetter(value);
        data.putIfAbsent(splitted, new ArrayList<>());
        data.get(splitted).add(value);
        size++;
        return true;
    }

    /**
     * Проверяем, хранится ли такая строка уже у нас
     *
     * @param value - передаваемая строка
     * @return - есть такая строка или нет в нашей структуре
     * <p>
     * Сложность - O(n) в худшем случае, если у нас все слова из одних и тех же букв
     */
    public boolean contains(String value) {
        if (value == null) {
            return false;
        }
        String splitted = splitByLetter(value);
        if (!data.containsKey(splitted)) {
            return false;
        }
        return data.get(splitted).contains(value);
    }

    /**
     * Удаляем сохраненную строку если она есть
     *
     * @param value - какую строку мы хотим удалить
     * @return - true если удалили, false - если такой строки нет
     * <p>
     * Сложность - O(n) в худшем случае, если у нас все слова из одних и тех же букв
     */
    public boolean remove(String value) {
        if (value == null) {
            return false;
        }
        String splitted = splitByLetter(value);
        if (!data.containsKey(splitted)) {
            return false;
        }
        if (data.get(splitted).remove(value)) {
            size--;
            return true;
        }
        return false;
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
     * Сложность - O(1)
     */
    public List<String> getSimilarWords(String value) {
        if (value == null) {
            throw new NullPointerException();
        }
        return data.getOrDefault(splitByLetter(value), new ArrayList<>());
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
     * Вспомогательный метод
     * <p>
     * Used in: add(), getSimilarWords(), remove(), contains()
     *
     * @param value - разбиваемая на уникальные символы строка
     * @return String букв из которых состоит слово
     * <p>
     * Сложность - O(n), n - длинна строки
     * Память - O(k), k - количество уникальных символов
     */
    private String splitByLetter(String value) {
        String loweredCased = value.toLowerCase();
        StringBuilder splitted = new StringBuilder();
        SortedSet<Character> chars = new TreeSet<>();
        for (int i = 0; i < loweredCased.length(); i++) {
            chars.add(loweredCased.charAt(i));
        }
        for (Character letter : chars) {
            splitted.append(letter);
        }
        return splitted.toString();
    }

}
