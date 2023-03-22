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
     * 4 тугрика
     */
    public static int[] getMaxArray(int[] array, int count) {
        if (array == null || array.length < count) {
            return null;
        }
        if (count == 0) {
            return new int[0];
        }

        int[] res = Arrays.copyOf(array, count);
        Arrays.sort(res);
        int index = 0;

        for (int i = count; i < array.length; i++) {
            if (array[i] > res[0]) {
                index = Arrays.binarySearch(res, array[i]);
                if (index < 0) {
                    index = -index - 1;
                }
                if (index != array.length) {
                    System.arraycopy(res, 1, res, 0, index - 1);
                }
                res[index - 1] = array[i];
            }
        }
        for (int j = 0; j < res.length / 2; j++) {
            int tmp = res[j];
            res[j] = res[count - j - 1];
            res[count - j - 1] = tmp;
        }
        return res;
    }
}
