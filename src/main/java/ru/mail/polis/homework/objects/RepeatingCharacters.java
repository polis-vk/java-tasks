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
        char curChar = str.charAt(0);
        int length = str.length();
        char tempChar = ' ';
        char maxChar = curChar;
        int maxAmount = 1;
        int indexDiff = 0;
        for (int i = 0; i < length; i++) {
            tempChar = str.charAt(i);
            if (curChar != tempChar) {
                if (maxAmount < i - indexDiff) {
                    maxAmount = i - indexDiff;
                    maxChar = curChar;
                }
                indexDiff = i;
                curChar = tempChar;
            }
        }
        if (maxAmount < length - indexDiff) {
            maxAmount = length - indexDiff;
            maxChar = tempChar;
        }
        return new Pair<>(maxChar, maxAmount);
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
