package ru.mail.polis.homework.collections.structure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Задание оценивается в 4 балла.
 * Необходимо реализовать класс которы умеет хранить строки и возвращать
 * список строк состоящий из того же набора буков, что ему передали строку.
 * Напишите какая сложность операций у вас получилась для каждого метода.
 */
public class CustomDictionary {
    private Set<String> data = new HashSet<>();

    /**
     * Сохранить строку в структуру данных
     * @param value - передаваемая строка
     * @return - успешно сохранили строку или нет.
     * <p>
     * Сложность - O(1)
     */
    public boolean add(String value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException();
        }
        return data.add(value);
    }

    /**
     * Проверяем, хранится ли такая строка уже у нас
     * @param value - передаваемая строка
     * @return - есть такая строка или нет в нашей структуре
     * <p>
     * Сложность - O(1)
     */
    public boolean contains(String value) {
        return data.contains(value);
    }

    /**
     * Удаляем сохраненную строку если она есть
     * @param value - какую строку мы хотим удалить
     * @return - true если удалили, false - если такой строки нет
     * <p>
     * Сложность - O(1)
     */
    public boolean remove(String value) {
        return data.remove(value);
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
     * <p>
     * Сложность - O(nm) * O(nlogn), n - кол-во символов в словаре, m - кол-во символов в value
     * O(nlogn) - сложность сортировки каждой строки в словаре
     */
    public List<String> getSimilarWords(String value) {
        if (value == null || value.isEmpty()) {
            return Collections.emptyList();
        }
        List<String> result = new ArrayList<>();
        char[] valueArray = getLowerCaseSortedCharArray(value);
        for (String current : data) {
            char[] currentStringArray = getLowerCaseSortedCharArray(current);
            if (currentStringArray.length != valueArray.length) {
                continue;
            }
            boolean match = true;
            for (int i = 0; i < valueArray.length; i++) {
                if (currentStringArray[i] != valueArray[i]) {
                    match = false;
                    break;
                }
            }
            if (match) {
                result.add(current);
            }
        }
        return result;
    }

    private char[] getLowerCaseSortedCharArray(String s) {
        char[] a = s.toLowerCase().toCharArray();
        Arrays.sort(a);
        return a;
    }

    /**
     * Колл-во хранимых строк.
     *
     * @return - Колл-во хранимых строк.
     * <p>
     * Сложность - O(1)
     */
    public int size() {
        return data.size();
    }


}
