package ru.mail.polis.homework.objects;


import java.util.Objects;

/**
 * Нужно найти символ, который встречается подряд в строке чаще всего, и указать количество повторений.
 * Если более одного символа с максимальным значением, то нужно вернуть тот символ,
 * который первый встречается в строчке
 * Если строка пустая или null, то вернуть null
 * Пример abbasbdlbdbfklsssbb -> (s, 3)
 * 4 тугрика
 */
public class RepeatingCharacters {

    public static Pair<Character, Integer> getMaxRepeatingCharacters(String str) {
        if (str == null || str.isEmpty()) {
            return null;
        }
        int numberOfRepetitions = 1;
        char symbol = str.charAt(0);
        int maxNumberOfRepetitions = 1;
        for (int i = 1; i < str.length(); i++) {
            if (str.length() - i + numberOfRepetitions <= maxNumberOfRepetitions) {
                break;
            }
            if (str.charAt(i) == str.charAt(i - 1)) {
                numberOfRepetitions++;
            } else if (numberOfRepetitions > maxNumberOfRepetitions) {
                maxNumberOfRepetitions = numberOfRepetitions;
                symbol = str.charAt(i - 1);
                numberOfRepetitions = 1;
            } else {
                numberOfRepetitions = 1;
            }
        }
        if (numberOfRepetitions > maxNumberOfRepetitions) {
            return new Pair<>(str.charAt(str.length() - 1), numberOfRepetitions);
        }
        return new Pair<>(symbol, maxNumberOfRepetitions);
    }

    public static class Pair<T, V> {
        private final T first;
        private final V second;

        public Pair(T first, V second) {
            this.first = first;
            this.second = second;
        }

        public T getFirst() {
            return first;
        }

        public V getSecond() {
            return second;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Pair<?, ?> pair = (Pair<?, ?>) o;
            return Objects.equals(first, pair.first) && Objects.equals(second, pair.second);
        }

    }
}
