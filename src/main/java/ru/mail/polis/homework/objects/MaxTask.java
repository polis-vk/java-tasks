package ru.mail.polis.homework.objects;

import java.util.Arrays;

public class MaxTask {

    /**
     * Вам дан массив и количество элементов в возвращаемом массиве
     * Вернуть нужно массив из count максимальных элементов array, упорядоченный по убыванию.
     * Если длина массива меньше count, то вернуть null
     * Например ({1, 3, 10, 11, 22, 0}, 2) -> {22, 11}
     * ({1, 3, 22, 11, 22, 0}, 3) -> {22, 22, 11}
     * НЕЛЬЗЯ СОРТИРОВАТЬ массив array и его копии
     *
     */
    public static int[] getMaxArray(int[] array, int count) {
        if (array.length < count)
            return null;
        int[] maxArray;
        maxArray = new int[count];
        Arrays.fill(maxArray, Integer.MIN_VALUE);
        for (int element : array) {
            for (int j = count - 1; j >= 0; j--) {
                if (element > maxArray[j]) {
                    if (j != count - 1) {
                        maxArray[j + 1] = maxArray[j];
                    }
                    maxArray[j] = element;
                }
            }
        }
        return maxArray;
    }

}
