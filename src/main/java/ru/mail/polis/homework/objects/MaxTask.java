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
        int result[] = Arrays.copyOf(array, count);
        Arrays.sort(result);
        int insertPos;
        for (int i = count; i < array.length; i++) {
            if (array[i] < result[0]) {
                continue;
            }
            insertPos = Arrays.binarySearch(result, array[i]);
            insertPos = insertPos >= 0 ? insertPos : -insertPos - 2;
            System.arraycopy(result, 1, result, 0, insertPos);
            result[insertPos] = array[i];
        }
        int temp;
        for (int i = 0; i < count / 2; i++) {
            temp = result[i];
            result[i] = result[count - i - 1];
            result[count - i - 1] = temp;
        }
        return result;
    }

}
