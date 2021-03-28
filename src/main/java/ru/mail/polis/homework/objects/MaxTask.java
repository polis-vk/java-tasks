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
        int[] tempArr = array;
        int[] maxArr = new int[count];
        for (int i = 0; i < count; i++) {
            int max = tempArr[0];
            int maxI = 0;
            for (int j = 1; j < tempArr.length; j++) {
                if (tempArr[j] > max) {
                    max = tempArr[j];
                    maxI = j;
                }
            }
            maxArr[i] = max;
            tempArr[maxI] = Integer.MIN_VALUE;
        }
        return maxArr;
    }

}
