package ru.mail.polis.homework.objects;

import java.util.*;

/**
 * Нужно найти символ, который встречается подряд в строке чаще всего, и указать количество повторений.
 * Если более одного символа с максимальным значением, то нужно вернуть тот символ,
 * который первый встречается в строчке
 * Если строка пустая или null, то вернуть null
 * Пример abbasbdlbdbfklsssbb -> (s, 3)
 */
public class RepeatingCharacters {

    public static Pair<Character, Integer> getMaxRepeatingCharacters(String str) {
        if (str == null) return null;
        char[] chars = str.toCharArray();
        int currentCount;
        Pair<Character, Integer> current = null;
        Pair<Character, Integer> maximum = null;
        for (int i = 0; i < chars.length; i++) {
            Character currentChar = chars[i];
            Character previousChar = i == 0 ? null : chars[i - 1];
            if (previousChar != null ) {
                if (previousChar == currentChar) {
                    currentCount = current.second + 1;
                } else {
                    currentCount = 1;
                }
            } else {
                currentCount = 1;
                maximum = new Pair<>(currentChar, currentCount);
            }
            current = new Pair<>(currentChar, currentCount);

            if (current.second > maximum.second) {
                maximum = current;
            }
        }

        return maximum;
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
