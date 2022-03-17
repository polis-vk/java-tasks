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
     */
    public static int[] getMaxArray(int[] array, int count) {
        if (count > array.length) {
            return null;
        }
        int[] maxArray = new int[count];
        int[] indexArray = new int[count];
        Arrays.fill(maxArray, Integer.MIN_VALUE);
        Arrays.fill(indexArray, Integer.MIN_VALUE);
        int checkIndex = Integer.MIN_VALUE;
        int maxElement = Integer.MIN_VALUE;
        for (int i = 0; i < count; ++i) {
            for (int j = 0; j < array.length; ++j) {
                if (array[j] > maxElement) {
                    if (!isElementRepeated(indexArray, j)) {
                        maxElement = array[j];
                        checkIndex = j;
                    }
                }
            }
            maxArray[i] = maxElement;
            indexArray[i] = checkIndex;
            maxElement = Integer.MIN_VALUE;
        }
        return maxArray;
    }

    public static boolean isElementRepeated(int[] array, int index) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == index) {
                return true;
            }
        }
        return false;
    }
}