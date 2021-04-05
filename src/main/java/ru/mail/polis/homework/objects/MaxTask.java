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

        int[] results = new int[count];
        int[] arrayClone = new int[array.length];
        int tempMax = Integer.MIN_VALUE;
        int tempIndex = -1;
        System.arraycopy(array, 0, arrayClone, 0, array.length);
        for (int i = 0; i < count; i++) {
            tempMax = Integer.MIN_VALUE;
            tempIndex = -1;
            for (int j = 0; j < arrayClone.length; j++) {
                if (arrayClone[j] > tempMax) {
                    tempMax = arrayClone[j];
                    tempIndex = j;
                }
            }
            arrayClone[tempIndex] = Integer.MIN_VALUE;
            results[i] = tempMax;
        }
        return results;
    }

}
