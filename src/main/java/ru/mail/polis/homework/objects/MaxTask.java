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
    private static void enterToArray(int[] array, int position, int elem) {
        // вставка элемента в массив
        // необходимо сначала сместить элементы справа от вставляемого на одну позицию
        for (int i = array.length - 1; i > position; i--) {
            array[i] = array[i - 1];
        }
        array[position] = elem;
    }

    private static int binarySearch(int[] sortedArray, int value, int high) {
        // мы ищем число, которое больше value, а число справа от большего числа - меньше, чем value
        // например: value = 10, sortedArray = {20, 19, 18, 9, 8} - нам нужно вставить 10 между 18 и 9
        int low = 0;
        int index = -1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (sortedArray[mid] < value) {
                high = mid - 1;
            } else if (sortedArray[mid] >= value) {
                if (mid < sortedArray.length - 1) {
                    if (sortedArray[mid + 1] <= value) {
                        index = mid + 1;
                        break;
                    }
                    low = mid + 1;
                } else {
                    index = mid;
                    break;
                }
            }
        }
        return index;
    }

    public static int[] getMaxArray(int[] array, int count) {
        if (array == null || count > array.length) {
            return null;
        }
        if (count == 0) {
            return new int[count];
        }
        int[] result = new int[count];
        Arrays.fill(result, -10);
        for (int i = 0; i < array.length; i++) {
            // если элемент больше максимального - можно сразу вставлять в начало
            // если же элемент меньше минимального - то вообще не пытаемся вставлять
            if (array[i] > result[0]) {
                enterToArray(result, 0, array[i]);
            } else if (array[i] > result[result.length - 1]) {
                enterToArray(result, binarySearch(result, array[i], result.length), array[i]);
            }
        }
        return result;
    }
}
