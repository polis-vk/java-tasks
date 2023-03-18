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
        if (count == 0) {
            return new int[0];
        }
        int[] result = Arrays.copyOf(array, count);
        reversedQuickSort(result, 0, count - 1);
        int insertPos;
        for (int i = count; i < array.length; i++) {
            if (array[i] < result[count - 1]) {
                continue;
            }
            insertPos = binarySearch(result, array[i]);
            System.arraycopy(result, insertPos, result, insertPos + 1, count - insertPos - 1);
            result[insertPos] = array[i];
        }
        return result;
    }

    static void reversedQuickSort(int[] array, int low, int high) {
        if (array.length == 0 || low >= high) {
            return;
        }
        int middle = low + (high - low) / 2;
        int border = array[middle];
        int i = low;
        int j = high;
        int swap;
        while (i <= j) {
            while (array[i] > border) {
                i++;
            }
            while (array[j] < border) {
                j--;
            }
            if (i <= j) {
                swap = array[i];
                array[i] = array[j];
                array[j] = swap;
                i++;
                j--;
            }
        }
        if (low > j) {
            reversedQuickSort(array, low, j);
        }
        if (high < i) {
            reversedQuickSort(array, i, high);
        }
    }

    static int binarySearch(int[] sortedArray, int element) {
        int left = 0;
        int right = sortedArray.length - 1;
        int middle;
        int greater;
        int less;
        while (left <= right) {
            middle = (left + right) / 2;
            greater = sortedArray[middle];
            less = sortedArray[middle + 1];
            if (greater > element && less <= element) {
                return middle + 1;
            }
            if (greater < element) {
                right = middle - 1;
            } else {
                left = middle + 1;
            }
        }
        return 0;
    }

}
