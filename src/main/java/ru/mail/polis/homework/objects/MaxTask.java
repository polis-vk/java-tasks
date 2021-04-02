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
     *
     */
    public static int[] getMaxArray(int[] array, int count) {
        if (array.length < count) {
            return null;
        }
        if (count == 0) {
            return new int[0];
        }

        int[] decArray = new int[count];
        Arrays.fill(decArray, Integer.MIN_VALUE);  //Надеюсь можно использовать

        for (int elemOfArray : array) {
            insertToDescendingSortArray(decArray, elemOfArray);
        }
        return decArray;
    }

    private static void insertToDescendingSortArray(int[] array, int value) {
        if (array[array.length - 1] >= value) {
            return;
        }

        int indexElementSmallerValue = array.length - 1;
        for (int i = 0; i < array.length; i++) {
            if (value > array[i]) {
                indexElementSmallerValue = i;
                break;
            }
        }
        for (int i = array.length - 1; i > indexElementSmallerValue; i--) {
            array[i] = array[i - 1];
        }
        array[indexElementSmallerValue] = value;
    }
}
