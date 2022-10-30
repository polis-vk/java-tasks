package ru.mail.polis.homework.collections.structure;

import java.util.*;
import java.util.stream.Stream;

/**
 * Задание оценивается в 4 тугрика.
 * Необходимо реализовать класс которы умеет хранить строки и возвращать
 * список строк состоящий из того же набора буков, что ему передали строку.
 * Напишите какая сложность операций у вас получилась для каждого метода.
 */
public class CustomDictionary {

    private final Map<String, String> map = new HashMap<>();

    /**
     * Сохранить строку в структуру данных
     * @param value - передаваемая строка
     * @return - успешно сохранили строку или нет.
     *
     * Сложность - []
     */
    public boolean add(String value) {
        if (value == null || value.isBlank()){
            throw new IllegalArgumentException();
        }
        return map.put(value, value) == null;
    }

    /**
     * Проверяем, хранится ли такая строка уже у нас
     * @param value - передаваемая строка
     * @return - есть такая строка или нет в нашей структуре
     *
     * Сложность - []
     */
    public boolean contains(String value) {
        return map.get(value) != null;
    }

    /**
     * Удаляем сохраненную строку если она есть
     * @param value - какую строку мы хотим удалить
     * @return - true если удалили, false - если такой строки нет
     *
     * Сложность - []
     */
    public boolean remove(String value) {
        if (map.get(value) == null) {
            return false;
        }
        map.remove(value);
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
     * Сложность - []
     */
    public List<String> getSimilarWords(String value) {
        List<String> list = new ArrayList<>();
        String val = value.toLowerCase(Locale.ROOT);
        int sum = sumeer(val);
        for (Map.Entry k : map.entrySet()){
            int s = sumeer((String) k.getValue());
            if (s == sum) {
                list.add((String) k.getValue());
            }
        }
        return list;
    }

    public int sumeer(String value){
        String val = value.toLowerCase(Locale.ROOT);
        int sum = 0;
        for(int i = 0; i < value.length(); i++) {
            sum += val.charAt(i);
        }
        return sum;
    }

    /**
     * Колл-во хранимых строк.
     * @return - Колл-во хранимых строк.
     *
     * Сложность - []
     */
    public int size() {
        return map.size();
    }


}
