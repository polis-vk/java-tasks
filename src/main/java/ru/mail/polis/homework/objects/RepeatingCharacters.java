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
        if (str == null || str.equals("")){
            return null;
        }
        int maxCharactersCount = 1;
        int sequenceCharactersCount = 1;
        char desiredCharacter = str.charAt(0);
        for (int i = 0; i < str.length() - 1; i++){
            if (str.charAt(i) == str.charAt(i + 1)){
                sequenceCharactersCount++;
            }
            else {
                if (sequenceCharactersCount > maxCharactersCount){
                    desiredCharacter = str.charAt(i);
                    maxCharactersCount = sequenceCharactersCount;
                }
                sequenceCharactersCount = 1;
            }
            if (i == str.length() - 2 && str.charAt(i) == str.charAt(i+1)){
                if (sequenceCharactersCount > maxCharactersCount){
                    desiredCharacter = str.charAt(i);
                    maxCharactersCount = sequenceCharactersCount;
                }
            }
        }
        return new Pair<>(desiredCharacter, maxCharactersCount);
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
