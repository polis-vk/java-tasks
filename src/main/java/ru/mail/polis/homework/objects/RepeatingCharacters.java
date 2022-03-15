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
        int count = 1;
        int buffCount = 0;
        char buffChar = 0;

        if (Objects.equals(str, "") || str == null) {
            return null;
        }
        char letter = str.charAt(0);                              // {b,b,a,a,a,c}

        for (int i = 1; i < str.length(); i++) {
            if (str.charAt(i) == letter) {
                count++;
                if (count > buffCount && str.length()-1 == i) {
                    buffChar = letter;
                    buffCount = count;
                }
            } else {
                if (count > buffCount) {
                    buffChar = letter;
                    buffCount = count;
                }
                letter = str.charAt(i);
                count = 1;
            }
        }
        if (buffChar == 0) {
            buffChar = letter;
            buffCount = count;
        }
        return new Pair<>(buffChar, buffCount);
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
