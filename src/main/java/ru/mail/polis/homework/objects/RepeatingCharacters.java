package ru.mail.polis.homework.objects;


import com.sun.tools.javac.util.ArrayUtils;

import java.util.Arrays;
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

    private static int inDex(Character[] array, char s) {

        for (int i = 0; i < array.length; i++) {
            if (array[i] == s) {
                return i;
            }
        }
        return -1;
    }

    public static Pair<Character, Integer> getMaxRepeatingCharacters(String str) {
        if (Objects.equals(str, null) || str.length() == 0) {
            return null;
        }
        int N = str.length();
        str += '?';
        Character[] chrs = new Character[N];
        int[] counts = new int[N];
        int lenPovtor = 1;
        int position = 0;
        int index;
        for (int i = 0; i < N; i++) {
            if (!Arrays.asList(chrs).contains(str.charAt(i))) {
                chrs[position] = str.charAt(i);
                position += 1;
            }
            if (str.charAt(i) == str.charAt(i + 1)) {
                lenPovtor += 1;
            } else {
                index = inDex(chrs, str.charAt(i));
                counts[index] = Math.max(lenPovtor, counts[index]);
                lenPovtor = 1;
            }

        }
        int max = 0;
        int indexChar = 0;
        for (int i = 0; i < N; i++) {
            if (counts[i] > max) {
                max = counts[i];
                indexChar = i;
            }
        }
        return new Pair<>(chrs[indexChar], max);
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
