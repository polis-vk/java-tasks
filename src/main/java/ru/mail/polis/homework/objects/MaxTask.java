package ru.mail.polis.homework.objects;

import java.util.Arrays;

public class MaxTask {

    /**
     * Вам дан массив и количество элементов в возвращаемом массиве
     * Вернуть нужно массив из count максимальных элементов array, упорядоченный по убыванию.
     * Если длина массива меньше count, то вернуть null
     * Например ({1, 3, 10, 11, 22, 0}, 2) -> {22, 11}
     * ({1, 3, 22, 11, 22, 0}, 3) -> {22, 22, 11}
     * НЕЛЬЗЯ СОРТИРОВАТЬ массив array и его копиии
     *
     */
    public static int[] getMaxArray(int[] array, int count) {
        if (array.length < count) {
            return null;
        }
        int[] copyOfArray = Arrays.copyOf(array, array.length);
        int[] maxArray = new int[count];
        for (int i = 0; i < count; i++) {
            int maxDigit = copyOfArray[0];
            int maxIndex = 0;
            for (int j = 1; j < copyOfArray.length; j++) {
                if (copyOfArray[j] > maxDigit) {
                    maxDigit = copyOfArray[j];
                    maxIndex = j;
                }
            }
            maxArray[i] = maxDigit;
            copyOfArray[maxIndex] = Integer.MIN_VALUE;
        }
        return maxArray;
    }

}
