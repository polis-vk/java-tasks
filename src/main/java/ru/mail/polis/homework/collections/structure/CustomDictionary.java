package ru.mail.polis.homework.collections.structure;

import java.util.*;

/**
 * Задание оценивается в 4 балла.
 * Необходимо реализовать класс которы умеет хранить строки и возвращать
 * список строк состоящий из того же набора буков, что ему передали строку.
 * Напишите какая сложность операций у вас получилась для каждого метода.
 */
public class CustomDictionary {
    private final Map<DictionaryKey, Set<String>> words = new HashMap<>();
    private int size;

    /**
     * Сохранить строку в структуру данных
     *
     * @param value - передаваемая строка
     * @return - успешно сохранили строку или нет.
     * <p>
     * Сложность - O(value.length())
     */
    public boolean add(String value) {
        if (value == null || value.equals("")) {
            throw new IllegalArgumentException();
        }
        DictionaryKey key = new DictionaryKey(value);
        words.putIfAbsent(key, new HashSet<>());
        if (!words.get(key).add(value)) {
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
     * Сложность - O(value.length())
     */
    public boolean contains(String value) {
        if (value == null || value.equals("")) {
            return false;
        }
        DictionaryKey key = new DictionaryKey(value);
        Set<String> container = words.get(key);
        if (container == null) {
            return false;
        }
        return container.contains(value);
    }

    /**
     * Удаляем сохраненную строку если она есть
     *
     * @param value - какую строку мы хотим удалить
     * @return - true если удалили, false - если такой строки нет
     * <p>
     * Сложность - O(value.length())
     */
    public boolean remove(String value) {
        if (value == null || value.equals("")) {
            return false;
        }
        DictionaryKey key = new DictionaryKey(value);
        Set<String> container = words.get(key);
        if (container == null) {
            return false;
        }
        if (!container.remove(value)) {
            return false;
        }
        size--;
        if (container.isEmpty()) {
            words.remove(key);
        }
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
     * Сложность - O(value.length())
     */
    public List<String> getSimilarWords(String value) {
        if (value == null || value.equals("")) {
            return Collections.emptyList();
        }
        DictionaryKey key = new DictionaryKey(value);
        Set<String> container = words.get(key);
        if (container == null) {
            return Collections.emptyList();
        }
        return new ArrayList<>(container);
    }

    /**
     * Колл-во хранимых строк.
     *
     * @return - Колл-во хранимых строк.
     * <p>
     * Сложность - O(1)
     */
    public int size() {
        return size;
    }

    private static class DictionaryKey {
        private final int wordLength;
        private final Set<Character> letters;

        public DictionaryKey(String word) {
            this.wordLength = word.length();
            Set<Character> letters = new HashSet<>();
            for (char ch : word.toUpperCase().toCharArray()) {
                letters.add(ch);
            }
            this.letters = letters;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            DictionaryKey key = (DictionaryKey) o;
            return wordLength == key.wordLength &&
                    letters.equals(key.letters);
        }

        @Override
        public int hashCode() {
            return Objects.hash(wordLength, letters);
        }
    }

}
