package ru.mail.polis.homework.collections.structure;

import java.util.*;

/**
 * Задание оценивается в 4 балла.
 * Необходимо реализовать класс которы умеет хранить строки и возвращать
 * список строк состоящий из того же набора буков, что ему передали строку.
 * Напишите какая сложность операций у вас получилась для каждого метода.
 */
public class CustomDictionary {

    private final Map<String, List<String>> dictionary = new HashMap<>();
    private int size;

    /**
     * Сохранить строку в структуру данных
     *
     * @param value - передаваемая строка
     * @return - успешно сохранили строку или нет.
     * <p>
     * Сложность - [O(m log(m) + v], где m - длина передаваемой строки, v - количество элементов в значении ключа (в списке), элемент по ключу берется за O(1) при грамотной реализации equals и hashCode
     */
    public boolean add(String value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException();
        }
        String transformedValue = valueTransform(value);
        List<String> valueList = dictionary.get(valueTransform(value));

        if (valueList != null) {
            if (valueList.contains(value)) {
                return false;
            }
            valueList.add(value);
        } else {
            ArrayList<String> listOfWords = new ArrayList<>();
            listOfWords.add(value);
            dictionary.put(transformedValue, listOfWords);
        }
        size++;
        return true;
    }

    /**
     * Проверяем, хранится ли такая строка уже у нас
     *
     * @param value - передаваемая строка
     * @return - есть такая строка или нет в нашей структуре
     * <p>
     * Сложность - [O(m log(m) + v]
     */
    public boolean contains(String value) {
        List<String> valueList = dictionary.get(valueTransform(value));
        if (valueList != null) {
            return valueList.contains(value);
        }
        return false;
    }

    /**
     * Удаляем сохраненную строку если она есть
     *
     * @param value - какую строку мы хотим удалить
     * @return - true если удалили, false - если такой строки нет
     * <p>
     * Сложность - [O(m log(m) + v]
     */
    public boolean remove(String value) {
        if (contains(value)) {
            dictionary.get(valueTransform(value)).remove(value);
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
     * Сложность - [O(m log(m)]
     */
    public List<String> getSimilarWords(String value) {
        List<String> valueList = dictionary.get(valueTransform(value));
        if (valueList != null) {
            return valueList;
        }
        return Collections.emptyList();
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

    private String valueTransform(String value) {
        char[] valueCharArray = value.toLowerCase().toCharArray();
        Arrays.sort(valueCharArray);
        return Arrays.toString(valueCharArray);
    }
}
