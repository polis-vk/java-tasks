package ru.mail.polis.homework.collections.structure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Задание оценивается в 4 балла.
 * Необходимо реализовать класс, который умеет хранить строки и возвращать
 * список строк, состоящий из того же набора букв, что и переданная ему строка.
 * Напишите, какая сложность операций у вас получилась для каждого метода.
 */
public class CustomDictionary {
    private final Map<LetterSignature, Set<String>> storage = new HashMap<>();
    private final Map<String, Set<String>> index = new HashMap<>();

    /**
     * Сохранить строку в структуру данных
     *
     * @param value - передаваемая строка
     * @return - успешно сохранили строку или нет.
     * <p>
     * Сложность - O(log(storage.size()) + log(index.size()))
     */
    public boolean add(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Null is not permitted");
        }
        if (value.isEmpty()) {
            throw new IllegalArgumentException("Empty string is not permitted");
        }
        if (contains(value)) {
            return false;
        }
        final LetterSignature letters = new LetterSignature(value);
        Set<String> set = storage.computeIfAbsent(letters, k -> new HashSet<>());
        index.put(value, set);
        set.add(value);
        return true;
    }

    /**
     * Проверяем, хранится ли такая строка уже у нас
     *
     * @param value - передаваемая строка
     * @return - есть такая строка или нет в нашей структуре
     * <p>
     * Сложность - O(log(index.size()))
     */
    public boolean contains(String value) {
        return index.containsKey(value);
    }

    /**
     * Удаляем сохраненную строку если она есть
     *
     * @param value - какую строку мы хотим удалить
     * @return - true если удалили, false - если такой строки нет
     * <p>
     * Сложность - O(log(index.size()) + log(set.size()))
     */
    public boolean remove(String value) {
        final Set<String> set = index.get(value);
        if (set == null) {
            return false;
        }
        set.remove(value);
        index.remove(value);
        return true;
    }

    /**
     * Возвращает список из сохраненных ранее строк, которые состоят
     * из того же набора букв, что и переданная ему строка.
     * Примеры:
     * сохраняем строки ["aaa", "aBa", "baa", "aaB"]
     * При поиске по строке "AAb" нам должен вернуться следующий
     * список: ["aBa","baa","aaB"]
     * <p>
     * Сохраняем строки ["aaa", "aAa", "a"]
     * поиск "aaaa"
     * результат: []
     * Как можно заметить - регистр строки не должен влиять на поиск, при этом
     * возвращаемые строки хранятся в том виде, что нам передали изначально.
     *
     * @return - список слов которые состоят из тех же букв, что и передаваемая
     * строка.
     * <p>
     * Сложность - O(log(storage.size()) + similar.size())
     */
    public List<String> getSimilarWords(String value) {
        Collection<String> similar = storage.get(new LetterSignature(value));
        if (similar == null) {
            return Collections.emptyList();
        }
        return new ArrayList<>(similar);
    }

    /**
     * Кол-во хранимых строк.
     *
     * @return - Кол-во хранимых строк.
     * <p>
     * Сложность - O(1)
     */
    public int size() {
        return index.size();
    }

    private static class LetterSignature {
        private static final int ALPHABET_SIZE = 26;
        private final int[] internal = new int[ALPHABET_SIZE];

        public LetterSignature(String word) {
            for (char c : word.toCharArray()) {
                if (!isLetter(c)) {
                    continue;
                }
                internal[index(lowerCase(c))]++;
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
            LetterSignature that = (LetterSignature) o;
            return Arrays.equals(internal, that.internal);
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(internal);
        }

        private static int index(char c) {
            return c - 'a';
        }

        private static char lowerCase(char c) {
            return Character.toLowerCase(c);
        }

        private static boolean isLetter(char c) {
            return 'a' <= c && c <= 'z' || 'A' <= c && c <= 'Z';
        }
    }
}
