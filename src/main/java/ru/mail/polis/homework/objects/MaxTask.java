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
        if (array.length < count) {
            return null;
        }
        int[] result = new int[count];
        int[] tmpArray = new int[array.length];
        System.arraycopy(array, 0, tmpArray, 0, array.length);

        for (int i = 0; i < count; ++i) {
            int currentMaxNumber = Integer.MIN_VALUE;
            int indexCurrentMaxNumber = 0;
            for (int j = 0; j < tmpArray.length; ++j) {
                if (tmpArray[j] > currentMaxNumber) {
                    currentMaxNumber = tmpArray[j];
                    indexCurrentMaxNumber = j;
                }
            }
            result[i] = currentMaxNumber;
            tmpArray[indexCurrentMaxNumber] = Integer.MIN_VALUE;
        }
        return result;
    }
}
