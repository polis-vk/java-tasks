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
        if (str==null||str.isEmpty()) {
            return null;
        }
        int lenght = str.length();
        int counter = 1;
        int max = Integer.MIN_VALUE;
        char mostFrequentCharacter = 0;
        for (int i = 0; i < lenght - 1; i++) {
            if (str.charAt(i) == str.charAt(i + 1)) {
                counter++;
            } else {
                if (max < counter) {
                    max = counter;
                    mostFrequentCharacter = str.charAt(i);
                    if(max>=lenght-i)
                        break;
                }
                counter = 1;
            }
        }
        if (max < counter) {
            max = counter;
            mostFrequentCharacter = str.charAt(lenght - 1);
        }
        return new Pair<>(mostFrequentCharacter, max);


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
