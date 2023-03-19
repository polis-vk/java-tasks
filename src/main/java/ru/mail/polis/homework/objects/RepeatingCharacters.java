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
        if (str == null || "".equals(str)) {
            return null;
        }
        int repetitionNumber = 1;
        int maxRepetition = 1;
        char maxRepeatingCharacter = str.charAt(0);
        char repeatingCharacter = str.charAt(0);
        int strLength = str.length();
        for (int i = 1; i < strLength; i++) {
            char currentCharacter = str.charAt(i);
            if (currentCharacter == repeatingCharacter) {
                repetitionNumber++;
            } else {
                if (maxRepetition < repetitionNumber) {
                    maxRepetition = repetitionNumber;
                    maxRepeatingCharacter = repeatingCharacter;
                }
                repetitionNumber = 1;
                repeatingCharacter = currentCharacter;
            }
        }
        if (repetitionNumber > maxRepetition) {
            maxRepetition = repetitionNumber;
            maxRepeatingCharacter = repeatingCharacter;
        }
        return new Pair<>(maxRepeatingCharacter, maxRepetition);
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
