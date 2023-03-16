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
        if (str == null || str.length() == 0) {
            return null;
        }

        char[] strArray = str.toCharArray();
        int[] lengthArray = new int[strArray.length];
        lengthArray[0] = 1;

        for (int i = 1; i < strArray.length; i++) {
            if (strArray[i] == strArray[i - 1]) {
                lengthArray[i] = lengthArray[i - 1] + 1;
            } else {
                lengthArray[i] = 1;
            }
        }

        int indexMax = 0;
        int max = 0;
        for (int i = 0; i < lengthArray.length; i++) {
            indexMax = lengthArray[i] > max ? i : indexMax;
            max = Math.max(max, lengthArray[i]);
        }

        return new Pair<>(strArray[indexMax], max);
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
