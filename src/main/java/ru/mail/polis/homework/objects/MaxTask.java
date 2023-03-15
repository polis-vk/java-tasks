package ru.mail.polis.homework.objects;

import java.lang.reflect.Array;
import java.util.Arrays;

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

        int length = array.length;
        String[] arrayStr = new String[length];

        for (int i = 0; i < length; i++) {
            arrayStr[i] = String.valueOf(array[i]);
        }

        int maxValue = Integer.MIN_VALUE;
        int index = 0;
        int[] maxArray = new int[count];

        int temp = 0;
        int cnt = 0;  //count
        while (cnt < count) {
            for (int i = 0; i < length; i++) {
                if (arrayStr[i] == null) {
                    continue;
                }

                temp = Integer.parseInt(arrayStr[i]);
                if (maxValue < temp) {
                    maxValue = temp;
                    index = i;
                }
            }

            maxArray[cnt] = maxValue;
            arrayStr[index] = null;
            cnt++;
            maxValue = Integer.MIN_VALUE;
        }

        return maxArray;
    }
}
