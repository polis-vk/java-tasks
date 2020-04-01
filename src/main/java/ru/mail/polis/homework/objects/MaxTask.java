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
        int[] res = new int[count];

        for (int i = 0; i < count; i++) {
            for (int j = 0; j <= i; j++) {
                if (array[i] > res[j] || j == i) {
                    for (int k = i; k > j; k--) {
                        res[k] = res[k - 1];
                    }
                    res[j] = array[i];
                    break;
                }
            }
        }

        for (int i = count; i < array.length; i++) {
            for (int j = 0; j < count; j++) {
                if ((array[i] > res[j])) {
                    for (int k = count - 1; k > j; k--) {
                        res[k] = res[k - 1];
                    }
                    res[j] = array[i];
                    break;
                }
            }
        }
        return res;

    }
}
