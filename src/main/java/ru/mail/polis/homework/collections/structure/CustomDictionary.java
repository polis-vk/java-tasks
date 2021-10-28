package ru.mail.polis.homework.collections.structure;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
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
     *
     * @param value - передаваемая строка
     * @return - успешно сохранили строку или нет.
     * <p>
     * Сложность - O(k), k - длинна строки value
     */
    public boolean add(String value) {
        if (value == null || value.equals("")) {
            throw new IllegalArgumentException();
        }
        return dictionary.add(value);
    }

    /**
     * Проверяем, хранится ли такая строка уже у нас
     *
     * @param value - передаваемая строка
     * @return - есть такая строка или нет в нашей структуре
     * <p>
     * Сложность - O(k), k - длинна строки value
     */
    public boolean contains(String value) {
        return dictionary.contains(value);
    }

    /**
     * Удаляем сохраненную строку если она есть
     *
     * @param value - какую строку мы хотим удалить
     * @return - true если удалили, false - если такой строки нет
     * <p>
     * Сложность - O(k), k - длинна строки value
     */
    public boolean remove(String value) {
        return dictionary.remove(value);
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
     * Сложность - O(n * k * log(k)), n - размер словаря, k - длина строки value
     */

    /*
     Сложность можно улучшить, пожертвовав памятью и сложностью других методов (например добавляя сохранять
     отсортированную строку нижнего регистра и держать стоки в HashMap). Но выбор подхода зависит от использования
     сервиса, а конкретно от частоты вызывания его различных методов. Поэтому на мое усмотрение был выбран данный
     вариант, не использующий доп. память.
    */
    public List<String> getSimilarWords(String value) {
        char[] modifyCharValue = getSortedLowerCharArray(value);
        LinkedList<String> result = new LinkedList<>();
        mainLoop:
        for (String elementOfDictionary : dictionary) {
            if (elementOfDictionary.length() != modifyCharValue.length) {
                continue;
            }
            char[] modifyElement = getSortedLowerCharArray(elementOfDictionary);
            for (int i = 0; i < modifyElement.length; i++) {
                if (modifyCharValue[i] != modifyElement[i]) {
                    continue mainLoop;
                }
            }
            result.add(elementOfDictionary);
        }
        return result;
    }

    /**
     * Колл-во хранимых строк.
     *
     * @return - Колл-во хранимых строк.
     * <p>
     * Сложность - O(1)
     */
    public int size() {
        return dictionary.size();
    }

    /**
     * @param value - строка для изменения
     * @return - отсортированный массив символов состоящий из символов нижнего регистра строки value
     * Сложность - O(k * log(k)), k - длинна строки value
     */
    private char[] getSortedLowerCharArray(String value) {
        char[] charValue = value.toLowerCase().toCharArray();
        Arrays.sort(charValue);
        return charValue;
    }

}
