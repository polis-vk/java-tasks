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

        int[] resultArray = new int[count];

        int currentMaxBoundIndex = 0;
        int currentMaxBound = Integer.MAX_VALUE;

        for (int j = 0; j < resultArray.length; j++) {

            int tempMaxIndex = 0;
            int min = Integer.MIN_VALUE;
            for (int i = 0; i < array.length; i++) {
                if (min < array[i]
                        && (array[i] < currentMaxBound || (array[i] == currentMaxBound && currentMaxBoundIndex < i))) {
                    min = array[i];
                    tempMaxIndex = i;
                }
            }
            resultArray[j] = array[tempMaxIndex];
            currentMaxBound = array[tempMaxIndex];
            currentMaxBoundIndex = tempMaxIndex;

        }
        return resultArray;
    }

}
