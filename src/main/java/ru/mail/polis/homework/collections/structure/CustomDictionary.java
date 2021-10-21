package ru.mail.polis.homework.collections.structure;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.beans.binding.StringBinding;

/**
 * Задание оценивается в 4 балла.
 * Необходимо реализовать класс которы умеет хранить строки и возвращать
 * список строк состоящий из того же набора буков, что ему передали строку.
 * Напишите какая сложность операций у вас получилась для каждого метода.
 */
public class CustomDictionary {
    private ArrayList<String> list;

    public CustomDictionary() {
        this.list = new ArrayList<>();
    }

    /**
     * Сохранить строку в структуру данных
     * @param value - передаваемая строка
     * @return - успешно сохранили строку или нет.
     *
     * Сложность - [O(1)]
     */
    public boolean add(String value) {
        return value != null && list.add(value);
    }

    /**
     * Проверяем, хранится ли такая строка уже у нас
     * @param value - передаваемая строка
     * @return - есть такая строка или нет в нашей структуре
     *
     * Сложность - [O(n)]
     */
    public boolean contains(String value) {
        return list.contains(value);
    }

    /**
     * Удаляем сохраненную строку если она есть
     * @param value - какую строку мы хотим удалить
     * @return - true если удалили, false - если такой строки нет
     *
     * Сложность - [O(n)]
     */
    public boolean remove(String value) {
        return list.remove(value);
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
     * Сложность - [O(n*m) или примерно O(max(n,m)^2), n - размер словаря, m - длина шаблона]
     */
    public List<String> getSimilarWords(String value) {
        ArrayList<String> res = new ArrayList<>();
        if (value == null || value.equals("")) {
            return res;
        }
        for (String s : list) {
            StringBuilder tmp = new StringBuilder(s.toLowerCase());
            for (int j = 0; j < value.length(); j++) {
                int index = tmp.indexOf(String.valueOf(Character.toLowerCase(value.charAt(j))));
                if (index < 0) {
                    break;
                }
                tmp.deleteCharAt(index);
            }
            if (tmp.length() == 0) {
                res.add(s);
            }
        }
        return res;
    }

    /**
     * Колл-во хранимых строк.
     * @return - Колл-во хранимых строк.
     *
     * Сложность - [O(1)]
     */
    public int size() {
        return list.size();
    }
}
