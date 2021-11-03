package ru.mail.polis.homework.collections.structure;

import java.util.*;

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
     * Сложность - O(m log(m) + log(n)),
     * где m - длина строки,
     * n - this.size()
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
        Set<String> set = storage.computeIfAbsent(new LetterSignature(value), k -> new HashSet<>());
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
     * Сложность - O(m + log(n)),
     * где m - длина строки,
     * n - this.size()
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
     * Сложность - O(m + log(n)),
     * где m - длина строки,
     * n - this.size()
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
     * Сложность - O(m log(m) + log(k) + p),
     * где m - длина строки,
     * k - количество различных (по набору букв) строк,
     * p - количество строк с тем же набором букв, что и value
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
        private final int[] internal;

        public LetterSignature(String word) {
            internal = word
                    .chars()
                    .filter(Character::isLetter)
                    .map(Character::toLowerCase)
                    .sorted()
                    .toArray();
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
    }
}
