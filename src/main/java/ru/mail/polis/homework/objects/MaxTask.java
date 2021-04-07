package ru.mail.polis.homework.objects;

public class MaxTask {

    /**
     * Вам дан массив и количество элементов в возвращаемом массиве
     * Вернуть нужно массив из count максимальных элементов array, упорядоченный по убыванию.
     * Если длина массива меньше count, то вернуть null
     * Например ({1, 3, 10, 11, 22, 0}, 2) -> {22, 11}
     * ({1, 3, 22, 11, 22, 0}, 3) -> {22, 22, 11}
     * НЕЛЬЗЯ СОРТИРОВАТЬ массив array и его копиии
     */
    public static int[] getMaxArray(int[] array, int count) {
        if (array.length < count || count < 0) {
            return null;
        }

        int initialArray[] = new int[array.length];
        System.arraycopy(array, 0, initialArray, 0, array.length);
        int maxArray[] = new int[count];
        for (int i = 0; i < count; ++i) {
            int currMax = Integer.MIN_VALUE;
            int currIndex = -1;
            for (int j = 0; j < initialArray.length; ++j) {
                if (initialArray[j] > currMax) {
                    currMax = initialArray[j];
                    currIndex = j;
                }
            }
            maxArray[i] = currMax;
            initialArray[currIndex] = Integer.MIN_VALUE;
        }

        return maxArray;
    }
}
