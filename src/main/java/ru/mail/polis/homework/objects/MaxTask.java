package ru.mail.polis.homework.objects;

public class MaxTask {

    /**
     * Вам дан массив и количество элементов в возвращаемом массиве
     * Вернуть нужно массив из count максимальных элементов array, упорядоченный по убыванию.
     * Если длина массива меньше count, то вернуть null
     * Например ({1, 3, 10, 11, 22, 0}, 2) -> {22, 11}
     * ({1, 3, 22, 11, 22, 0}, 3) -> {22, 22, 11}
     * НЕЛЬЗЯ СОРТИРОВАТЬ массив array и его копии
     */
    public static int[] getMaxArray(int[] array, int count) {
        if (array.length == 0 || count == 0) {
            return new int[]{};
        }
        if (array.length < count) {
            return null;
        }
        int [] inputArray = array.clone();
        int[] maxArr = new int[count];
        int minFromMax = Integer.MAX_VALUE;
        for (int j = 0; j < maxArr.length; j++) {
            int max = Integer.MIN_VALUE;
            int indexMax = 0;
            for (int i = 0; i < inputArray.length; i++) {
                if (inputArray[i] > max && inputArray[i] <= minFromMax) {
                    max = inputArray[i];
                    indexMax = i;
                }
            }
            maxArr[j] = max;
            inputArray[indexMax] = Integer.MIN_VALUE;
            minFromMax = max;
        }
        return maxArr;
    }
}
