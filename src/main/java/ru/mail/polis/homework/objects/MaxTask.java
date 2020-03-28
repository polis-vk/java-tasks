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
     */
    public static int[] getMaxArray(int[] array, int count) {
        if (array.length < count) {
            return null;
        }

        int[] maxArray = new int[count];
        int[] copyArray = array.clone();
        int[] temp = new int[2];
        for (int i = 0; i < count; i++) {
            temp = findMax(copyArray);
            maxArray[i] = temp[0];
            copyArray[temp[1]] = Integer.MIN_VALUE;
        }
        return maxArray;
    }

    private static int[] findMax(int[] table) {
        int maxValue = table[0];
        int indexOfMax = 0;
        for (int i = 1; i < table.length; i++) {
            if (table[i] >= maxValue) {
                indexOfMax = i;
                maxValue = table[i];
            }
        }
        int[] arrayExport = {maxValue, indexOfMax};
        return arrayExport;
    }
}


