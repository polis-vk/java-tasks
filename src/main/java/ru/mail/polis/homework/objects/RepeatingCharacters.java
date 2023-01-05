package ru.mail.polis.homework.objects;


import java.util.Arrays;
import java.util.Objects;

/**
 * Нужно найти символ, который встречается подряд в строке чаще всего, и указать количество повторений.
 * Если более одного символа с максимальным значением, то нужно вернуть тот символ,
 * который первый встречается в строчке
 * Если строка пустая или null, то вернуть null
 * Пример abbasbdlbdbfklsssbb -> (s, 3)
 */
public class RepeatingCharacters {

    public static void main(String[] args) {
        Pair<Character, Integer> tm = RepeatingCharacters.getMaxRepeatingCharacters("aaaddddggeeereee");
        System.out.println(tm.getFirst() + " " + tm.getSecond());
    }

    public static Pair<Character, Integer> getMaxRepeatingCharacters(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }

        int[] alp = new int[26];
        int[] first_appear = new int[26];
        Arrays.fill(first_appear, -1);
        int repeat_count = 0;
        for (int i = 0; i < str.length(); i++) {
            int letter = str.charAt(i) - 'a';
            if (first_appear[letter] == -1) {
                first_appear[letter] = i;
            }

            if (i == 0) {
                repeat_count++;
                continue;
            }

            int letter_prev = str.charAt(i - 1) - 'a';

            if (str.charAt(i - 1) == str.charAt(i)) {
                repeat_count++;
            } else {
                alp[letter_prev] = Math.max(alp[letter_prev], repeat_count);
                repeat_count = 1;
            }

        }
        int letter_last = str.charAt(str.length() - 1) - 'a';
        alp[letter_last] = Math.max(alp[letter_last], repeat_count);

        int ma = 0;
        int ind = -1;
        for (int i = 0; i < 26; i++) {
            if (alp[i] > ma) {
                ma = alp[i];
                ind = i;
            } else if (alp[i] == ma && ind != -1) {
                if (first_appear[ind] > first_appear[i]) {
                    ind = i;
                }
            }
        }


        return new Pair<>((char) ('a' + ind), ma);
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
