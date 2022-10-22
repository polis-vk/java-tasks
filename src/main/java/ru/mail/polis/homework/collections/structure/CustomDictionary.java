package ru.mail.polis.homework.collections.structure;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Задание оценивается в 4 тугрика.
 * Необходимо реализовать класс которы умеет хранить строки и возвращать
 * список строк состоящий из того же набора буков, что ему передали строку.
 * Напишите какая сложность операций у вас получилась для каждого метода.
 */
public class CustomDictionary {

    private Map<Map<Character, Integer>, List< String > > storage;

    private int size;

    public CustomDictionary() {
        storage = new HashMap<>();
        size = 0;
    }

    /**
     * Сохранить строку в структуру данных
     *
     * @param value - передаваемая строка
     * @return - успешно сохранили строку или нет.
     * <p>
     * Сложность - [O(len(value) + containsKey]
     * Учитывая то, что в hashMap операция добавления за константу
     */
    public boolean add(String value) {
        if (!isValid(value)) {
            throw new IllegalArgumentException();
        }

        if (contains(value)) {
            return false;
        }

        Map<Character, Integer> lets = getLetsMap(value);

        if (!storage.containsKey(lets)) {
            storage.put(lets, new LinkedList<>());
        }

        storage.get(lets).add(value);
        size++;
        return true;
    }

    /**
     * Проверяем, хранится ли такая строка уже у нас
     *
     * @param value - передаваемая строка
     * @return - есть такая строка или нет в нашей структуре
     * <p>
     * Сложность - [O(len(value) + m]
     * m - количество слов в нашей мапе, которая соотвествует слову
     */
    public boolean contains(String value) {
        return removeOrCheck(value, false);
    }

    /**
     * Удаляем сохраненную строку если она есть
     *
     * @param value - какую строку мы хотим удалить
     * @return - true если удалили, false - если такой строки нет
     * <p>
     * Сложность - [O(len(value) + m]
     * m - количество слов в нашей мапе, которая соотвествует слову
     * (надеюсь, что так, потому что не уверен как под капотом работает.Но если через итераторы, то должно быть так)
     */
    public boolean remove(String value) {
        return removeOrCheck(value, true);
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
     * Сложность - [O(len(value))]
     */
    public List<String> getSimilarWords(String value) {
        if(!isValid(value)) {
            return Collections.emptyList();
        }

        List< String > list = storage.get(getLetsMap(value));
        if (list != null) {
            return list;
        }
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

    private Map< Character, Integer> getLetsMap(String value) {
        Map<Character, Integer> lets = new HashMap<>();
        for(int i = 0; i < value.length(); ++i) {
            lets.merge(Character.toLowerCase(value.charAt(i)), 1, Integer::sum);
        }
        return lets;
    }

    private boolean isValid(String value) {
        return value != null && !value.isEmpty();
    }

    private boolean removeOrCheck(String value, boolean remove) {
        if (!isValid(value)) {
            return false;
        }

        List< String > list = storage.get(getLetsMap(value));
        if (list != null) {
            if (remove) {
                if (list.removeIf(value::equals)) {
                    size--;
                    return true;
                }
                return false;
            } else {
                for (String s : list) {
                    if (s.equals(value)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

}
