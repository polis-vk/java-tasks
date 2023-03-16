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
        Pair<Character, Integer> result = null;
        if (Objects.nonNull(str) && !str.isEmpty()) {
            int maxLength = 1;
            int repeatSymbolCount = maxLength;
            char maxCommonSymbol = str.charAt(0);
            for (int i = 1; i < str.length(); i++) {
                if (str.charAt(i - 1) == str.charAt(i)) {
                    repeatSymbolCount++;
                } else {
                    repeatSymbolCount = 1;
                }
                if (repeatSymbolCount > maxLength) {
                    maxLength = repeatSymbolCount;
                    maxCommonSymbol = str.charAt(i - 1);
                }
            }
            result = new Pair<>(maxCommonSymbol, maxLength);
        }
        return result;
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
