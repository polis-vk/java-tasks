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
        if (str.equals("")) {
            return null;
        }

        char[] arrayFromStr = str.toCharArray();
        Pair[] arrayOfPair = new Pair[str.length()];
        int countRepeat = 1;
        int length = arrayFromStr.length;

        for (int i = 1, j = 0; i <= length; i++) {
            if (i != length && arrayFromStr[j] == arrayFromStr[i]) {
                countRepeat++;
            } else {
                arrayOfPair[j] = new Pair<>(arrayFromStr[j], countRepeat);
                j = i;
                countRepeat = 1;
            }
        }

        int maxCountOfRepetition = (int) arrayOfPair[0].getSecond();
        int index = 0;

        for (int i = 1; i < length; i++) {
            if (arrayOfPair[i] != null && maxCountOfRepetition < (int) arrayOfPair[i].getSecond()) {
                maxCountOfRepetition = (int) arrayOfPair[i].getSecond();
                index = i;
            }
        }

        return arrayOfPair[index];
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
