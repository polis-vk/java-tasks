package ru.mail.polis.homework.collections.structure;

import java.util.*;

/**
 * Задание оценивается в 4 балла.
 * Необходимо реализовать класс которы умеет хранить строки и возвращать
 * список строк состоящий из того же набора букв, что ему передали строку.
 * Напишите какая сложность операций у вас получилась для каждого метода.
 */
public class CustomDictionary {

    private final Map<String, Set<String>> map = new HashMap<>();
    private int size;

    /**
     * Сохранить строку в структуру данных
     * @param value - передаваемая строка
     * @return - успешно сохранили строку или нет.
     *
     * Сложность - [O(log(n) + m*log(m))]
     */
    public boolean add(String value) {
        String key = makeKey(value);
        boolean flag;
        if (map.containsKey(key)) {
             flag = map.get(key).add(value);
        } else {
            Set<String> set = new HashSet<>();
            set.add(value);
            map.put(key, set);
            flag = true;
        }
        if (flag) {
            size++;
        }
        return flag;
    }

    /**
     * Проверяем, хранится ли такая строка уже у нас
     * @param value - передаваемая строка
     * @return - есть такая строка или нет в нашей структуре
     *
     * Сложность - [O(log(n) + m*log(m))]
     */
    public boolean contains(String value) {
        return map.containsKey(makeKey(value));
    }

    /**
     * Удаляем сохраненную строку если она есть
     * @param value - какую строку мы хотим удалить
     * @return - true если удалили, false - если такой строки нет
     *
     * Сложность - [O(log(n) + m*log(m))]
     */
    public boolean remove(String value) {
        if (!contains(value)) {
            return false;
        }
        String key = makeKey(value);
        boolean flag = map.get(key).remove(value);
        if (flag) {
            if (map.get(key).isEmpty()) {
                map.remove(key);
            }
            size--;
        }
        return flag;
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
     * Сложность - [O(log(n) + m*log(m))]
     */
    public List<String> getSimilarWords(String value) {
        String key = makeKey(value);
        return (map.containsKey(key) ? new ArrayList<>(map.get(key)) : Collections.emptyList());
    }


    /**
     * Колл-во хранимых строк.
     * @return - Колл-во хранимых строк.
     *
     * Сложность - [O(1)]
     */
    public int size() {
        return size;
    }


    //Сложность - [O(m*log(m))]
    private String makeKey(String value) {
        if (value == null || value.equals("")) {
            throw new IllegalArgumentException();
        }
        String word = value.toLowerCase();
        char[] temp = word.toCharArray();
        Arrays.sort(temp);
        return new String(temp);
    }
}
