package ru.mail.polis.homework.objects;

import java.util.*;

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
        if (str == null || str.equals("")) return null;
        char[] chars = str.toCharArray();
        int currentCount = 0;
        char maximumChar = str.charAt(0);
        int maximumCount = Integer.MIN_VALUE;
        for (int i = 0; i < chars.length; i++) {
            char currentChar = chars[i];
            if (i > 0) {
                char previousChar = chars[i - 1];
                if (previousChar == currentChar) {
                    currentCount = currentCount + 1;
                } else {
                    currentCount = 1;
                }
            } else {
                currentCount = 1;
                maximumCount = 1;
                maximumChar = currentChar;
            }

            if (currentCount > maximumCount) {
                maximumCount = currentCount;
                maximumChar = currentChar;
            }
        }
        return new Pair<>(maximumChar, maximumCount);
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
