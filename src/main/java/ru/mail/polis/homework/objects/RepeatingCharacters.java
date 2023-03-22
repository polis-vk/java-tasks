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
        int maxNumRepeats = 1;
        int numRepeats = 1;
        char maxChar = str.charAt(0);
        for (int counter = 1; counter < str.length(); counter++) {
            if (str.charAt(counter) == str.charAt(counter - 1)) {
                numRepeats++;
                if (counter != str.length() - 1) {
                    continue;
                }
            }
            if (numRepeats > maxNumRepeats) {
                maxNumRepeats = numRepeats;
                maxChar = str.charAt(counter - 1);
            }
            numRepeats = 1;
        }
        return new Pair<>(maxChar, maxNumRepeats);
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
