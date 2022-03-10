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
        int[] counts = new int[str.length()];
        char[] symbols = new char[str.length()];
        int cnt;
        int index;
        for (int i = 0; i < str.length(); i += cnt) {
            cnt = 0;
            index = i;
            while ((index < str.length()) && (str.charAt(i) == str.charAt(index))) {
                ++cnt;
                ++index;
            }
            counts[i] = cnt;
            symbols[i] = str.charAt(i);
        }
        int max = counts[0];
        index = 0;
        for (int i = 0; i < str.length(); i++) {
            if (counts[i] > max) {
                max = counts[i];
                index = i;
            }
        }
        return new Pair<>(symbols[index], max);
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
