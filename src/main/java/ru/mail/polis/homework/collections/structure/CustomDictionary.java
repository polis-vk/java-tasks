package ru.mail.polis.homework.collections.structure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Задание оценивается в 4 балла.
 * Необходимо реализовать класс, который умеет хранить строки и возвращать
 * список строк, состоящих из того же набора букв, что и переданная строка.
 * Напишите, какая сложность операций у вас получилась для каждого метода.
 */
public class CustomDictionary {

    private final Map<String, Words> dict = new HashMap<>();

    /**
     * Сохранить строку в структуру данных
     * @param value - передаваемая строка
     * @return - успешно сохранили строку или нет.
     *
     * Сложность - O(1)
     */
    public boolean add(String value) {
        if (value == null || value.equals("")) {
            throw new IllegalArgumentException();
        }
        return dict.putIfAbsent(value, new Words(value)) == null;
    }

    /**
     * Проверяем, хранится ли такая строка уже у нас
     * @param value - передаваемая строка
     * @return - есть такая строка или нет в нашей структуре
     *
     * Сложность - O(1)
     */
    public boolean contains(String value) {
        return dict.containsKey(value);
    }

    /**
     * Удаляем сохраненную строку, если она есть
     * @param value - какую строку мы хотим удалить
     * @return - true если удалили, false - если такой строки нет
     *
     * Сложность - O(1)
     */
    public boolean remove(String value) {
        if (!dict.containsKey(value)) {
            return false;
        }
        dict.remove(value);
        return true;
    }

    /**
     * Возвращает список из сохраненных ранее строк, которые состоят
     * из тех же букв, что и переданная строка.
     * Примеры: сохраняем строки ["aaa", "aBa", "baa", "aaB"]
     *          При поиске по строке "AAb" нам должен вернуться следующий
     *          список: ["aBa","baa","aaB"]
     *
     * Как можно заметить - регистр строки не должен влиять на поиск, при этом
     * возвращаемые строки хранятся в том виде, что нам передали изначально.
     *
     * @return - список слов, которые состоят из тех же букв, что и передаваемая
     * строка.
     *
     * Сложность - O(n), где n - количество записей в словаре
     */
    public List<String> getSimilarWords(String value) {
        List<String> res = new ArrayList<>();
        Words wordsOfValue = new Words(value);
        for (Map.Entry<String, Words> entry: dict.entrySet()) {
            if (wordsOfValue.equals(entry.getValue())) {
                res.add(entry.getKey());
            }
        }
        return res;
    }

    /**
     * Колл-во хранимых строк.
     * @return - Колл-во хранимых строк.
     *
     * Сложность - O(1)
     */
    public int size() {
        return dict.size();
    }

    private static class Words {

        private final Map<Character, Integer> words = new HashMap<>();

        public Words(String str) {
            String lowCaseString = str.toLowerCase();
            for (int i = 0; i < lowCaseString.length(); i++) {
                char c = lowCaseString.charAt(i);
                if (words.containsKey(c)) {
                    words.put(c, words.get(c) + 1);
                } else {
                    words.put(c, 1);
                }
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Words other = (Words) o;
            return Objects.equals(words, other.words);
        }

        @Override
        public int hashCode() {
            return Objects.hash(words);
        }
    }
}
