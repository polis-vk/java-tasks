package ru.mail.polis.homework.collections.structure;

import java.util.*;

/**
 * Задание оценивается в 4 балла.
 * Необходимо реализовать класс которы умеет хранить строки и возвращать
 * список строк состоящий из того же набора буков, что ему передали строку.
 * Напишите какая сложность операций у вас получилась для каждого метода.
 */
public class CustomDictionary {

    private Map<Letters, Set<String>> dict = new HashMap<>();
    private int size = 0;

    /**
     * Сохранить строку в структуру данных
     * @param value - передаваемая строка
     * @return - успешно сохранили строку или нет.
     *
     * Сложность - O(value.size())
     */
    public boolean add(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Can't add null");
        }
        if (value.isEmpty()) {
            throw new IllegalArgumentException("Can't add empty line");
        }

        Letters letters = new Letters(value);
        Set<String> strings = dict.computeIfAbsent(letters, s -> new HashSet<>());

        boolean result = strings.add(value);
        if (result) {
            size++;
        }
        return result;
    }

    /**
     * Проверяем, хранится ли такая строка уже у нас
     * @param value - передаваемая строка
     * @return - есть такая строка или нет в нашей структуре
     *
     * Сложность - O(value.size())
     */
    public boolean contains(String value) {
        if (value == null) {
            return false;
        }

        Letters letters = new Letters(value);
        if (!dict.containsKey(letters)) {
            return false;
        } else {
            return dict.get(letters).contains(value);
        }
    }

    /**
     * Удаляем сохраненную строку если она есть
     * @param value - какую строку мы хотим удалить
     * @return - true если удалили, false - если такой строки нет
     *
     * Сложность - O(value.size())
     */
    public boolean remove(String value) {
        if (value == null) {
            return false;
        }

        Letters letters = new Letters(value);
        if (!dict.containsKey(letters)) {
            return false;
        } else {
            boolean result = dict.get(letters).remove(value);
            if (result) {
                size--;
            }

            if (dict.get(letters).isEmpty()) {
                dict.remove(letters);
            }

            return result;
        }
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
     * Сложность - O(value.size() + dict.size())
     */
    public List<String> getSimilarWords(String value) {
        if (value == null) {
            return Collections.emptyList();
        }

        Letters letters = new Letters(value);

        return dict.containsKey(letters) ? new LinkedList<>(dict.get(letters)) : Collections.emptyList();
    }

    /**
     * Колл-во хранимых строк.
     * @return - Колл-во хранимых строк.
     *
     * Сложность - O(1)
     */
    public int size() {
        return size;
    }

    private class Letters {
        private static final int LETTERS_COUNT = 26;
        private int[] letters = new int[LETTERS_COUNT];

        Letters(String value) {
            for(int i = 0; i < value.length(); ++i) {
                if (Character.isLetter(value.charAt(i))) {
                    letters[Character.toLowerCase(value.charAt(i)) - 'a']++;
                }
            }
        }

        @Override
        public boolean equals(Object o) {
            if (o.getClass() != Letters.class) {
                return false;
            }
            return this.hashCode() == ((Letters) o).hashCode();
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(letters);
        }
    }
}
