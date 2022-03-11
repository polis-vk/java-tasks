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
     *
     */
    public static int[] getMaxArray(int[] array, int count) {
        if ((array == null) || (array.length < count)) {
            return null;
        }

        int[] maxArray = new int[count];
        int[] indexesMax = new int[count];
        int currentMax = Integer.MIN_VALUE;
        boolean indicator = false;
        int a = 0;
        Arrays.fill(indexesMax,-1);

        for (int i = 0; i < count; i++) {
            for (int j = 0; j < array.length; j++) {

                for (int k = 0; k < indexesMax.length; k++) {
                    if (i == 0) { break; }
                    if (j == indexesMax[k]) {
                        indicator = true;
                        break;
                    }
                }

                if (indicator) {
                    indicator = false;
                    continue;
                }

                if (array[j] > currentMax) {
                    currentMax = array[j];
                    a = j;
                }
            }
            indexesMax[i] = a;
            maxArray[i] = currentMax;
            currentMax = Integer.MIN_VALUE;
        }
        return maxArray;
    }

}
