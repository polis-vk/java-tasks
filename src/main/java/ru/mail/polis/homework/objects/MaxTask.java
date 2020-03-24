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

        int MaxIndex = 0;
        int MaxElement = Integer.MAX_VALUE;

        int i = 0;
        while (i < count) {
            int currentMaxIndex = 0;
            for (int j = 1; j < array.length; j++) {
                if (array[currentMaxIndex] < array[j] && ((MaxIndex < j && array[j] == MaxElement) || array[j] < MaxElement))
                    currentMaxIndex = j;
            }

            sortedArray[i] = array[currentMaxIndex];
            MaxElement = array[currentMaxIndex];
            MaxIndex = currentMaxIndex;
            i++;
        }
        return sortedArray;
    }

}
