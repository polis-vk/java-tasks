package ru.mail.polis.homework.objects;

import java.util.Arrays;

public class MaxTask {

    /**
     * Вам дан массив и количество элементов в возвращаемом массиве
     * Вернуть нужно массив из count максимальных элементов array, упорядоченный по убыванию.
     * Если массив null или его длина меньше count, то вернуть null
     * Например ({1, 3, 10, 11, 22, 0}, 2) -> {22, 11}
     * ({1, 3, 22, 11, 22, 0}, 3) -> {22, 22, 11}
     */
    public static int[] getMaxArray(int[] array, int count) {
        if (array == null || array.length < count) {
            return null;
        }
        int[] maxArray = new int[count];
        for (int i = 0; i < count; i++) {
            maxArray[i] = Integer.MIN_VALUE;
        }
        int[] arrayCopy = Arrays.copyOf(array, array.length);
        int temp = 0;
        for (int i = 0; i < count; i++) {
            for (int j = 0; j < arrayCopy.length; j++) {
                if (maxArray[i] < arrayCopy[j]) {
                    maxArray[i] = arrayCopy[j];
                    temp = j;
                }
            }
            arrayCopy[temp] = Integer.MIN_VALUE;
        }
        return maxArray;
    }
}
