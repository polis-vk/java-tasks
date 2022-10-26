package ru.mail.polis.homework.collections.structure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Задание оценивается в 4 тугрика.
 * Необходимо реализовать класс которы умеет хранить строки и возвращать
 * список строк состоящий из того же набора буков, что ему передали строку.
 * Напишите какая сложность операций у вас получилась для каждого метода.
 */
public class CustomDictionary {
    private Map<String, Integer> strings = new HashMap<>();
    private int size = 0;

    private int getLetterSet(char[] letters) {
        int set = 0;
        char buf;
        for (char c : letters) {
            buf = c;
            //Переводим букву верхнего регистра в нижний
            if (buf >= 97) {
                buf -= 32;
            }
            //Переводим номер буквы по ASCII к номеру по порядку в алфавите
            buf -= 65;
            //Заполняем соответсвующий номеру по порядку в алфавите бит в числе
            set = set | (1 << buf);
        }
        return set;
    }

    /**
     * Сохранить строку в структуру данных
     *
     * @param value - передаваемая строка
     * @return - успешно сохранили строку или нет.
     * <p>
     * Сложность - [O(l)], где l длина строки
     */
    public boolean add(String value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException();
        }
        if (strings.put(value, getLetterSet(value.toCharArray())) != null) {
            return false;
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
     * Сложность - [O(1)]
     */
    public boolean contains(String value) {
        return strings.containsKey(value);
    }

    /**
     * Удаляем сохраненную строку если она есть
     *
     * @param value - какую строку мы хотим удалить
     * @return - true если удалили, false - если такой строки нет
     * <p>
     * Сложность - [O(1)]
     */
    public boolean remove(String value) {
        if (strings.remove(value) != null) {
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
     * Сложность - [O(n)]
     */
    public List<String> getSimilarWords(String value) {
        int set = getLetterSet(value.toCharArray());
        List<String> res = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : strings.entrySet()) {
            if (entry.getValue() == set && value.length() == entry.getKey().length()) {
                res.add(entry.getKey());
            }
        }
        return res;
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
