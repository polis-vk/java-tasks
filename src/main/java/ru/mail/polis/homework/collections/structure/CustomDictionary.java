package ru.mail.polis.homework.collections.structure;

import java.util.*;

/**
 * Задание оценивается в 4 тугрика.
 * Необходимо реализовать класс которы умеет хранить строки и возвращать
 * список строк состоящий из того же набора буков, что ему передали строку.
 * Напишите какая сложность операций у вас получилась для каждого метода.
 */
public class CustomDictionary {
    private Map<String, Map<Character, Integer>> dictionary;
    private int size = 0;

    public CustomDictionary() {
        this.dictionary = new HashMap<>();
    }

    //Сложность - [O(2 * value.length())]
    public Map<Character, Integer> getMapValueInChars(String value) {
        Map<Character, Integer> valueToChars = new HashMap<>();
        char[] arrayValue = value.toLowerCase().toCharArray();
        for (int i = 0; i < value.length(); i++) {
            valueToChars.merge(arrayValue[i], 1, Integer::sum);
        }
        return valueToChars;
    }

    public void checkValue(String value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException();
        }
    }


    /**
     * Сохранить строку в структуру данных
     *
     * @param value - передаваемая строка
     * @return - успешно сохранили строку или нет.
     * <p>
     * Сложность - [O(2 * value.length())]
     */
    public boolean add(String value) {
        checkValue(value);
        if (contains(value)) {
            return false;
        }
        dictionary.put(value, getMapValueInChars(value));
        size++;
        return true;
    }

    /**
     * Проверяем, хранится ли такая строка уже у нас
     *
     * @param value - передаваемая строка
     * @return - есть такая строка или нет в нашей структуре
     * <p>
     * Сложность - [O(dictionary.size())]
     */
    public boolean contains(String value) {
        checkValue(value);
        return dictionary.containsKey(value);
    }

    /**
     * Удаляем сохраненную строку если она есть
     *
     * @param value - какую строку мы хотим удалить
     * @return - true если удалили, false - если такой строки нет
     * <p>
     * Сложность - [O(dictionary.size())]
     */
    public boolean remove(String value) {
        checkValue(value);
        if (contains(value)) {
            dictionary.remove(value);
            size--;
            return true;
        }
        return false;
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
     * Сложность - [O(dictionary.size() * 2 * value.length())]
     */
    public List<String> getSimilarWords(String value) {
        checkValue(value);
        ArrayList<String> similarWordsList = new ArrayList<>();
        Map<Character, Integer> mapValueInChars = getMapValueInChars(value);
        for (Map.Entry valueInDictionary : dictionary.entrySet()) {
            if (valueInDictionary.getValue().equals(mapValueInChars)) {
                similarWordsList.add((String) valueInDictionary.getKey());
            }
        }
        return similarWordsList;
    }

    /**
     * Колл-во хранимых строк.
     *
     * @return - Колл-во хранимых строк.
     * <p>
     * Сложность - [O(1)]
     */
    public int size() {
        return size;
    }
}
