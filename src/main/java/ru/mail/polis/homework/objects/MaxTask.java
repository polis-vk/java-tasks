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
        int[] retArray = new int[count];
        int[] alreadyAddedIndexes = new int[count];
        Arrays.fill(alreadyAddedIndexes, -1);
        for (int i = 0; i < retArray.length; i++) {
            int maxNum = Integer.MIN_VALUE;
            int maxNumIndex = -1;
            for (int j = 0; j < array.length; j++) {
                boolean flag = false;
                for (int addedIndex : alreadyAddedIndexes) {
                    if (j == addedIndex) {
                        flag = true;
                        break;
                    }
                }
                if (flag) {
                    continue;
                }

                if (array[j] >= maxNum) {
                    maxNum = array[j];
                    maxNumIndex = j;
                }
            }

            retArray[i] = maxNum;
            alreadyAddedIndexes[i] = maxNumIndex;
        }
        return retArray;
    }

}
