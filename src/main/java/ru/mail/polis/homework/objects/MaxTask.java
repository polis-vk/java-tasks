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
        int[] result = new int[count];
        Arrays.fill(result, Integer.MIN_VALUE);
        for (int el : array) {
            for (int k = 0; k < result.length; k++) {
                if (el > result[k]) {
                    insert(result, k, el);
                    break;
                }
            }
        }
        return result;
    }

    static void insert(int[] arr, int pos, int value) {
        if (pos != arr.length - 1) {
            for (int i = arr.length - 1; i > pos; i--) {
                if (arr[i - 1] == Integer.MIN_VALUE){
                    continue;
                }
                arr[i] = arr[i - 1];
            }
        }
        arr[pos] = value;
    }

}
