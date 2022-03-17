package ru.mail.polis.homework.objects;

import java.util.Arrays;
import java.util.Set;

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
        int[] copyArray = array.clone();
        int[] maxArray = new int[count];
        for (int i = 0; i < count; i++) {
            int maxIndex = 0;
            int maxValue = Integer.MIN_VALUE;
            for (int j = 0; j < copyArray.length; j++) {
                if (copyArray[j] > maxValue) {
                    maxValue = copyArray[j];
                    maxIndex = j;
                }
            }
            maxArray[i] = maxValue;
            copyArray[maxIndex] = Integer.MIN_VALUE;
        }
        return maxArray;
    }
}
