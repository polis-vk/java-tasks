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
        if (array == null) {
            return null;
        }
        int arrLength = array.length;
        if (arrLength < count) {
            return null;
        }
        if (count == 0) {
            return new int[count];
        }
        int[] result = Arrays.copyOf(array, count);
        Arrays.sort(result);
        int min = result[0];
        int pos;
        for (int i = count; i < arrLength; i++) {
            if (array[i] <= min) {
                continue;
            }
            pos = Arrays.binarySearch(result, array[i]);
            if (pos < 0) {
                pos = -pos - 1;
            }
            System.arraycopy(result, 1, result, 0, pos - 1);
            result[pos - 1] = array[i];
            min = result[0];
        }
        for (int i = 0; i < count / 2; i++) {
            int temp = result[i];
            result[i] = result[count - (i + 1)];
            result[count - (i + 1)] = temp;
        }
        return result;
    }
}
