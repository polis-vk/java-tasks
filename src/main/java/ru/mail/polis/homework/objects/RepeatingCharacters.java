package ru.mail.polis.homework.objects;


import java.util.HashMap;
import java.util.Map;
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
        if (str == null) {
            return null;
        } else if (str.length() == 0) {
            return null;
        }

        Integer maxCount = 0;
        Integer tmpCount = 1;
        Character character = null;
        Character tmpCharacter = null;

        for (int i = 1; i <= str.length(); i++) {
            if (i < str.length() && str.charAt(i) == str.charAt(i - 1)) {
                tmpCharacter = str.charAt(i);
                tmpCount++;
            } else {
                if (tmpCount > maxCount) {
                    maxCount = tmpCount;
                    character = tmpCharacter;
                }
                tmpCount = 1;
            }
        }
        if (character == null) {
            character = str.charAt(0); //нет повторов
        }
        return new Pair(character, maxCount);
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
