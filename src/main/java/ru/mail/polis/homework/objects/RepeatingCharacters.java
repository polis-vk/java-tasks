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
        if (str == null || str.isEmpty())
        {
            return null;
        }
        char[] arrStr = str.toCharArray();
        char currSymbol = arrStr[0];
        char maxRepSymbol = arrStr[0];
        int currLength = 1;
        int maxLength = 1;
        for (int i = 1; i < arrStr.length; i++)
        {
            if (currSymbol == arrStr[i])
            {
                currLength++;
                if (currLength > maxLength)
                {
                    maxLength = currLength;
                    maxRepSymbol = currSymbol;
                }
            }
            else {
                currSymbol = arrStr[i];
                currLength = 1;
            }
        }
        return new Pair<>(maxRepSymbol, maxLength);
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
