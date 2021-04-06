package ru.mail.polis.homework.objects;

import java.util.ArrayList;

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
        //ArrayList<Integer> copyArray = new ArrayList<>();
        int[] tempArray = new int[array.length];
        System.arraycopy(array, 0, tempArray, 0, array.length);
        int copyCount = count;
        int[] result = new int[copyCount];
        if (tempArray.length < copyCount) {
            return null;
        }
        int max = Integer.MIN_VALUE;
        int index = 1;
        for (int ind = 0; ind <= copyCount - 1; ind++){
            for (int i = 0; i < tempArray.length; i++) {
                if (tempArray[i] > max) {
                    max = tempArray[i];
                    index = i;
                }
            }
            result[ind] = max;
            tempArray[index] = Integer.MIN_VALUE;
            max = Integer.MIN_VALUE;
        }

        return result;
    }

}
