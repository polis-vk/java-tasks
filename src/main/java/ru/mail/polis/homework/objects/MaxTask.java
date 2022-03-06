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
        if (count == 0) {
            return new int[0];
        }
        if (count > array.length) {
            return null;
        }
        int[] ans = Arrays.copyOf(array, count);
        int index = -1;
        boolean flag = false;
        for (int i = count; i < array.length; i++) {
            if (!flag) {
                index = minValueIndex(ans);
                flag = true;
            }
            if (array[i] > ans[index]) {
                ans[index] = array[i];
                flag = false;
            }
        }
        return mySort(ans);
    }

    static int minValueIndex(int[] array) {
        int index = 0;
        for (int i = 1; i < array.length; i++) {
            if (array[i] < array[index]) index = i;
        }
        return index;
    }

    static int[] mySort(int[] array) {
        int temp;
        boolean flag;
        int[] current = Arrays.copyOf(array, array.length);
        for (int i = 0; i < array.length - 1; i++) {
            flag = false;
            for (int j = 0; j < array.length - i - 1; j++) {
                if (current[j] < current[j + 1]) {
                    temp = current[j];
                    current[j] = current[j + 1];
                    current[j + 1] = temp;
                    flag = true;
                }
            }
            if (!flag) {
                break;
            }
        }
        return current;
    }
}
