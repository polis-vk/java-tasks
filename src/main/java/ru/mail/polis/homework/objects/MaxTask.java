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

        int[] result = Arrays.copyOf(array, count);
        Arrays.sort(result);
        int min = result[0];
        int insertId;
        int curId = count;

        do {
            if (array[curId] <= min) {
                continue;
            }
            insertId = Arrays.binarySearch(result, array[curId]);
            if (insertId >= 0) {
                System.arraycopy(result, 1, result, 0, insertId);
            } else {
              insertId = -(insertId + 1);
              System.arraycopy(result, 1, result, 0, insertId - 1);
              result[insertId - 1] = array[curId];
            }
            min = result[0];
            curId++;
        } while (curId != array.length);

        int temp;
        for (int i = 0; i < count / 2; i++) {
            temp = result[i];
            result[i] = result[count - i - 1];
            result[count - i - 1] = temp;
        }

        return result;
    }

}
