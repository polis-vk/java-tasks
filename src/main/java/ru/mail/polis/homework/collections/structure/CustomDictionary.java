package ru.mail.polis.homework.collections.structure;

import java.util.*;

/**
 * Задание оценивается в 4 тугрика.
 * Необходимо реализовать класс которы умеет хранить строки и возвращать
 * список строк состоящий из того же набора буков, что ему передали строку.
 * Напишите какая сложность операций у вас получилась для каждого метода.
 */
public class CustomDictionary {
    Set<List<Character>> set;
    CustomDictionary()
    {
        set = new HashSet<>();
    }

    public List<Character> modification(String value)
    {
        value = value.toLowerCase();
        LinkedList<Character> list = new LinkedList<>();
        for(int i = 0; i < value.length(); ++i)  list.add(value.charAt(i));
        Collections.sort(list);
        return list;
    }

    /**
     * Сохранить строку в структуру данных
     * @param value - передаваемая строка
     * @return - успешно сохранили строку или нет.
     *
     * Сложность - []
     */
    public boolean add(String value) {
        if(value == null || value.isEmpty()) throw new IllegalArgumentException();
        int curr = set.size();
        List<Character> list = new LinkedList<>();
        for(int i = 0; i < value.length(); ++i)  list.add(value.charAt(i));
        set.add(list);
        if(curr != set.size()) return true;
        return false;
    }

    /**
     * Проверяем, хранится ли такая строка уже у нас
     * @param value - передаваемая строка
     * @return - есть такая строка или нет в нашей структуре
     *
     * Сложность - []
     */
    public boolean contains(String value) {
        if(value == null || value.equals("")) return false;
        List<Character> list = new LinkedList<>();
        for(int i = 0; i < value.length(); ++i)  list.add(value.charAt(i));
        if(set.contains(list)) return true;
        return false;
    }

    /**
     * Удаляем сохраненную строку если она есть
     * @param value - какую строку мы хотим удалить
     * @return - true если удалили, false - если такой строки нет
     *
     * Сложность - []
     */
    public boolean remove(String value) {
        if(value == null || value.equals("")) return false;
        int curr = set.size();
        List<Character> list = new LinkedList<>();
        for(int i = 0; i < value.length(); ++i)  list.add(value.charAt(i));
        if(set.contains(list)) set.remove(list);
        if(set.size() < curr) return true;
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
     * Сложность - []
     */

    public static String toString(List<?> list)
    {
        StringBuilder stringBuilder = new StringBuilder();
        for(var a : list) stringBuilder.append(a);
        return stringBuilder.toString();
    }

    public List<String> getSimilarWords(String value) {
        List<String> ans = new LinkedList<>();
        String temp = value;
        String match = toString(modification(temp));
        for(var a : set)
        {
            String original = toString(a);
            String tmp = toString(modification(original));
            if(tmp.equals(match))  ans.add(original);
        }
        return ans;
    }

    /**
     * Колл-во хранимых строк.
     * @return - Колл-во хранимых строк.
     *
     * Сложность - []
     */
    public int size() {
        return set.size();
    }


}
