package ru.mail.polis.homework.objects;


import java.util.*;

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
        LinkedHashMap<Character, Integer> temp = new LinkedHashMap<>();
        char c;
        temp.put(str.charAt(0), 1);
        for (int i = 1; i < str.length(); i++) {
            c = str.charAt(i);
            if (temp.containsKey(c) && str.charAt(i - 1) == c) {
                temp.put(c, temp.get(c) + 1);
            } else {
                temp.put(c, 1);
            }
        }
        Pair<Character, Integer> ans = new Pair<>(' ', 0);
        for (Map.Entry<Character, Integer> pair : temp.entrySet()) {
            if (pair.getValue() > ans.getSecond()) {
                ans = new Pair<>(pair.getKey(), pair.getValue());
            }
        }
        return ans;
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
