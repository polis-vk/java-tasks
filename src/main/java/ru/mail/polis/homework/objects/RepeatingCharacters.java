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
        char letter = str.charAt(0);
        int strLength = str.length();
        if (strLength == 1) {
            return new Pair<>(letter, 1);
        }
        int earlyBeg=0;
        int max=1;
        char mc = letter;
        char c = ' ';
        for(int i=1; i< strLength; i++) {
            c = str.charAt(i);
            if (letter != c) {
                if (i - earlyBeg > max) {
                    max = i - earlyBeg;
                    mc = letter;
                }
                earlyBeg = i;
                letter = c;
            }
        }
        if(strLength - earlyBeg > max){
            max = strLength - earlyBeg;
            mc = c;
        }
            return new Pair<>(mc, max);
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
