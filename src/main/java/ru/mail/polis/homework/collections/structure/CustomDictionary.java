package ru.mail.polis.homework.collections.structure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Задание оценивается в 4 тугрика.
 * Необходимо реализовать класс которы умеет хранить строки и возвращать
 * список строк состоящий из того же набора буков, что ему передали строку.
 * Напишите какая сложность операций у вас получилась для каждого метода.
 */
public class CustomDictionary {
    private final Map<String, Character[]> dictionary = new HashMap<>();

    /**
     * Сохранить строку в структуру данных
     * @param value - передаваемая строка
     * @return - успешно сохранили строку или нет.
     *
     * Сложность - [O(1), O(n) - в худшем случае]
     */
    public boolean add(String value) {
        nullOrEmptyString(value);
        if (this.contains(value)) {
            return false;
        }
        dictionary.put(value, toCharSortedArr(value));
        return true;
    }

    /**
     * Проверяем, хранится ли такая строка уже у нас
     * @param value - передаваемая строка
     * @return - есть такая строка или нет в нашей структуре
     *
     * Сложность - [O(1), O(n) - в худшем случае]
     */
    public boolean contains(String value) {
        return dictionary.containsKey(value);
    }

    /**
     * Удаляем сохраненную строку если она есть
     * @param value - какую строку мы хотим удалить
     * @return - true если удалили, false - если такой строки нет
     *
     * Сложность - [O(1)]
     */
    public boolean remove(String value) {
        nullOrEmptyString(value);
        if (this.contains(value)) {
            dictionary.remove(value);
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
     *
     * сохраняем строки ["aaa", "aAa", "a"]
     * поиск "aaaa"
     * результат: []
     * Как можно заметить - регистр строки не должен влиять на поиск, при этом
     * возвращаемые строки хранятся в том виде что нам передали изначально.
     *
     * @return - список слов которые состоят из тех же букв, что и передаваемая
     * строка.
     *
     * Сложность - [O(n^2)]
     */
    public List<String> getSimilarWords(String value) {
        nullOrEmptyString(value);
        List<String> similarWords = new ArrayList<>();
        Character[] valCharArr = toCharSortedArr(value.toLowerCase(Locale.ROOT));
        boolean isSimilar = true;
        for (Map.Entry<String, Character[]> entry : dictionary.entrySet()) {
            Character[] entryValCharArr = entry.getValue().clone();
            if (valCharArr.length > entryValCharArr.length) {
                continue;
            }
            for (int i = 0; i < valCharArr.length; i++) {
                if (!valCharArr[i].equals(entryValCharArr[i])) {
                    isSimilar = false;
                    break;
                }
            }
            if (isSimilar) {
                similarWords.add(entry.getKey());
            }
            isSimilar = true;
        }
        return similarWords;
    }

    /**
     * Колл-во хранимых строк.
     * @return - Колл-во хранимых строк.
     *
     * Сложность - [O(1)]
     */
    public int size() {
        return dictionary.size();
    }

    private void nullOrEmptyString(String value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException();
        }
    }

    private Character[] toCharSortedArr(String str) {
        int len = str.length();
        Character[] array = new Character[len];
        for (int i = 0; i < len; i++) {
            array[i] = Character.toLowerCase(str.charAt(i));
        }
        Arrays.sort(array);
        return array;
    }
}
