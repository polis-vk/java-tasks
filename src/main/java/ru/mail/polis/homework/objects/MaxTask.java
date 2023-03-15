package ru.mail.polis.homework.objects;

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
        if (array == null || array.length < count)
            return null;
        if (array.length == 0 || count == 0)
            return new int[] {};

        int[] arrayCopy = array;
        int[] maxElemArray = new int[count];

        for (int k = 0; k < count; k++) {

            int maxElem = arrayCopy[0];
            int maxInd = 0;

            for (int i = 0; i < array.length; i++) {
                if (arrayCopy[i] > maxElem){
                    maxElem = arrayCopy[i];
                    maxInd = i;
                }
            }

            maxElemArray[k] = maxElem;
            arrayCopy[maxInd] = Integer.MIN_VALUE;

        }
        return maxElemArray;
    }

}
