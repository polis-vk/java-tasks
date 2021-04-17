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
        Character ch = null;
        if (str == null || str.isEmpty()) {
            return null;
        }
        if (str.length() == 1) {
            return new Pair<>(str.charAt(0), 1);
        }
        int maxCommonLetter = 0;
        for (int i = 0; i < str.length(); i++) {
            int count = checkCommon(str, i + 1, str.charAt(i));
            if (count > maxCommonLetter) {
                ch = str.charAt(i);
                maxCommonLetter = count;
            }
        }
        return new Pair<>(ch, maxCommonLetter);
    }

    public static int checkCommon(String str, int startIndex, Character first) {
        int commonLetters = 1;
        for (int i = startIndex; i < str.length(); i++) {
            if (str.charAt(i) == first) {
                commonLetters++;
            } else {
                i = str.length();
            }
        }
        return commonLetters;
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
