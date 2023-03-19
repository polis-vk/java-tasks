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
        int strLength = str.length();
        char repeatingCharacter = str.charAt(0);
        int length = 1;
        char maxRepeatingCharacter = repeatingCharacter;
        int maxLength = 1;
        for (int i = 1; i < strLength; i++) {
            char currentChar = str.charAt(i);
            if (currentChar == repeatingCharacter) {
                length++;
                if (length > maxLength) {
                    maxRepeatingCharacter = repeatingCharacter;
                    maxLength = length;
                    if (i != strLength - 1 && maxLength >= strLength - i && currentChar != str.charAt(i + 1)) {
                        return new Pair<>(maxRepeatingCharacter, maxLength);
                    }
                }
            } else {
                repeatingCharacter = currentChar;
                length = 1;
            }
        }
        return new Pair<>(maxRepeatingCharacter, maxLength);
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
