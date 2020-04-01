package ru.mail.polis.homework.objects;

import java.util.Arrays;
import java.util.Collections;

public class MaxTask {

    /**
     * Вам дан массив и количество элементов в возвращаемом массиве
     * Вернуть нужно массив из count максимальных элементов array, упорядоченный по убыванию.
     * Если длина массива меньше count, то вернуть null
     * Например ({1, 3, 10, 11, 22, 0}, 2) -> {22, 11}
     * ({1, 3, 22, 11, 22, 0}, 3) -> {22, 22, 11}
     */
    public static int[] getMaxArray(int[] array, int count) {

        if (count > array.length) {
            return null;
        }
        if (count == 0) {
            return new int[0];
        }

        int[] maxElem = new int[count];
        for (int i = 0; i < count; i++) {
            maxElem[i] = array[i];
        }
        Arrays.sort(maxElem);

        int swap = 0;
        for (int j = 0; j < count / 2; j++) {
            swap = maxElem[j];
            maxElem[j] = maxElem[count - 1 - j];
            maxElem[count - 1 - j] = swap;
        }

        for (int i = count; i < array.length; i++) {
            if (array[i] > maxElem[count - 1]) {
                int min = array[i];
                for (int j = 0; j < count; j++) {
                    if (min > maxElem[j]) {
                        int temp = maxElem[j];
                        maxElem[j] = min;
                        min = temp;
                    }
                }
            }
        }
        return maxElem;
    }
}
