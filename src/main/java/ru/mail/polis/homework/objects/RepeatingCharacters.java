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
        if (str == null || str.equals("")) {
            return null;
        }
        int count = 0; // счётчик элементов в массивах
        int[] arrayRepeats = new int[str.length()]; // массив количества повторений каждого символа
        char[] arrayElems = new char[str.length()]; // массив символов
        char currentElem = str.charAt(0); // текущий элемент
        int countRepeats = 0; // количество повторений текущего элемента
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == currentElem) {
                countRepeats++;
            } else {
                // если встречаем новый элемент - заносим в массивы данные о предыдущем элементе и обновляем currentElem
                arrayElems[count] = currentElem;
                arrayRepeats[count] = countRepeats;
                countRepeats = 1;
                currentElem = str.charAt(i);
                count++;
            }
        }
        arrayElems[count] = currentElem;
        arrayRepeats[count] = countRepeats;
        int maxRepeats = 0;
        char maxElem = '\n';
        // выбираем максимальный
        for (int i = 0; i <= count; i++) {
            if (arrayRepeats[i] > maxRepeats) {
                maxRepeats = arrayRepeats[i];
                maxElem = arrayElems[i];
            }
        }
        return new Pair<>(maxElem, maxRepeats);
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
