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
        if ((str == null) || (str == "")) {
            return null;
        }
        char lit = str.charAt(0);
        int max = 1;
        for (int i = 0; i < str.length(); i++) {
            int tempCount = 1;
            int tempPos = i;
            for (int k = 1; k < str.length(); k++) {
                if (str.charAt(i) == str.charAt(k) && (k - tempPos == 1)) {
                    tempPos++;
                    tempCount++;
                    if (tempCount > max) {
                        max = tempCount;
                        lit = str.charAt(i);
                    }
                }
            }
        }
        return new Pair<>(lit, max);
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
