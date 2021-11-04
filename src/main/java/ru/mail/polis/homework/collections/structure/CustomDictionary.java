package ru.mail.polis.homework.collections.structure;

import javax.print.attribute.HashAttributeSet;
import java.util.*;

/**
 * Задание оценивается в 4 балла.
 * Необходимо реализовать класс которы умеет хранить строки и возвращать
 * список строк состоящий из того же набора буков, что ему передали строку.
 * Напишите какая сложность операций у вас получилась для каждого метода.
 */
public class CustomDictionary {

    private HashMap<String, LinkedList<String>> map = new HashMap<>();
    private int size = 0;

    /**
     * Сохранить строку в структуру данных
     *
     * @param value - передаваемая строка
     * @return - успешно сохранили строку или нет.
     * <p>
     * Сложность - [O(mlogm) +O(1) в среднем или O(n) в худшем случае)] n - число элементов в словаре, m - длина добавляемой строки
     */
    public boolean add(String value) {
        if (value == null || value.equals(""))
            throw new IllegalArgumentException("trying to add null/empty string");

        if (contains(value))
            return false;

        String key = createKey(value);

        if (map.containsKey(key)) {
            map.get(key).add(value);
        } else {
            LinkedList<String> lst = new LinkedList<>();
            lst.add(value);
            map.put(key, lst);
        }
        size++;
        return true;
    }

    /**
     * Проверяем, хранится ли такая строка уже у нас
     *
     * @param value - передаваемая строка
     * @return - есть такая строка или нет в нашей структуре
     * <p>
     * Сложность - [O(mlogm) +O(1) в среднем или O(n) в худшем случае)] n - число элементов в словаре, m - длина искомой строки
     */
    public boolean contains(String value) {
        String key = createKey(value);
        LinkedList<String> lst = map.get(key);
        if (lst != null) {
            return lst.contains(value);
        }
        return false;
    }

    /**
     * Удаляем сохраненную строку если она есть
     *
     * @param value - какую строку мы хотим удалить
     * @return - true если удалили, false - если такой строки нет
     * <p>
     * Сложность - [O(mlogm) +O(1) в среднем или O(n) в худшем случае)] n - число элементов в словаре, m - длина удаляемой строки
     */
    public boolean remove(String value) {
        if (value == null || value.equals(""))
            return false;

        String key = createKey(value);

        if (map.containsKey(key)) {
            if (map.get(key).remove(value))
                size--;
            else
                return false;
            if (map.get(key).size() == 0)
                map.remove(key);
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
     * Сложность - [O(mlogm)] m - длина ключа
     */
    public List<String> getSimilarWords(String value) {
        String key = createKey(value);
        List<String> result = map.get(key);
        if (result != null)
            return result;
        return Collections.emptyList();
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

    String createKey(String value) {
        char[] arr = value.toLowerCase().toCharArray();
        Arrays.sort(arr);
        return Arrays.toString(arr);
    }
}
