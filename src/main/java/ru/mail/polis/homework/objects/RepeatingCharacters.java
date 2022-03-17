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
        if (str == "" || str == null) {
            return null;
        }
        char[] charArray = str.toCharArray();
        int count = 1;
        int maxCount = 0;
        char maxRepeatedChar = charArray[0];
        for (int i = 1; i < charArray.length; ++i) {
            if (charArray[i - 1] == charArray[i]) {
                ++count;
            } else {
                if (count > maxCount) {
                    maxCount = count;
                    maxRepeatedChar = charArray[i - 1];
                }
                count = 1;
            }
        }
        if (count > maxCount) {
            return new Pair<>(charArray[charArray.length - 1], count);
        }
        return new Pair<>(maxRepeatedChar, maxCount);
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
