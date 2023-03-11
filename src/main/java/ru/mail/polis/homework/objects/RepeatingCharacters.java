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
        if (str == null || str == "") {
            return null;
        }
        StringBuilder strCopy = new StringBuilder();
        StringBuilder strRepeat = new StringBuilder();
        strCopy.append(str);
        if (strCopy.length() == 1) {
            return new Pair<>(strCopy.charAt(0), 1);
        }
        char repSym = '0';
        int repeat = 1;
        int maxRepeat = 0;
        for (int i = 1; i < strCopy.length(); i++) {
            char prevSym = strCopy.charAt(i - 1);
            char curSym = strCopy.charAt(i);
            if (prevSym == curSym) {
                strRepeat.append(prevSym);
                repeat++;
                if (repeat > maxRepeat) {
                    maxRepeat = repeat;
                    repSym = prevSym;
                }
                if (repeat == strCopy.length()) {
                    new Pair<>(prevSym, repeat);
                }
            } else if (repeat > maxRepeat) {
                maxRepeat = repeat;
                repSym = prevSym;
                repeat = 1;
                strRepeat = new StringBuilder();
            } else {
                repeat = 1;
                strRepeat = new StringBuilder();
            }
        }
        return new Pair<>(repSym, maxRepeat);
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
