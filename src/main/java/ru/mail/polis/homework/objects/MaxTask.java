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
            return new int[count];
        }
        int[] maxArray = new int[count];
        for (int i = 0; i < count; i++) {
            maxArray[i] = Integer.MIN_VALUE;
        }
        int[] arrayCopy = Arrays.copyOf(array, array.length);
        for (int i = 0; i < arrayCopy.length; i++) {
            if (count - 1 < i && maxArray[0] < arrayCopy[i]) {
                maxArray[0] = arrayCopy[i];
                Arrays.sort(maxArray);
            }
            if (count > i) {
                maxArray[i] = arrayCopy[i];
            }
        }
        Arrays.sort(maxArray);
        int[] reverseArray = new int[count];
        for (int i = count - 1; i >= 0; i--) {
            reverseArray[count - 1 - i] = maxArray[i];
        }
        return reverseArray;
    }
}
