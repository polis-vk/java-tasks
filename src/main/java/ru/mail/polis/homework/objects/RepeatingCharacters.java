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
        if (str == null || str.isEmpty()) return null;

        int currentCharIndex;
        int currentCharCount;
        char currentChar;

        int maxCount = 0;
        char maxCountChar = str.charAt(0);

        for (int i = 0; i < str.length(); i += currentCharCount) {
            currentCharIndex = i + 1;
            currentChar = str.charAt(i);
            currentCharCount = 1;

            while (currentCharIndex < str.length() && currentChar == str.charAt(currentCharIndex)) {
                currentChar = str.charAt(currentCharIndex);
                currentCharIndex++;
                currentCharCount++;
            }
            if (currentCharCount > maxCount) {
                maxCount = currentCharCount;
                maxCountChar = currentChar;
            }
        }
        return new Pair<>(maxCountChar, maxCount);
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
