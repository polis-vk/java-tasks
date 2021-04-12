package ru.mail.polis.homework.objects;

public class MaxTask {

    /**
     * Вам дан массив и количество элементов в возвращаемом массиве
     * Вернуть нужно массив из count максимальных элементов array, упорядоченный по убыванию.
     * Если длина массива меньше count, то вернуть null
     * Например ({1, 3, 10, 11, 22, 0}, 2) -> {22, 11}
     * ({1, 3, 22, 11, 22, 0}, 3) -> {22, 22, 11}
     * НЕЛЬЗЯ СОРТИРОВАТЬ массив array и его копиии
     *
     */
    public static int[] getMaxArray(int[] array, int count) {
        if (count > array.length) {
            return null;
        }
        int[] resultArray = new int[count];
        if (count == 0 || array.length == 0) {
            return resultArray;
        }
        int maxValue = array[0];
        int maxIndex = 0;
        int[] doppelArray = array.clone();
        for (int i = 0; i < count; i++) {
            for (int j = 1; j < doppelArray.length; j++) {
                if (maxValue < doppelArray[j]) {
                    maxValue = doppelArray[j];
                    maxIndex = j;
                }
            }
            resultArray[i] = maxValue;
            doppelArray[maxIndex] = Integer.MIN_VALUE;
            maxValue = doppelArray[0];
            maxIndex = 0;
        }
        return resultArray;
    }

}
