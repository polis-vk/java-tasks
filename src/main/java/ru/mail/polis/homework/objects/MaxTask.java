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
        if (array == null || array.length < count) { return null; }

        int[] maxFromRemaining = new int[count];
        int[] indexesOfMaxNum = new int[count];
        Arrays.fill(indexesOfMaxNum,-1);
        int currMax = Integer.MIN_VALUE;
        boolean goOutFromLoop;

        for (int i = 0; i < count; i++) {
            for (int j = 0; j < array.length; j++) {
                goOutFromLoop = false;
                for (int k : indexesOfMaxNum) {
                    if (k == j) {
                        goOutFromLoop = true;
                        break;
                    }
                }
                if (goOutFromLoop) { continue; }
                if (array[j] > currMax) {
                    currMax = array[j];
                    indexesOfMaxNum[i] = j;
                }
            }
            maxFromRemaining[i] = currMax;
            currMax = Integer.MIN_VALUE;
        }
        return maxFromRemaining;
    }
}
