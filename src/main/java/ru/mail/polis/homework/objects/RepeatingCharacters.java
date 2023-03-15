package ru.mail.polis.homework.objects;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
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
        if (str == null || str.isEmpty()) {
            return null;
        }

        CharacterIterator it = new StringCharacterIterator(str);
        char currentSymbol = it.current();
        it.next();
        char mostOftenSymbol = currentSymbol;
        int count = 1;
        int maxCount = 1;

        while (it.current() != CharacterIterator.DONE) {
            if (it.current() == currentSymbol) {
                ++count;
                if (count > maxCount) {
                    mostOftenSymbol = currentSymbol;
                    maxCount = count;
                }
            } else {
                currentSymbol = it.current();
                count = 1;
            }
            it.next();
        }
        return new Pair<>(mostOftenSymbol, maxCount);
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
