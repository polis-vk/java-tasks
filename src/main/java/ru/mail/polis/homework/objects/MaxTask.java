package ru.mail.polis.homework.objects;

public class MaxTask {

    /**
     * Вам дан массив и количество элементов в возвращаемом массиве
     * Вернуть нужно массив из count максимальных элементов array, упорядоченный по убыванию.
     * Если длина массива меньше count, то вернуть null
     * Например ({1, 3, 10, 11, 22, 0}, 2) -> {22, 11}
     * ({1, 3, 22, 11, 22, 0}, 3) -> {22, 22, 11}
     * НЕЛЬЗЯ СОРТИРОВАТЬ массив array и его копии
     *
     */
    public static int[] getMaxArray(int[] array, int count) {
        if (array.length < count || count < 0) {
            return null;
        }

        int[] clonedArray = array.clone();
        int[] resultArray = new int[count];

        for (int i = 0; i < count; i++) {
            int maxElement = Integer.MIN_VALUE;
            int indexOfMaxElement = 0;
            for (int j = 0; j < clonedArray.length; j++) {
                if (clonedArray[j] > maxElement) {
                    maxElement = clonedArray[j];
                    indexOfMaxElement = j;
                }
            }
            resultArray[i] = maxElement;
            clonedArray[indexOfMaxElement] = Integer.MIN_VALUE;
        }
        return resultArray;
    }

}
