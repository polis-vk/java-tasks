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

        if (str.length() == 1) {
            return new Pair<>(str.charAt(0), 1);
        }

        char maxSymb = str.charAt(0);
        int maxLength = 1;

        char currentSymb = str.charAt(0);
        int currentLength = 1;

        for (int i = 0; i < str.length() - 1; i++) {

            if (str.charAt(i + 1) == currentSymb) {
                currentLength++;
            } else {
                if (maxLength < currentLength) {
                    maxLength = currentLength;
                    maxSymb = currentSymb;
                }
                if (str.length() - i - 1 <= maxLength) {
                    break;
                }
                currentLength = 1;
                currentSymb = str.charAt(i + 1);
            }
        }

        if (maxLength < currentLength) {
            maxLength = currentLength;
            maxSymb = currentSymb;
        }

        return new Pair<>(maxSymb, maxLength);
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
