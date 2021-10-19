package ru.mail.polis.homework.collections.structure;

import java.util.*;

/**
 * Задание оценивается в 4 балла.
 * Необходимо реализовать класс которы умеет хранить строки и возвращать
 * список строк состоящий из того же набора буков, что ему передали строку.
 * Напишите какая сложность операций у вас получилась для каждого метода.
 */
public class CustomDictionary {

    private final HashMap<Set<Character>, LinkedList<String>> dictionary;
    private int size;

    public CustomDictionary() {
        dictionary = new HashMap<>();
        size = 0;
    }

    /**
     * Сохранить строку в структуру данных
     * @param value - передаваемая строка
     * @return - успешно сохранили строку или нет.
     *
     * Сложность - [size(словаря) + length(слова), но если у нас хорошее распределение то length(слова)]
     */
    public boolean add(String value) {
        Set<Character> key = getSetFromString(value);
        LinkedList<String> stringList = dictionary.getOrDefault(key, new LinkedList<>());
        if (stringList.contains(value)) {
            return false;
        }
        stringList.add(value);
        dictionary.put(key, stringList);
        size++;
        return true;
    }

    /**
     * Проверяем, хранится ли такая строка уже у нас
     * @param value - передаваемая строка
     * @return - есть такая строка или нет в нашей структуре
     *
     * Сложность - [size(словаря) + length(слова), но если у нас хорошее распределение то length(слова)]
     */
    public boolean contains(String value) {
        Set<Character> key = getSetFromString(value);
        LinkedList<String> stringList = dictionary.getOrDefault(key, null);
        if (stringList == null) {
            return false;
        }

        for (String s : stringList) {
            if (value.equals(s)) {
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
     * Сложность - [size(словаря) + length(слова), но если у нас хорошее распределение то length(слова)]
     */
    public boolean remove(String value) {
        Set<Character> key = getSetFromString(value);
        LinkedList<String> stringList = dictionary.getOrDefault(key, null);
        if (stringList == null) {
            return false;
        }

        size--;
        return stringList.remove(value);
    }

    /**
     * Возвращает список из сохраненных ранее строк, которые состоят
     * из тех же букв что нам передали строку.
     * Примеры: сохраняем строки ["aaa", "aBa", "baa", "aaB"]
     *          При поиске по строке "AAb" нам должен вернуться следующий
     *          список: ["aBa","baa","aaB"]
     *
     * Как можно заметить - регистр строки не должен влиять на поиск, при этом
     * возвращаемые строки хранятся в том виде что нам передали изначально.
     *
     * @return - список слов которые состоят из тех же букв, что и передаваемая
     * строка.
     *
     * Сложность - [1]
     */
    public List<String> getSimilarWords(String value) {
        Set<Character> key = getSetFromString(value);
        return dictionary.getOrDefault(key, null);
    }

    /**
     * Колл-во хранимых строк.
     * @return - Колл-во хранимых строк.
     *
     * Сложность - [1]
     */
    public int size() {
        return size;
    }

    private static Set<Character> getSetFromString(String value) {
        Set<Character> set = new HashSet<>();
        for(char c : value.toCharArray()) {
            set.add(Character.toLowerCase(c));
        }
        return set;
    }

}
