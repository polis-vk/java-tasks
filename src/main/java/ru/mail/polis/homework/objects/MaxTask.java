package ru.mail.polis.homework.objects;

import java.sql.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class MaxTask {

    /**
     * Вам дан массив и количество элементов в возвращаемом массиве
     * Вернуть нужно массив из count максимальных элементов array, упорядоченный по убыванию.
     * Если массив null или его длина меньше count, то вернуть null
     * Например ({1, 3, 10, 11, 22, 0}, 2) -> {22, 11}
     * ({1, 3, 22, 11, 22, 0}, 3) -> {22, 22, 11}
     * НЕЛЬЗЯ СОРТИРОВАТЬ массив array и его копии
     * 4 тугрика
     */
    public static int[] getMaxArray(int[] array, int count) {
        if (array == null || array.length < count) {
            return null;
        }

        if (array.length == 0 || count == 0) {
            return new int[0];
        }

        Integer[] maxArray = new Integer[count];

        for (int i = 0; i < count; i++) {
            maxArray[i] = array[i];
        }

        Arrays.sort(maxArray, Collections.reverseOrder());

        int midl = (int) Math.ceil((double) count / 2);

        //бинарный поиск
        for (int i = count; i < array.length; i++) {
            if (array[i] > maxArray[midl]) {
                for (int j = 0; j < midl; j++) {
                    if (array[i] > maxArray[j]) {
                        //сдвиг элементов вправо
                        for (int k = count - 1; k > j; k--) {
                            maxArray[k] = maxArray[k - 1];
                        }
                        maxArray[j] = array[i];
                        break;
                    }
                }
            } else {
                for (int j = midl; j < count; j++) {
                    if (array[i] > maxArray[j]) {
                        if (j == count - 1) {
                            maxArray[j] = array[i];
                            break;
                        }
                        //сдвиг элементов вправо
                        for (int k = count - 1; k >= j; k--) {
                            maxArray[k] = maxArray[k - 1];
                        }
                        maxArray[j] = array[i];
                        break;
                    }
                }
            }
        }

        int[] returnArray = new int[count];

        for (int i = 0; i < count; i++) {
            returnArray[i] = maxArray[i];
        }

        return returnArray;
    }
}
