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
        if (str == null || str.equals("")) {
            return null;
        }
        char[] chars = str.toCharArray();
        char maxCharacter = chars[0];
        int maxRepeats = 0;
        char currentCharacter = chars[0];
        int currentRepeats = 1;
        int length = str.length();
        for (int i = 1; i < length; i++) {
            if (chars[i] != currentCharacter) {
                if (currentRepeats > maxRepeats) {
                    maxRepeats = currentRepeats;
                    maxCharacter = currentCharacter;
                }
                currentCharacter = chars[i];
                currentRepeats = 1;
            } else {
                currentRepeats++;
            }
        }
        if (currentRepeats > maxRepeats) {
            return new Pair<>(currentCharacter, currentRepeats);
        }
        return new Pair<>(maxCharacter, maxRepeats);
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
