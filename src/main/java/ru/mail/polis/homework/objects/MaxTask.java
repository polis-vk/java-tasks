package ru.mail.polis.homework.objects;

import java.util.Arrays;

public class MaxTask {

    /**
     * Вам дан массив и количество элементов в возвращаемом массиве
     * Вернуть нужно массив из count максимальных элементов array, упорядоченный по убыванию.
     * Если массив null или его длина меньше count, то вернуть null
     * Например ({1, 3, 10, 11, 22, 0}, 2) -> {22, 11}
     * ({1, 3, 22, 11, 22, 0}, 3) -> {22, 22, 11}
     * НЕЛЬЗЯ СОРТИРОВАТЬ массив array и его копии
     * 4 тугрика
     */
    public static int[] getMaxArray(int[] array, int count) {
        if (array == null || count > array.length) {
            return null;
        } else if (array.length == 0 || count == 0) {
            return new int[0];
        }
        int[] maxArray = new int[count];
        int[] copyArray = Arrays.copyOf(array, array.length);
        for (int i = 0; i < count; i++) {
            int max = Integer.MIN_VALUE;
            int indexMax = 0;
            for (int j = 0; j < copyArray.length; j++) {
                if (copyArray[j] > max) {
                    max = copyArray[j];
                    indexMax = j;
                }
            }
            maxArray[i] = max;
            copyArray[indexMax] = Integer.MIN_VALUE;
        }
        return maxArray;
    }
}