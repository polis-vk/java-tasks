package ru.mail.polis.homework.collections.structure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * Задание оценивается в 4 балла.
 * Необходимо реализовать класс которы умеет хранить строки и возвращать
 * список строк состоящий из того же набора буков, что ему передали строку.
 * Напишите какая сложность операций у вас получилась для каждого метода.
 */
public class CustomDictionary {

    private final HashSet<String> dictionary = new HashSet<>();

    /**
     * Сохранить строку в структуру данных
     * @param value - передаваемая строка
     * @return - успешно сохранили строку или нет.
     *
     * Сложность - [O(1)]
     */
    public boolean add(String value) {
        if (value == null || value.equals("")) {
            throw new IllegalArgumentException();
        }
        return dictionary.add(value);
    }

    /**
     * Проверяем, хранится ли такая строка уже у нас
     * @param value - передаваемая строка
     * @return - есть такая строка или нет в нашей структуре
     *
     * Сложность - [O(1)]
     */
    public boolean contains(String value) {
        return dictionary.contains(value);//false;
    }

    /**
     * Удаляем сохраненную строку если она есть
     * @param value - какую строку мы хотим удалить
     * @return - true если удалили, false - если такой строки нет
     *
     * Сложность - [O(1)]
     */
    public boolean remove(String value) {
        return dictionary.remove(value);
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
     * Сложность - [O(n * m), n - количество элементов в словаре, m - длина value]
     */
    public List<String> getSimilarWords(String value) {
        int[] newValue = value.toLowerCase().codePoints().sorted().toArray();
        List<String> result = new ArrayList<>();
        for (String s : dictionary) {
            if (s.length() != newValue.length) {
                continue;
            }

            if (Arrays.equals(s.toLowerCase().codePoints().sorted().toArray(), newValue)) {
                result.add(s);
            }
        }
        return result;
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


}
