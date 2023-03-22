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
    public static void binarySearchIndex(int[] a, int item) {
        int low = 0, high = a.length - 1;
        if (item >= a[low]) {
            offsetArray(a, low);
            a[low] = item;
        } else if (item > a[high]) {
            while (low <= high) {
                int mid = low + ((high - low) / 2);
                if (a[mid] < item) {
                    if (a[mid - 1] > item) {
                        offsetArray(a, mid);
                        a[mid] = item;
                        break;
                    }
                    high = mid - 1;
                } else if (a[mid] > item) {
                    if (a[mid + 1] < item) {
                        offsetArray(a, mid);
                        a[mid] = item;
                        break;
                    }
                    low = mid + 1;
                } else {
                    offsetArray(a, mid);
                    a[mid] = item;
                    break;
                }
            }
        }
    }

    public static void reverse(int[] array) {
        if (array == null) {
            return;
        }
        int i = 0;
        int j = array.length - 1;
        int tmp;
        while (j > i) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
            j--;
            i++;
        }
    }

    public static void offsetArray(int[] a, int startIndex) {
        if (startIndex < a.length) {
            int temp = a[startIndex];
            for (int i = startIndex + 1; i < a.length; i++) {
                int nowTmp = a[i];
                a[i] = temp;
                temp = nowTmp;
            }
        }
    }

    public static int[] getMaxArray(int[] array, int count) {
        if (array == null || array.length < count) {
            return null;
        }
        int[] finalItems = new int[count];
        if (count == 0) {
            return finalItems;
        }
        for (int i = 0; i < count; i++) {
            finalItems[i] = array[i];
        }
        Arrays.sort(finalItems);
        reverse(finalItems);
        for (int i = count; i < array.length; i++) {
            binarySearchIndex(finalItems, array[i]);
        }
        return finalItems;
    }
}
