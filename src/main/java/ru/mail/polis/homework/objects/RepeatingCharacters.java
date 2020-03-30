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
        char[] array = str.toCharArray();
        char resultArray = array[0];
        int resultCount = 1;
        for (int i = 0; i < array.length - 1; i++) {
            int count = 1;
            if (array[i] == array[i + 1]) {
                while (i < array.length - 1 && array[i + 1] == array[i]) {
                    count++;
                    i++;
                }
                if (count > resultCount) {
                    resultCount = count;
                    resultArray = array[i];
                }
            }
        }
        return new Pair<>(resultArray, resultCount);
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
