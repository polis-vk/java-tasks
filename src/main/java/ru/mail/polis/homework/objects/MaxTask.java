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
     */
    public static int[] getMaxArray(int[] array, int count) {
        if (array == null || count > array.length) {
            return null;
        }
        int[] result = new int[count];
        int fillIndexResult = 0;
        int[] copyArray = Arrays.copyOf(array, array.length);
        int maxValue = Integer.MIN_VALUE;
        int indexMaxValue = -1;
        for (int i = 0; i < count; ++i) {
            for (int j = 0; j < copyArray.length; ++j) {
                if (maxValue < copyArray[j]) {
                    maxValue = copyArray[j];
                    indexMaxValue = j;
                }
            }
            result[fillIndexResult++] = maxValue;
            copyArray[indexMaxValue] = Integer.MIN_VALUE;
            maxValue = Integer.MIN_VALUE;
        }
        return result;
    }
}
