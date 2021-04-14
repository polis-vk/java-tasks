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
        if (str.length() == 1) {
            return new Pair<>(str.charAt(0), 1);
        }

        char resultChar = str.charAt(0);
        int resultCount = 1;

        for (int i = 0; i < str.length(); i++) {
            if (i == 0 || (str.charAt(i - 1) != str.charAt(i))) {
                int tempCount = 1;
                for (int j = i + 1; j < str.length(); j++) {
                    if (str.charAt(j) == str.charAt(i)) {
                        tempCount++;
                    } else {
                        break;
                    }
                }
                if (tempCount > resultCount) {
                    resultCount = tempCount;
                    resultChar = str.charAt(i);
                }
                i += tempCount - 1;
            }
        }

        return new Pair<>(resultChar, resultCount);
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
