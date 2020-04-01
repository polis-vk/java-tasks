package ru.mail.polis.homework.objects;

import java.lang.reflect.Array;
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

        int[] indexMax = new int[count];
        int[] copyArray = array.clone();

        for (int j = 0; j < count; j++) {
            int maxNumber = Integer.MIN_VALUE;
            for (int i = 0; i < copyArray.length; i++) {
                if (copyArray[i] > maxNumber) {
                    maxNumber = copyArray[i];
                    indexMax[j] = i;
                }
            }
            copyArray[indexMax[j]] = Integer.MIN_VALUE;
        }

        int[] maxOfArray = new int[count];
        for (int i = 0; i < count; i++) {
            maxOfArray[i] = array[indexMax[i]];
        }
        return maxOfArray;
    }

}


