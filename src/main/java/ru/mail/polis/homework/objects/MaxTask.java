package ru.mail.polis.homework.objects;

import java.util.Arrays;

public class MaxTask {

    /**
     * Вам дан массив и количество элементов в возвращаемом массиве
     * Вернуть нужно массив из count максимальных элементов array, упорядоченный по убыванию.
     * Если длина массива меньше count, то вернуть null
     * Например ({1, 3, 10, 11, 22, 0}, 2) -> {22, 11}
     * ({1, 3, 22, 11, 22, 0}, 3) -> {22, 22, 11}
     */
    public static int[] getMaxArray(int[] array, int count) {
        if (array.length < count) {
            return null;
        }
        if (count == 0) {
            return new int[0];
        }
        int[] resultArray = Arrays.copyOf(array, count);
        Arrays.sort(resultArray);
        for (int i = count; i < array.length; i++) {
            if (array[i] > resultArray[0]) {
                int j = 0;
                while (j < count - 1 && array[i] > resultArray[j + 1]) {
                    j++;
                }
                swap(resultArray, j, array[i]);
            }
        }
        return reverse(resultArray);
    }

    public static void swap(int[] resultArray, int index, int newElement) {
        for (int i = 0; i < index; i++) {
            resultArray[i] = resultArray[i + 1];
        }
        resultArray[index] = newElement;
    }

    public static int[] reverse(int[] resultArray) {
        for (int i = 0; i < resultArray.length / 2; i++) {
            int buf = resultArray[i];
            resultArray[i] = resultArray[resultArray.length - 1 - i];
            resultArray[resultArray.length - 1 - i] = buf;
        }
        return resultArray;
    }
}
