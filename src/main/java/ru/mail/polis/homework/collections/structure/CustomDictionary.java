package ru.mail.polis.homework.collections.structure;

import java.util.*;

/**
 * Задание оценивается в 4 балла.
 * Необходимо реализовать класс которы умеет хранить строки и возвращать
 * список строк состоящий из того же набора буков, что ему передали строку.
 * Напишите какая сложность операций у вас получилась для каждого метода.
 */
public class CustomDictionary {
    private final HashMap<String, ArrayList<String>> stringDict = new HashMap<>();
    private int size = 0;

    /**
     * Проверяем, хранится ли такая строка уже у нас
     *
     * @param value - передаваемая строка
     * @return - есть такая строка или нет в нашей структуре
     * <p>
     * Сложность - [O(nlogn), поскольку при получении ключа происходит сортировка символов. Эта операция самая затратная]
     */
    public boolean contains(String value) {
        if (value == null) {
            return false;
        }

        String key = getKey(value);

        if (stringDict.containsKey(key)) {
            return stringDict.get(key).contains(value);
        }

        return false;
    }

    /**
     * Удаляем сохраненную строку если она есть
     *
     * @param value - какую строку мы хотим удалить
     * @return - true если удалили, false - если такой строки нет
     * <p>
     * Сложность - [O(nlogn)]
     */
    public boolean remove(String value) {
        if (value == null) {
            return false;
        }

        if (!contains(value)) {
            return false;
        }

        String key = getKey(value);
        stringDict.get(key).remove(value);

        size--;
        return true;
    }

    /**
     * Сохранить строку в структуру данных
     *
     * @param value - передаваемая строка
     * @return - успешно сохранили строку или нет.
     * <p>
     * Сложность - [O(nlogn)]
     */
    public boolean add(String value) {
        if (value == null) {
            return false;
        }

        String key = getKey(value);
        if (stringDict.containsKey(key)) {
            stringDict.get(key).add(value);
        } else {
            ArrayList<String> arrForNewKey = new ArrayList<>();
            arrForNewKey.add(value);
            stringDict.put(key, arrForNewKey);
        }
        size++;

        return true;
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
     * Сложность - [O(nlogn)]
     */
    public List<String> getSimilarWords(String value) {
        if (value == null) {
            return Collections.emptyList();
        }
        return stringDict.get(getKey(value));
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

    private static String getKey(String value) {
        char[] key = (value.toLowerCase()).toCharArray();
        Arrays.sort(key);
        // Ключ является String, а не массивом char, потому что у массивов одинаковый hashCode,
        // только если объекты те же, а не само содержимое
        return Arrays.toString(key);
    }
}
