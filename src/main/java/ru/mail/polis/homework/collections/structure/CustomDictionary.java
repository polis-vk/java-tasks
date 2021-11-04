package ru.mail.polis.homework.collections.structure;

import java.util.*;

/**
 * Задание оценивается в 4 балла.
 * Необходимо реализовать класс которы умеет хранить строки и возвращать
 * список строк состоящий из того же набора буков, что ему передали строку.
 * Напишите какая сложность операций у вас получилась для каждого метода.
 */
public class CustomDictionary {
    private final Map<Map<Character, Integer>, Set<String>> customDictionary = new HashMap<>();
    private int dictSize = 0;

    /**
     * Сохранить строку в структуру данных
     *
     * @param value - передаваемая строка
     * @return - успешно сохранили строку или нет.
     * <p>
     * Сложность - [O(m), где m - длина переданной строки]
     */
    public boolean add(String value) {
        if (value == null || value.equals("")) {
            throw new IllegalArgumentException();
        }
        Map<Character, Integer> valueMap = newHashMap(value);
        Set<String> allCollectedStrings = customDictionary.get(valueMap);
        if (allCollectedStrings != null) {
            if (allCollectedStrings.add(value)) {
                dictSize++;
                return true;
            }
        } else {
            allCollectedStrings = new HashSet<>();
            allCollectedStrings.add(value);
            customDictionary.put(valueMap, allCollectedStrings);
            dictSize++;
            return true;
        }
        return false;
    }

    /**
     * Проверяем, хранится ли такая строка уже у нас
     *
     * @param value - передаваемая строка
     * @return - есть такая строка или нет в нашей структуре
     * <p>
     * Сложность - [O(m)]
     */
    public boolean contains(String value) {
        Set<String> allCollectedStrings = customDictionary.get(newHashMap(value));
        if (allCollectedStrings != null) {
            return allCollectedStrings.contains(value);
        }
        return false;
    }

    /**
     * Удаляем сохраненную строку если она есть
     *
     * @param value - какую строку мы хотим удалить
     * @return - true если удалили, false - если такой строки нет
     * <p>
     * Сложность - [O(m)]
     */
    public boolean remove(String value) {
        Map<Character, Integer> valueMap = newHashMap(value);
        Set<String> allCollectedStrings = customDictionary.get(valueMap);
        if (allCollectedStrings != null) {
            if (allCollectedStrings.remove(value)) {
                if (allCollectedStrings.isEmpty()) {
                    customDictionary.remove(valueMap);
                }
                dictSize--;
                return true;
            }
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
     * Сложность - [O(m)]
     */
    public List<String> getSimilarWords(String value) {
        Set<String> allCollectedStrings = customDictionary.get(newHashMap(value));
        if (allCollectedStrings != null) {
            return new LinkedList<>(allCollectedStrings);
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
        return dictSize;
    }

    private HashMap<Character, Integer> newHashMap(String value) {
        HashMap<Character, Integer> allChars = new HashMap<>();
        for (int i = 0; i < value.length(); i++) {
            char c = Character.toLowerCase(value.charAt(i));
            allChars.put(c, allChars.getOrDefault(c, 0) + 1);
        }
        return allChars;
    }
}
