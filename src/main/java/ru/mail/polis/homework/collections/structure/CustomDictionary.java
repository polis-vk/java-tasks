package ru.mail.polis.homework.collections.structure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Задание оценивается в 4 балла.
 * Необходимо реализовать класс которы умеет хранить строки и возвращать
 * список строк состоящий из того же набора буков, что ему передали строку.
 * Напишите какая сложность операций у вас получилась для каждого метода.
 */
public class CustomDictionary {
    
    private HashMap<String, char[]> dictionary;
    
    public CustomDictionary() {
        // char[] т.к. приходится сортировать массив, и преобразование обратно
        // в String - лишний шаг
        this.dictionary = new HashMap<String, char[]>();
    }

    /**
     * Сохранить строку в структуру данных
     * @param value - передаваемая строка
     * @return - успешно сохранили строку или нет.
     *
     * Сложность - O(n)
     */
    public boolean add(String value) {
        if (value == null || value.length() == 0) {
            throw new IllegalArgumentException();
        }
        // проверка O(1)
        if (this.contains(value)) {
            return false;
        }
        // преобразование регистра, копирование и сортировка массива - O(n)
        // вставка O(1)
        this.dictionary.put(value, countSort(value.toLowerCase().toCharArray()));
        return true;
    }

    /**
     * Проверяем, хранится ли такая строка уже у нас
     * @param value - передаваемая строка
     * @return - есть такая строка или нет в нашей структуре
     *
     * Сложность - O(1)
     */
    public boolean contains(String value) {
        if (value == null || value.length() == 0) {
            throw new IllegalArgumentException();
        }
        return this.dictionary.containsKey(value);
    }

    /**
     * Удаляем сохраненную строку если она есть
     * @param value - какую строку мы хотим удалить
     * @return - true если удалили, false - если такой строки нет
     *
     * Сложность - O(1)
     */
    public boolean remove(String value) {
        if (value == null || value.length() == 0) {
            throw new IllegalArgumentException();
        }
        return this.dictionary.remove(value) != null;
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
     * Сложность - O(n * k), k - количество элементов в словаре, n - длина слова
     */
    public List<String> getSimilarWords(String value) {
        if (value == null || value.length() == 0) {
            throw new IllegalArgumentException();
        }
        // преобразование регистра, копирование и сортировка массива - O(n)
        char[] charset1 = countSort(value.toLowerCase().toCharArray());

        // неизвестно что нужно внешним методам, поэтому не LinkedList
        List<String> ret = new ArrayList();

        // k сравнений
        this.dictionary.forEach((String word, char[] charset2) -> {
            // от 1 (в случае несовпадения длин) до n итераций, O(n)
            if (Arrays.equals(charset1, charset2)) {
                ret.add(word); // вставка в список O(1)
            }
        }); // итогово O(k * n)

        // чтоб не возвращать пустые ArrayList'ы
        if (ret.size() == 0) {
            return Collections.emptyList();
        } else {
            return ret;
        }
    }

    /**
     * Колл-во хранимых строк.
     * @return - Колл-во хранимых строк.
     *
     * Сложность - []
     */
    public int size() {
        return this.dictionary.size();
    }

    // сортировка счетом за O(n)
    private static final char[] countSort(char[] array) {
        char min = array[0];
        char max = array[0];
        for (int i = 1; i < array.length; ++i) {
            if (array[i] < min) {
                min = array[i];
            }
            if (array[i] > max) {
                max = array[i];
            }
        }

        int[] count = new int[max - min + 1];

        for (int i = 0; i < array.length; ++i) {
            ++count[array[i] - min];
        }

        int idx = 0;
        for (int i = 0; i < count.length; ++i) {
            for (int j = 0; j < count[i]; ++j) {
                array[idx++] = (char) (i + min);
            }
        }
        return array;
    }
    
}
