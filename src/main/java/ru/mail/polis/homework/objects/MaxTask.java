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
    public static int[] getMaxArray(int[] array, int count) {
        if (array == null || array.length < count) {
            return null;
        } else if (array.length == 0 || count == 0) {
            return new int[0];
        }
        int[] maxArray = new int[count];
        Arrays.fill(maxArray, Integer.MIN_VALUE);
        for (int digit : array) {
            if (digit >= maxArray[count - 1]) {
                maxArray[count - 1] = digit;
                int j = 1;
                int temp;
                while (count - j - 1 >= 0 && maxArray[count - j] > maxArray[count - j - 1]) {
                    temp = maxArray[count - j - 1];
                    maxArray[count - j - 1] = maxArray[count - j];
                    maxArray[count - j] = temp;
                    j++;
                }
            }
        }
        return maxArray;
    }
}
