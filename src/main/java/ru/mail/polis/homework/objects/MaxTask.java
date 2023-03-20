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
        sortReverse(result);
        for (int i = count; i < array.length; i++) {
            if (array[i] <= result[count - 1]) {
                continue;
            }

            int pos = findPLace(result, array[i]);
            if (pos < 0) { // if 'key' wasn't found
                pos = -(pos + 1);
            } else {
                pos += 1;
            }
            insertWithShift(result, pos, array[i]);
        }

        return result;
    }

    private static void sortReverse(int[] array) {
        Arrays.sort(array);
        for (int i = 0; i < array.length / 2; i++) {
            int temp = array[i];
            array[i] = array[array.length - 1 - i];
            array[array.length - 1 - i] = temp;
        }
    }

    private static int findPLace(int[] array, int key) {
        int left = 0;
        int right = array.length - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (array[mid] == key) {
                return mid;
            } else if (array[mid] < key) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return -(left + 1);
    }

    private static void insertWithShift(int[] array, int pos, int key) {
        int prev = array[pos];
        for (int i = pos + 1; i < array.length; i++) {
            int temp = array[i];
            array[i] = prev;
            prev = temp;
        }
        array[pos] = key;
    }
}
