package ru.mail.polis.homework.objects;

import java.util.Arrays;

public class MaxTask {

    /**
     * Вам дан массив и количество элементов в возвращаемом массиве
     * Вернуть нужно массив из count максимальных элементов array, упорядоченный по убыванию.
     * Если массив null или его длина меньше count, то вернуть null
     * Например ({1, 3, 10, 11, 22, 0}, 2) -> {22, 11}
     * ({1, 3, 22, 11, 22, 0}, 3) -> {22, 22, 11}
     * //* НЕЛЬЗЯ СОРТИРОВАТЬ массив array и его копии
     */
    public static int[] getMaxArray(int[] array, int count) {
        if (array == null || array.length < count) {
            return null;
        }
        int[] newArray = new int[count];
        int[] copyArray = array;
        if (count == 0) {
            return newArray;
        }
        for (int i = 0; i < count; i++) {
            int currentMax = copyArray[0];
            int maxIndex = 0;
            for (int j = 1; j < copyArray.length; j++) {
                if (copyArray[j] > currentMax) {
                    currentMax = copyArray[j];
                    maxIndex = j;
                }
            }
            copyArray[maxIndex] = Integer.MIN_VALUE;
            newArray[i] = currentMax;
        }
        return newArray;
    }
}
