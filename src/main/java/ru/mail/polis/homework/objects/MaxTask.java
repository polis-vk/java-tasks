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
        int[] tempArray = Arrays.copyOf(array,array.length);
        int[] resultArray = new int[count];
        for (int i = 0; i < count; i++) {
            int maxValue = tempArray[i];
            int indexMaxValue = i;
            for (int j = i; j < tempArray.length; j++) {
                if (tempArray[j] > maxValue) {
                    maxValue = tempArray[j];
                    indexMaxValue = j;
                }
            }
            int temp = tempArray[i];
            tempArray[i] = maxValue;
            tempArray[indexMaxValue] = temp;
            resultArray[i] = maxValue;
        }
        return resultArray;
    }
}
