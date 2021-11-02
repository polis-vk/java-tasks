package ru.mail.polis.homework.collections.structure;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Задание оценивается в 4 балла.
 * Необходимо реализовать класс которы умеет хранить строки и возвращать
 * список строк состоящий из того же набора буков, что ему передали строку.
 * Напишите какая сложность операций у вас получилась для каждого метода.
 */
public class CustomDictionary {
    // какой-то лютый способ
    private Map<String, List<String>> dictionary = new HashMap<String, List<String>>();

    private int size = 0;
    
    /**
     * Сохранить строку в структуру данных
     * @param value - передаваемая строка
     * @return - успешно сохранили строку или нет.
     *
     * Сложность - O(k + m), 
     * k - количество элементов в словаре, имеющих тот же набор букв что и value,
     * m - количество символов в слове
     */
    public boolean add(String value) {
        if (value == null || value.length() == 0) {
            throw new IllegalArgumentException();
        }
        // преобразование регистра, копирование и сортировка массива - O(m)
        String charset = sortString(value);
        // O(1)
        List<String> list = this.dictionary.get(charset);
        if (list != null) {
            // O(k)
            if (list.contains(value)) {
                return false;
            }
        } else {
            list = new ArrayList<String>();
            // O(1)
            this.dictionary.put(charset, list);
        }
        // O(1) амортизированная (ArrayList)
        list.add(value);
        ++this.size;
        return true;
    }

    /**
     * Проверяем, хранится ли такая строка уже у нас
     * @param value - передаваемая строка
     * @return - есть такая строка или нет в нашей структуре
     *
     * Сложность - O(k + m), 
     * k - количество элементов в словаре, имеющих тот же набор букв что и value,
     * m - количество символов в слове
     */
    public boolean contains(String value) {
        if (value == null || value.length() == 0) {
            throw new IllegalArgumentException();
        }
        // O(1) на get() и O(m) на sortString(), т.е. O(m)
        List<String> list = this.dictionary.get(sortString(value));
        if (list != null) {
            // O(k) (ArrayList)
            return list.contains(value);
        }
        return false;
    }

    /**
     * Удаляем сохраненную строку если она есть
     * @param value - какую строку мы хотим удалить
     * @return - true если удалили, false - если такой строки нет
     *
     * Сложность - O(k + m), 
     * k - количество элементов в словаре, имеющих тот же набор букв что и value,
     * m - количество символов в слове
     */
    public boolean remove(String value) {
        if (value == null || value.length() == 0) {
            throw new IllegalArgumentException();
        }
        // O(1) на get() и O(m) на sortString(), т.е. O(m)
        List<String> list = this.dictionary.get(sortString(value));
        // O(k) (ArrayList)
        if (list == null || !list.remove(value)) {
            return false;
        }
        --this.size;
        return true;
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
     * Сложность - O(m),
     * m - количество символов в слове
     */
    public List<String> getSimilarWords(String value) {
        if (value == null || value.length() == 0) {
            throw new IllegalArgumentException();
        }
        // O(1) на get() и O(m) на sortString(), т.е. O(m)
        List<String> ret = this.dictionary.get(sortString(value));
        if (ret == null) {
            return Collections.emptyList();
        }
        return ret;
    }

    /**
     * Колл-во хранимых строк.
     * @return - Колл-во хранимых строк.
     *
     * Сложность - O(1)
     */
    public int size() {
        return this.size;
    }
    
    // O(m), m - длина слова
    private static String sortString(String toSort) {
        return String.valueOf(countSort(toSort.toLowerCase().toCharArray()));
    }

    // сортировка счетом за O(n), n - длина массива
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
