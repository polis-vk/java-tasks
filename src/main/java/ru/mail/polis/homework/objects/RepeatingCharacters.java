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
        // Граничный случай
        if (str == null || str.equals("")) {
            return null;
        }

        int quantOfSym = 1; // Кол-во повторяющихся символов на данный момент
        int maxSym = quantOfSym; // Максимальное кол-во повторяющихся символов

        char currentChar = str.charAt(0); // Первый символ
        char maxRepChar = currentChar; // Самый часто повторяющийся символ

        for (int i = 1; i < str.length(); i++) {
            if (currentChar == str.charAt(i)) {
                quantOfSym += 1;
            } else {
                currentChar = str.charAt(i);
                quantOfSym = 1;
            }

            if (quantOfSym > maxSym) {
                maxSym = quantOfSym;
                maxRepChar = currentChar;

            }
        }


        return new Pair<>(maxRepChar, maxSym);
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
