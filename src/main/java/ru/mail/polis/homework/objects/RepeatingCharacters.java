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
        if (str == null || str.isEmpty()) {
            return null;
        }

        int currentMax = 1;
        int currentLength = 1;
        int indexMax = 0;

        for (int i = 1; i < str.length(); i++) {
            if (str.charAt(i) == str.charAt(i - 1)) {
                currentLength++;
                continue;
            }
            indexMax = currentLength > currentMax ? i - 1 : indexMax;
            currentMax = Math.max(currentLength, currentMax);
            currentLength = 1;

            //Досрочный выход из цикла, если максимум точно найден - если длина оставшейся строки меньше, чем текущий максимум
            //Условие не можем записать в for так как мб ситуация "aaaссссса" и выход из цикла будет до нахождения максимума
            //Корректно проверять условие каждый раз после нахождения длины подряд идущих символов
            if (str.length() - i  < currentMax){
                break;
            }
        }

        indexMax = currentLength > currentMax ? str.length() - 1 : indexMax;
        currentMax = Math.max(currentLength, currentMax);

        return new Pair<>(str.charAt(indexMax), currentMax);
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
