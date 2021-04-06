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
        if (str == null || str == "") {
            return null;
        }

        char cur = 0;
        char let = 0;
        int count = 0;
        int max = -1;
        char str_copy[] = str.toCharArray();
        for (int i = 0; i < (str_copy.length) ; i++) {

            if (str_copy[i] != cur) {
                count = 0;
            }
            count++;
            cur = str_copy[i];
            if (count > max) {
                max = count;
                let = cur;
            }
        }
        return new Pair<Character, Integer>(let, max);
    }

    public static class Pair<Character, Integer> {
        private final Character first;
        private final Integer second;

        public Pair(Character first, Integer second) {
            this.first = first;
            this.second = second;
        }

        public Character getFirst() {
            return first;
        }

        public Integer getSecond() {
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
