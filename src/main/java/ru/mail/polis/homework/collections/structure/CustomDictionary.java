package ru.mail.polis.homework.collections.structure;

import java.util.*;

/**
 * Задание оценивается в 4 тугрика.
 * Необходимо реализовать класс которы умеет хранить строки и возвращать
 * список строк состоящий из того же набора буков, что ему передали строку.
 * Напишите какая сложность операций у вас получилась для каждого метода.
 */
public class CustomDictionary {
    private Set<String> set;

    public CustomDictionary() {
        set = new LinkedHashSet<>();
    }

    /**
     * Сохранить строку в структуру данных
     *
     * @param value - передаваемая строка
     * @return - успешно сохранили строку или нет.
     * <p>
     * Сложность - [O(1)]
     */
    public boolean add(String value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException();
        }
        return set.add(value);
    }

    /**
     * Проверяем, хранится ли такая строка уже у нас
     *
     * @param value - передаваемая строка
     * @return - есть такая строка или нет в нашей структуре
     * <p>
     * Сложность - [O(1)]
     */
    public boolean contains(String value) {
        return set.contains(value);
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
        return set.remove(value);
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
     * n - размер set, l - длина строки value
     * Сложность - [l + n * l]
     */
    public List<String> getSimilarWords(String value) {
        List<String> words = new ArrayList<>();
        HashMap<Character, Integer> mapForValue = countLetters(value);
        for (String str : set) {
            HashMap<Character, Integer> mapForStringInSet = countLetters(str);
            if (mapForValue.equals(mapForStringInSet)) {
                words.add(str);
            }
        }
        return words;
    }

    /**
     * Колл-во хранимых строк.
     *
     * @return - Колл-во хранимых строк.
     * <p>
     * Сложность - [O(1)]
     */
    public int size() {
        return set.size();
    }

    private HashMap<Character, Integer> countLetters(String str) {
        HashMap<Character, Integer> map = new HashMap<>();
        String strInLowerCase = str.toLowerCase();
        for (int i = 0; i < strInLowerCase.length(); i++) {
            char charInValue = strInLowerCase.charAt(i);
            if (!map.containsKey(charInValue)) {
                map.put(charInValue, 1);
            } else {
                int counterChar = map.get(charInValue) + 1;
                map.remove(charInValue);
                map.put(charInValue, counterChar);
            }
        }
        return map;
    }

}
