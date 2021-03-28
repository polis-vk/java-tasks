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
        if (str == null || str.equals("")) {
            return null;
        }

//      Довольно инересный код я написал нечаянно - ищет наиболее часто встречающийся в строке элемент
//      не зависимо от того, идет ли подряд. Решил оставить на всякий случай - вдруг вам понравится
//      Ибо сильно усложнил сво. задачу из-за невинимательного чтения задания
//        char[] symbols = new char[str.length()];
//        int[] symbolCount = new int[str.length()];
//        int numOfSymbols = 0;
//
//        for(int i = 0; i < str.length(); i++){
//            boolean isUnique = true;
//            for(int j = 0; j < numOfSymbols; j++){
//                if (symbols[j] == str.charAt(i)) {
//                    symbolCount[j] += 1;
//                    isUnique = false;
//                    break;
//                }
//            }
//            if(isUnique){
//                symbols[numOfSymbols] = str.charAt(i);
//                symbolCount[numOfSymbols] += 1;
//                numOfSymbols += 1;
//            }
//        }
//
//        int max = symbolCount[0];
//        int iMax = 0;
//        for(int i = 1; i < numOfSymbols; i++){
//            if(max < symbolCount[i]){
//                iMax = i;
//                max = symbolCount[i];
//            }
//        }
//        return new Pair<>(symbols[iMax], max);

        char maxRowChar = str.charAt(0);
        char currRowChar = str.charAt(0);
        int currRow = 1;
        int maxInRow = 0;
        for (int i = 1; i < str.length(); i++) {
            if (currRowChar == str.charAt(i)) {
                currRow += 1;
            } else {
                if (currRow > maxInRow) {
                    maxInRow = currRow;
                    maxRowChar = currRowChar;
                }
                currRow = 1;
                currRowChar = str.charAt(i);
            }
        }
        if (currRow > maxInRow) {
            maxInRow = currRow;
            maxRowChar = currRowChar;
        }
        return new Pair<>(maxRowChar, maxInRow);
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
