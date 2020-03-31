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
        int[] sortedArray = new int[count];

        int maxIndex = 0;
        int maxElement = Integer.MAX_VALUE;

        int i = 0;
        while (i < count) {
            int currentmaxIndex = array.length - 1;
            for (int j = array.length - 2; j >= 0; j--) {
                if (array[currentmaxIndex] <= array[j]  && ((maxIndex < j && array[j] == maxElement) || array[j] < maxElement))
                    currentmaxIndex = j;
            }

            sortedArray[i] = array[currentmaxIndex];
            maxElement = array[currentmaxIndex];
            maxIndex = currentmaxIndex;
            i++;
        }
        return sortedArray;
    }

}
