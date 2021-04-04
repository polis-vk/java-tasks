package ru.mail.polis.homework.objects;

import java.lang.reflect.Array;

public class MaxTask {

    /**
     * Вам дан массив и количество элементов в возвращаемом массиве
     * Вернуть нужно массив из count максимальных элементов array, упорядоченный по убыванию.
     * Если длина массива меньше count, то вернуть null
     * Например ({1, 3, 10, 11, 22, 0}, 2) -> {22, 11}
     * ({1, 3, 22, 11, 22, 0}, 3) -> {22, 22, 11}
     * НЕЛЬЗЯ СОРТИРОВАТЬ массив array и его копиии
     *
     * @return
     */
    public static int[] getMaxArray(int[] array, int count) {
        if (Array.getLength(array) < count) {
            return null;
        }

        int[] result = new int[count];
        if (count == 0) {
            return result;
        }

        int[] index = new int[count];
        for (int h = 0; h < count; h++) {
            index[h] = -1;
        }
        int pos = 0;
        while (pos != count) {
            int max = Integer.MIN_VALUE;
            for (int i = 0; i < Array.getLength(array); i++) {
                int check = 0;
                if (array[i] > max) {
                    for (int j = 0; j < count; j++) {
                        if (index[j] == i) {
                            check = 1;
                        }
                    }
                    if (check == 0) {
                        max = array[i];
                        index[pos] = i;
                    }
                }
                result[pos] = max;
            }
            pos++;
        }
        return result;
    }
}
