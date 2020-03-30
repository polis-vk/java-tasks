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
        if (array.length < count) {
            return null;
        }

        int[] solvedArray = new int[count];
        for (int i = 0; i < count; i++) {
            int maxValue = Integer.MIN_VALUE;
            int maxIndex = 0;
            for (int j = 0; j < array.length; j++) {
                if (array[j] > maxValue) {
                    maxValue = array[j];
                    maxIndex = j;
                }
            }
            solvedArray[i] = maxValue;
            array[maxIndex] = Integer.MIN_VALUE;
        }
        return solvedArray;
    }
}
