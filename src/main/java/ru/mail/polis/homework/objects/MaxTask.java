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
        if (array == null || count > array.length) {
            return null;
        }
        int[] maxNums = new int[count];
        Arrays.fill(maxNums, Integer.MIN_VALUE);
        for (int j : array) {
            int insertPosition = binarySearch(maxNums, 0, count - 1, j);
            if (insertPosition == -1) {
                continue;
            }
            shift(maxNums, insertPosition, count - 1);
            maxNums[insertPosition] = j;
        }
        return maxNums;
    }

    public static void shift(int[] arr, int num, int length) {
        for (int i = length; i > num; i--) {
            arr[i] = arr[i - 1];
        }
    }

    public static int binarySearch(int[] arr, int low, int high, int num) {
        if (high >= low) {
            int middle = low + (high - low) / 2;
            if (middle == 0 && arr[middle] <= num || middle != 0 && arr[middle] <= num && arr[middle - 1] >= num) {
                return middle;
            }
            if (arr[middle] < num) {
                return binarySearch(arr, low, middle - 1, num);
            }
            return binarySearch(arr, middle + 1, high, num);
        }
        return -1;
    }

}
