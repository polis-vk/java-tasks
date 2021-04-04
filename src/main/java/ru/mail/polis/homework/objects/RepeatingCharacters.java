package ru.mail.polis.homework.objects;


import java.util.Objects;

/**
 * Нужно найти символ, который встречается подряд в строке чаще всего, и указать количество повторений.
 * Если более одного символа с максимальным значением, то нужно вернуть тот символ,
 * который первый встречается в строчке
 * Если строка пустая или null, то вернуть null
 * Пример abbasbdlbdbfklsssbb -> (s, 3)
 */
public class RepeatingCharacters {

    public static Pair<Character, Integer> getMaxRepeatingCharacters(String str) {
        if (str == null || str.isEmpty()) {
            return null;
        }
        char[] symbols = str.toCharArray();
        char maxFrequent = symbols[0];
        int sequenceLength = 1;
        int maxSequenceLength = 1;
        for (int i = 1; i < symbols.length; i++) {
            if (symbols[i] == symbols[i - 1]) {
                sequenceLength++;
            }
            if (symbols[i] != symbols[i - 1] || i == symbols.length - 1) {
                if (sequenceLength > maxSequenceLength) {
                    maxSequenceLength = sequenceLength;
                    maxFrequent = symbols[i - 1];
                }
                sequenceLength = 1;
            }
        }
        return new Pair<>(maxFrequent, maxSequenceLength);
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
