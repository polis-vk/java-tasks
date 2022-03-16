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
     */
    // в общем я додумался только до O(n * log(count)), где log(count) - binarySearch
    public static int[] getMaxArray(int[] array, int count) {
        if (array == null || count > array.length) {
            return null;
        }
        int[] result = Arrays.copyOf(array, count);
        if (count != 0) {
            Arrays.sort(result);
            int min = result[0];
            int indexForInsert;

            for (int i = count; i < array.length; i++) {
                if (array[i] > min) {
                    indexForInsert = Arrays.binarySearch(result, array[i]);
                    indexForInsert = indexForInsert < 0 ? (-indexForInsert - 2) : indexForInsert;
                    System.arraycopy(result, 1, result, 0, indexForInsert);
                    result[indexForInsert] = array[i];
                    min = result[0];
                }
            }

            for (int i = 0; i < count / 2; i++) {
                int temp = result[i];
                result[i] = result[count - i - 1];
                result[count - i - 1] = temp;
            }
        }
        return result;
    }

}
