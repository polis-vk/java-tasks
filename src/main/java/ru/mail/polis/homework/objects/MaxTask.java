package ru.mail.polis.homework.objects;

public class MaxTask {

    /**
     * Вам дан массив и количество элементов в возвращаемом массиве
     * Вернуть нужно массив из count максимальных элементов array, упорядоченный по убыванию.
     * Если длина массива меньше count, то вернуть null
     * Например ({1, 3, 10, 11, 22, 0}, 2) -> {22, 11}
     * ({1, 3, 22, 11, 22, 0}, 3) -> {22, 22, 11}
     */
    public static int[] getMaxArray(int[] array, int count) {
        if (count == 0) {
            return new int[0];
        }
        if (array.length < count) {
            return null;
        }
        int[] result = new int[count];
        int temp;
        for (int element : array) {
            for (int j = count - 1; j >= 0 && element > result[j]; j--) {
                if (j == count - 1) {
                    result[j] = element;
                } else {
                    temp = result[j];
                    result[j] = result[j + 1];
                    result[j + 1] = temp;
                }
            }
        }
        return result;
    }
}
