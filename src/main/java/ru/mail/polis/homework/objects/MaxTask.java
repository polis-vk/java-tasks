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
        int[] arrayMax = null;
        if (array != null && count <= array.length) {
            arrayMax = Arrays.copyOf(array, count);
            if (count == 0) {
                return arrayMax;
            }
            Arrays.sort(arrayMax);
            arrayMax = reverse(arrayMax);
            int position;
            for (int arrayCounter = count; arrayCounter < array.length; arrayCounter++) {
                if (array[arrayCounter] >= arrayMax[count - 1]) {
                    position = binarySearchIndex(arrayMax, array[arrayCounter]);
                    System.arraycopy(arrayMax, position, arrayMax, position + 1, count - (position + 1));
                    arrayMax[position] = array[arrayCounter];
                }
            }
        }
        return arrayMax;
    }

    private static int[] reverse(int[] array) {
        int[] reverseArray = new int[array.length];
        for (int counter = 0; counter < array.length; counter++) {
            reverseArray[array.length - 1 - counter] = array[counter];
        }
        return reverseArray;
    }

    private static int binarySearchIndex(int[] array, int value) {
        int left = 0;
        int right = array.length - 1;
        int middle;
        while (left <= right) {
            middle = (left + right) / 2;
            if (value == array[middle]) {
                return middle;
            }
            if (value < array[middle] && value >= array[middle + 1]) {
                return middle + 1;
            }
            if (value > array[middle]) {
                right = middle - 1;
                continue;
            }
            left = middle + 1;
        }
        return 0;
    }
}
