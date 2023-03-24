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
        if (array == null || array.length < count) {
            return null;
        }
        int max;
        int maxIndex = 0;
        int arrayLength = array.length;
        boolean[] check = new boolean[arrayLength];
        int[] result = new int[count];
        for (int i = 0; i < count; i++) {
            max = Integer.MIN_VALUE;
            for (int j = 0; j < arrayLength; j++) {
                if (check[j])
                    continue;
                if (array[j] > max) {
                    max = array[j];
                    maxIndex = j;
                }
            }
            result[i] = max;
            check[maxIndex] = true;
        }
        return result;
    }

}
