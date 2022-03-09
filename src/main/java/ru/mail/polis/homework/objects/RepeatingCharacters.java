package ru.mail.polis.homework.objects;

import java.lang.String;
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
            return null; // если строка пустая или nullptr вернуть null
        }
        if (str.length() == 1) {
            return new Pair<>(str.charAt(0), 1); // если в строке 1 символ, вернуть его же
        }
        int count = 1;
        int recordLength = 0;
        char symbol = str.charAt(0);
        for (int i = 0; i < str.length() - 1; i++) {
            if (str.charAt(i) == str.charAt(i + 1)) { // если следующий элемент равен элементу в данный момент
                count += 1;
                if (count > recordLength) {
                    recordLength = count; // обновляю рекордную длину
                    symbol = str.charAt(i); // заменяю на новый рекордный по длине элемент
                }
                continue; // продолжаю дальше цикл, тк след элемент из условия выше точно равен нынешнему
            }
            count = 1;
            if (str.length() - 1 - i <= recordLength) { // если длина рекордной строки больше, чем количество оставшихся элементов
                break; // выхожу досрочно из цикла
            }
        }
        if (recordLength == 0) {
            return new Pair<>(str.charAt(0), 1);
        }
        return new Pair<>(symbol, recordLength);
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
