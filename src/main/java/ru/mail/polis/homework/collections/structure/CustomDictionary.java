package ru.mail.polis.homework.collections.structure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * Задание оценивается в 4 тугрика.
 * Необходимо реализовать класс которы умеет хранить строки и возвращать
 * список строк состоящий из того же набора буков, что ему передали строку.
 * Напишите какая сложность операций у вас получилась для каждого метода.
 */
public class CustomDictionary {

    private final List<String> store = new ArrayList<>();

    /**
     * Сохранить строку в структуру данных
     * @param value - передаваемая строка
     * @return - успешно сохранили строку или нет.
     *
     * Сложность - [O(1)]
     */
    public boolean add(String value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException();
        }
        if (store.contains(value)) {
            return false;
        }
        return store.add(value);
    }

    /**
     * Проверяем, хранится ли такая строка уже у нас
     * @param value - передаваемая строка
     * @return - есть такая строка или нет в нашей структуре
     *
     * Сложность - [O(n)]
     */
    public boolean contains(String value) {
        return store.contains(value);
    }

    /**
     * Удаляем сохраненную строку если она есть
     * @param value - какую строку мы хотим удалить
     * @return - true если удалили, false - если такой строки нет
     *
     * Сложность - [O(n)]
     */
    public boolean remove(String value) {
        return store.remove(value);
    }

    /**
     * Возвращает список из сохраненных ранее строк, которые состоят
     * из того же набора букв, что нам передали строку.
     * Примеры:
     * сохраняем строки ["aaa", "aBa", "baa", "aaB"]
     * При поиске по строке "AAb" нам должен вернуться следующий
     * список: ["aBa","baa","aaB"]
     *
     * Сохраняем строки ["aaa", "aAa", "a"]
     * поиск "aaaa"
     * результат: []
     * Как можно заметить - регистр строки не должен влиять на поиск, при этом
     * возвращаемые строки хранятся в том виде, что нам передали изначально.
     *
     * @return - список слов которые состоят из тех же букв, что и передаваемая
     * строка.
     *
     * Сложность - [O(n * k), где k - количество букв в слове, n - размер массива]
     */
    public List<String> getSimilarWords(String value) {

        char[] letters = value.toLowerCase().toCharArray();
        Arrays.sort(letters);
        String currentElement;
        List<String> result = new ArrayList<>();

        for (String element : store) {
            if (letters.length != element.length()) {
                continue;
            }

            currentElement = element;
            char[] elementChars = element.toLowerCase().toCharArray();
            Arrays.sort(elementChars);
            if (Arrays.equals(elementChars, letters)) {
                result.add(currentElement);
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
        return store.size();
    }


}
