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

        if (str == null) {
            return null;
        }
        if (str.isEmpty()) {
            return null;
        }
        char previousLetter = 0;
        char letter;
        Pair<Character, Integer> maxCharacter = new Pair<>('0', 0);
        Pair<Character, Integer> currentCharacter = new Pair<>('0', 0);
        for (int i = 0; i < str.length(); i++) {
            letter = str.charAt(i);
            if (i == 0) {
                maxCharacter = new Pair<>(letter, 1);
                currentCharacter = new Pair<>(letter, 1);
            } else {
                if (letter == previousLetter) {
                    currentCharacter = new Pair<>(currentCharacter.first, currentCharacter.second + 1);
                } else {
                    currentCharacter = new Pair<>(letter, 1);
                }
                if (currentCharacter.second > maxCharacter.second) {
                    maxCharacter = currentCharacter;
                }
            }
            previousLetter = letter;
        }
        return maxCharacter;
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
