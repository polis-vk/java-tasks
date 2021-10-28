package ru.mail.polis.homework.collections.structure;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ListIterator;
import java.util.LinkedList;
import java.util.BitSet;
import java.util.Objects;

/**
 * Задание оценивается в 4 балла.
 * Необходимо реализовать класс, который умеет хранить строки и возвращать
 * список строк, состоящий из того же набора букв, что и переданная ему строка.
 * Напишите, какая сложность операций у вас получилась для каждого метода.
 */
public class CustomDictionary {

    public static final int ALPHABET_SIZE = 26;
    private final Map<LetterSet, List<String>> storage = new HashMap<>();
    private final Map<String, ListIterator<String>> cache = new HashMap<>();

    /**
     * Сохранить строку в структуру данных
     *
     * @param value - передаваемая строка
     * @return - успешно сохранили строку или нет.
     * <p>
     * Сложность - O(log(storage.size()) + log(cache.size()))
     */
    public boolean add(String value) {
        if (value == null) {
            return false;
        }
        if (contains(value)) {
            return false;
        }
        final LetterSet letters = new LetterSet(value);
        List<String> list = storage.computeIfAbsent(letters, k -> new LinkedList<>());
        final ListIterator<String> it = list.listIterator(list.size());
        cache.put(value, it);
        it.add(value);
        return true;
    }

    /**
     * Проверяем, хранится ли такая строка уже у нас
     *
     * @param value - передаваемая строка
     * @return - есть такая строка или нет в нашей структуре
     * <p>
     * Сложность - O(log(cache.size()))
     */
    public boolean contains(String value) {
        return cache.containsKey(value);
    }

    /**
     * Удаляем сохраненную строку если она есть
     *
     * @param value - какую строку мы хотим удалить
     * @return - true если удалили, false - если такой строки нет
     * <p>
     * Сложность - O(log(cache.size()))
     */
    public boolean remove(String value) {
        final ListIterator<String> iterator = cache.get(value);
        if (iterator == null) {
            return false;
        }
        iterator.remove();
        cache.remove(value);
        return true;
    }

    /**
     * Возвращает список из сохраненных ранее строк, которые состоят
     * из того же набора букв что и переданная ему строка.
     * Примеры:
     * сохраняем строки ["aaa", "aBa", "baa", "aaB"]
     * При поиске по строке "AAb" нам должен вернуться следующий
     * список: ["aBa","baa","aaB"]
     * <p>
     * Сохраняем строки ["aaa", "aAa", "a"]
     * поиск "aaaa"
     * результат: []
     * Как можно заметить - регистр строки не должен влиять на поиск, при этом
     * возвращаемые строки хранятся в том виде что нам передали изначально.
     *
     * @return - список слов которые состоят из тех же букв, что и передаваемая
     * строка.
     * <p>
     * Сложность - O(log(storage.size()))
     */
    public List<String> getSimilarWords(String value) {
        return storage.get(new LetterSet(value));
    }

    /**
     * Кол-во хранимых строк.
     *
     * @return - Колл-во хранимых строк.
     * <p>
     * Сложность - O(1)
     */
    public int size() {
        return cache.size();
    }

    private static class LetterSet {
        final BitSet internal = new BitSet(ALPHABET_SIZE);

        public LetterSet(String word) {
            for (char c : word.toCharArray()) {
                if (!isLetter(c)) {
                    continue;
                }
                internal.set(index(lowerCase(c)), true);
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            if (!super.equals(o)) return false;
            LetterSet that = (LetterSet) o;
            return internal.equals(that.internal);
        }

        @Override
        public int hashCode() {
            return Objects.hash(internal);
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
