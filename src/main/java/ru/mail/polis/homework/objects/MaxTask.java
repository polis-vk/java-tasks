package ru.mail.polis.homework.objects;

public class MaxTask {

    /**
     * Вам дан массив и количество элементов в возвращаемом массиве
     * Вернуть нужно массив из count максимальных элементов array, упорядоченный по убыванию.
     * Если массив null или его длина меньше count, то вернуть null
     * Например ({1, 3, 10, 11, 22, 0}, 2) -> {22, 11}
     * ({1, 3, 22, 11, 22, 0}, 3) -> {22, 22, 11}
     * НЕЛЬЗЯ СОРТИРОВАТЬ массив array и его копии
     * 4 тугрика
     */
    public static int[] getMaxArray(int[] array, int count) {
        if (array == null || count > array.length) {
            return null;
        }
        int max;
        int[] maxNums = new int[count];
        int maxIndex = 0;
        boolean[] takenMaximus = new boolean[array.length];
        for (int i = 0; i < count; i++) {
            max = Integer.MIN_VALUE;
            for (int j = 0; j < array.length; j++) {
                if ((i == 0 || array[j] <= maxNums[i - 1]) && array[j] >= max && !takenMaximus[j]) {
                    max = array[j];
                    maxIndex = j;
                }
            }
            maxNums[i] = max;
            takenMaximus[maxIndex] = true;
        }
        return maxNums;
    }

}
