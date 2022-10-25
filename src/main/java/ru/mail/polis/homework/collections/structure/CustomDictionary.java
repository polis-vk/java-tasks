package ru.mail.polis.homework.collections.structure;

import java.util.*;

/**
 * Задание оценивается в 4 тугрика.
 * Необходимо реализовать класс которы умеет хранить строки и возвращать
 * список строк состоящий из того же набора буков, что ему передали строку.
 * Напишите какая сложность операций у вас получилась для каждого метода.
 */
public class CustomDictionary {

    private final HashMap<String, String> map = new HashMap<>();

    /**
     * Сохранить строку в структуру данных
     *
     * @param value - передаваемая строка
     * @return - успешно сохранили строку или нет.
     * <p>
     * Сложность - O(N)
     */
    public boolean add(String value) {
        if (value == null || value.equals("")) {
            throw new IllegalArgumentException();
        } else if (map.containsKey(value)) { // T(N) = O(1)
            return false;
        }
        String sortLowerCaseValue = sortedLowerCaseStr(value);
        map.put(value, sortLowerCaseValue); // T(N) = O(N)  <-- (худший случай при перезаписи элементов)
        return true;
    }

    private String sortedLowerCaseStr(String value) {
        char[] chars = value.toLowerCase(Locale.ROOT).toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }

    /**
     * Проверяем, хранится ли такая строка уже у нас
     *
     * @param value - передаваемая строка
     * @return - есть такая строка или нет в нашей структуре
     * <p>
     * Сложность - O(1)
     */
    public boolean contains(String value) {
        return map.containsKey(value);
    }

    /**
     * Удаляем сохраненную строку если она есть
     *
     * @param value - какую строку мы хотим удалить
     * @return - true если удалили, false - если такой строки нет
     * <p>
     * Сложность - O(1)
     */
    public boolean remove(String value) {
        if (map.size() == 0 || !map.containsKey(value)) { // T(N) = O(1)
            return false;
        }
        map.remove(value); // T(N) = O(1)
        return true;
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
     * Сложность - O(N^2)
     */
    public List<String> getSimilarWords(String value) {
        ArrayList<String> list = new ArrayList<>();
        String inputStr = sortedLowerCaseStr(value);
        for (Map.Entry<String, String> entry: map.entrySet()) { // T(N) = O(N)
            if (entry.getValue().equals(inputStr)) { // T(N) = O(N)
                list.add(entry.getKey()); // T(N) = O(1)
            }
        }
        return list;
    }

    /**
     * Колл-во хранимых строк.
     *
     * @return - Колл-во хранимых строк.
     * <p>
     * Сложность - O(1)
     */
    public int size() {
        return map.size();
    }


}
