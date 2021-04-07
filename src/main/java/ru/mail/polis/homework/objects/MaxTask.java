package ru.mail.polis.homework.objects;

import java.util.Arrays;

public class MaxTask {

    /**
     * Вам дан массив и количество элементов в возвращаемом массиве
     * Вернуть нужно массив из count максимальных элементов array, упорядоченный по убыванию.
     * Если длина массива меньше count, то вернуть null
     * Например ({1, 3, 10, 11, 22, 0}, 2) -> {22, 11}
     * ({1, 3, 22, 11, 22, 0}, 3) -> {22, 22, 11}
     * НЕЛЬЗЯ СОРТИРОВАТЬ массив array и его копиии
     */
    public static int[] getMaxArray(int[] array, int count) {
        if (array.length < count) {
            return null;
        }
        if (count == 0) {
            return new int[]{};
        }
        int[] maxArray = new int[count];
        Arrays.fill(maxArray, Integer.MIN_VALUE);
        for (int value : array) {
            if (value > maxArray[maxArray.length - 1]) {
                addValue(maxArray, value);
            }
        }
        return maxArray;
    }

    private static void addValue(int[] maxArray, int value) {
        for (int i = 0; i < maxArray.length; i++) {
            if (value > maxArray[i]) {
                int temp = maxArray[i];
                maxArray[i] = value;
                value = temp;
            }
        }
    }
}
