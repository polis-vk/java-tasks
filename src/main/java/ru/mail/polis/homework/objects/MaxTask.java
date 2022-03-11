package ru.mail.polis.homework.objects;

import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;

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
        if (array == null || count > array.length) {
            return null;
        }
        if (count == 0) {
            return new int[0];
        }
        int[] result = Arrays.copyOf(array, count);
        int index = -1;
        boolean flag = false;
        for (int i = count; i < array.length; i++) {
            if (!flag) {
                index = minValueIndex(result);
                flag = true;
            }
            if (array[i] >= result[index]) {
                result[index] = array[i];
                flag = false;
            }
        }
        reverse(result);
        return result;
    }

    static int minValueIndex(int[] array) {
        int index = 0;
        for (int i = 1; i < array.length; i++) {
            if (array[i] < array[index]) {
                index = i;
            }
        }
        return index;
    }

    public static void reverse(int[] array) {
        for (int i = 0; i < array.length; i++) {
            array[i] = -1 * array[i];
        }
        Arrays.sort(array);
        for (int i = 0; i < array.length; i++) {
            array[i] = -1 * array[i];
        }
    }
}
