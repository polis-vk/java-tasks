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
        char[] arrSymbols = str.toCharArray();
        int resultLenght = 1;
        char resultSymbol = arrSymbols[0];
        for (int i = 0; i < arrSymbols.length - 1; i++) {
            if (arrSymbols[i] == arrSymbols[i + 1]) {
                int lenght = 2;
                int j = i + 1;
                while (j < arrSymbols.length - 1 && arrSymbols[j] == arrSymbols[j + 1]) {
                    lenght++;
                    j++;
                }
                if (lenght > resultLenght) {
                    resultLenght = lenght;
                    resultSymbol = arrSymbols[i];
                }
                i = j;
            }
        }
        return new Pair<>(resultSymbol, resultLenght);
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
