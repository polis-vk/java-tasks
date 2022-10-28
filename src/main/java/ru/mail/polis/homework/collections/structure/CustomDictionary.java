package ru.mail.polis.homework.collections.structure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Задание оценивается в 4 тугрика.
 * Необходимо реализовать класс которы умеет хранить строки и возвращать
 * список строк состоящий из того же набора буков, что ему передали строку.
 * Напишите какая сложность операций у вас получилась для каждого метода.
 */
public class CustomDictionary {
    private Map<List<Integer>, Set<String>> strings = new HashMap<>();
    private int size = 0;

    private List<Integer> getLetterSet(char[] letters) {
        int[] letterSet = new int[26];
        char index;
        for (char c : letters) {
            index = c;
            //Проверяем, то что является буквой
            if (c <= 65 || c >= 90) {
                if (c <= 97 || c >= 122) {
                    continue;
                }
            }
            //Переводим букву верхнего регистра в нижний
            if (index >= 97) {
                index -= 32;
            }
            //Переводим номер буквы по ASCII к номеру по порядку в алфавите
            index -= 65;
            //Увеличиваем на единицу кол-во текущей буквы
            letterSet[index]++;
        }
        return Arrays.stream(letterSet)
                .boxed()
                .collect(Collectors.toList());
    }

    /**
     * Сохранить строку в структуру данных
     *
     * @param value - передаваемая строка
     * @return - успешно сохранили строку или нет.
     * <p>
     * Сложность - [O(l)], где l - длина строки
     */
    public boolean add(String value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException();
        }
        if (contains(value)) {
            return false;
        }
        List<Integer> letterSet = getLetterSet(value.toCharArray());
        Set<String> stringsSet = strings.get(letterSet);
        if (stringsSet == null) {
            stringsSet = new HashSet<>();
        }
        stringsSet.add(value);
        strings.put(letterSet, stringsSet);
        size++;
        return true;
    }

    /**
     * Проверяем, хранится ли такая строка уже у нас
     *
     * @param value - передаваемая строка
     * @return - есть такая строка или нет в нашей структуре
     * <p>
     * Сложность - [O(l)], где l - длина строки
     */
    public boolean contains(String value) {
        List<Integer> letterSet = getLetterSet(value.toCharArray());
        Set<String> stringsSet = strings.get(letterSet);
        return stringsSet != null && stringsSet.contains(value);
    }

    /**
     * Удаляем сохраненную строку если она есть
     *
     * @param value - какую строку мы хотим удалить
     * @return - true если удалили, false - если такой строки нет
     * <p>
     * Сложность - [O(l)], где l - длина строки
     */
    public boolean remove(String value) {
        List<Integer> letterSet = getLetterSet(value.toCharArray());
        Set<String> stringsSet = strings.get(letterSet);
        if (stringsSet != null && stringsSet.remove(value)) {
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
     * Сложность - [O(l + n)], где l - длина строки, n - кол-во слов с таким же множеством букв
     */
    public List<String> getSimilarWords(String value) {
        Set<String> similarStrings = strings.get(getLetterSet(value.toCharArray()));
        if (similarStrings == null) {
            return new ArrayList<>();
        }
        return new ArrayList<String>() {{
            addAll(similarStrings);
        }};
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
}
