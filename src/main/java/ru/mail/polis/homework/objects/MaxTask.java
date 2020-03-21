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

        int indexMaxNumberArray = 0;
        int[] maxNumbers = new int[count];

        int lastMaxNumber = Integer.MAX_VALUE;
        for (int i = 0; i < count; i++) {
            int maxIndex = 0;
            int currentMaxElement = Integer.MIN_VALUE;
            for (int j = 0; j < array.length; j++) {
                if (array[j] > currentMaxElement && array[j] <= lastMaxNumber) {
                    currentMaxElement = array[j];
                    maxIndex = j;
                }
            }

            lastMaxNumber = currentMaxElement;
            maxNumbers[indexMaxNumberArray++] = currentMaxElement;
            array[maxIndex] = Integer.MIN_VALUE;
        }

        return maxNumbers;
    }

}
