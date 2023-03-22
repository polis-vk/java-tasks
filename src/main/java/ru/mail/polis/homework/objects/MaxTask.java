package ru.mail.polis.homework.objects;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

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

        int[] result = Arrays.copyOf(array, count);
        Arrays.sort(result);

        for (int i = count; i < array.length; i++) {
            if (result[0] > array[i]) {
                continue;
            }
            int index = Arrays.binarySearch(result, array[i]);
            if (index < 0) {
                index *= (-1);
                index = Math.min(index - 2, result.length - 1);
            } else {
                index -= 1;
            }
            if (index < 0) {
                continue;
            }
            for (int j = 1; j <= index; j++) {
                result[j - 1] = result[j];
            }
            result[index] = array[i];
        }

        for (int i = 0; i < (result.length + 1) / 2; i++) {
            int temp = result[i];
            result[i] = result[result.length - 1 - i];
            result[result.length - 1 - i] = temp;
        }
        return result;
    }
}

