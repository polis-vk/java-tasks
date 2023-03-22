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

        int currentCount = 1;
        int maximumCount = 1;
        char[] chars = str.toCharArray();
        char previousChar = chars[0];
        char maximumChar = chars[0];

        for (int i = 1; i < str.length(); i++) {
            if (chars[i] == previousChar) {
                currentCount++;
            } else {
                previousChar = str.charAt(i);
                currentCount = 1;
            }

            if (currentCount > maximumCount) {
                maximumCount = currentCount;
                maximumChar = previousChar;
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
