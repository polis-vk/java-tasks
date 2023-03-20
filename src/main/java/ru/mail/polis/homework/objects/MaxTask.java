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
        if (array == null || array.length < count) {
            return null;
        }

        if (array.length == 0 || count == 0) {
            return new int[0];
        }

        int[] maxArray = new int[count];

        Arrays.fill(maxArray, Integer.MIN_VALUE);
        binarySearch(array, maxArray, count);

        return maxArray;
    }

    private static void binarySearch(final int[] array, int[] maxArray, final int count) {
        int left = 0;
        int right = 0;
        int midl = 0;

        for (int i = 0; i < array.length; i++) {
            left = 0;
            right = count - 1;
            midl = (left + right) / 2;

            if (array[i] <= maxArray[count - 1]) {
                continue;
            }

            while (right >= left) {
                if (array[i] > maxArray[midl]) {
                    right = midl - 1;
                } else {
                    left = midl + 1;
                }
                midl = (left + right) / 2;
            }

            System.arraycopy(maxArray, left, maxArray, left + 1, count - left - 1);
            maxArray[left] = array[i];
        }
    }
}