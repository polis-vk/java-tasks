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
        if (count == 0) {
            return result;
        }
        int buff = array[0];
        int index = 0;
        int min = 0;
        int[][] temporary = new int[2][array.length];
        for (int i = 0; i < array.length; i++) {
            temporary[0][i] = array[i];
            temporary[1][i] = 1;
            if (temporary[0][i] <= buff) {
                min = i;
            }
        }
        for (int k = 0; k < count; k++) {
            buff = array[min];
            for (int i = 0; i < array.length; i++) {
                if (temporary[0][i] >= buff && temporary[1][i] != -1) {
                    buff = temporary[0][i];
                    index = i;
                }
            }
            temporary[1][index] = -1;
            result[k] = buff;
        }
        return result;
    }
}
