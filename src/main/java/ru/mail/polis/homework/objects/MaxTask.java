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
        if (array == null || array.length < count) {
            return null;
        }
        int[] result = new int[count];
        int[] oldIndexes = new int[count];
        Arrays.fill(oldIndexes, -1);
        int maxIndex = 0;
        int maxNumber = Integer.MIN_VALUE;
        for (int j = 0; j < count; ++j) {

            for (int i = 0; i < array.length; ++i) {

                for (int k = 0; k < oldIndexes.length; ++k) {
                    if (i == oldIndexes[k]) {
                        ++i;
                        k = -1;
                    }
                }

                if (i >= array.length) {
                    break;
                }

                if (array[i] >= maxNumber) {
                    maxNumber = array[i];
                    maxIndex = i;
                }

            }
            result[j] = maxNumber;
            oldIndexes[j] = maxIndex;
            maxNumber = Integer.MIN_VALUE;
        }

        return result;
    }

}
