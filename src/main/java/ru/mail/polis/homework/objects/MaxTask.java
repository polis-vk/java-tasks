package ru.mail.polis.homework.objects;

public class MaxTask {

    /**
     * Вам дан массив и количество элементов в возвращаемом массиве
     * Вернуть нужно массив из count максимальных элементов array, упорядоченный по убыванию.
     * Если массив null или его длина меньше count, то вернуть null
     * Например ({1, 3, 10, 11, 22, 0}, 2) -> {22, 11}
     * ({1, 3, 22, 11, 22, 0}, 3) -> {22, 22, 11}
     * НЕЛЬЗЯ СОРТИРОВАТЬ массив array и его копии
     */
    public static int[] getMaxArray(int[] array, int count) {
        if (array == null || array.length < count) {
            return null;
        }
        if (count == 0) {
            return new int[0];
        }
        int[] result = new int[count];
        int resultSize = 0;
        int maximum = Integer.MIN_VALUE;
        int countMaximum = 1;
        int previousMaximum = Integer.MAX_VALUE;
        while (resultSize != count) {
            for (int element : array) {
                if (element > maximum && element < previousMaximum) {
                    maximum = element;
                    countMaximum = 1;
                } else if (element == maximum) {
                    countMaximum += 1;
                }
            }
            if (countMaximum <= count - resultSize) {
                for (int i = 0; i < countMaximum; i++) {
                    result[resultSize++] = maximum;
                }
            } else {
                for (int i = 0; i < count - resultSize; i++) {
                    result[resultSize++] = maximum;
                }
                break;
            }
            previousMaximum = maximum;;
            maximum = Integer.MIN_VALUE;
        }
        return result;
    }
}
