package ru.mail.polis.homework.objects;

public class MaxTask {

    /**
     * Вам дан массив и количество элементов в возвращаемом массиве Вернуть нужно
     * массив из count максимальных элементов array, упорядоченный по убыванию. Если
     * длина массива меньше count, то вернуть null Например ({1, 3, 10, 11, 22, 0},
     * 2) -> {22, 11} ({1, 3, 22, 11, 22, 0}, 3) -> {22, 22, 11} НЕЛЬЗЯ СОРТИРОВАТЬ
     * массив array и его копиии
     *
     */
    public static int[] getMaxArray(int[] array, int count) {
        if (array.length < count || count < 0 || array == null) {
            return null;
        }
        int[] sorted = new int[count];
        int check = 0;
        for (int i = 0; i < count; i++) {
            sorted[i] = Integer.MIN_VALUE;
        }
        int tempMax = Integer.MAX_VALUE;
        for (int k = 0; k < count; k++) {
            if (check != 0) {
                sorted[k] = tempMax;
                check--;
            } else {
                for (int i = 0; i < array.length; i++) {
                    if (array[i] == sorted[k]) {
                        check++;
                    }
                    if (array[i] > sorted[k] && array[i] < tempMax) {
                        sorted[k] = array[i];
                        check = 0;
                    }
                }
            }
            tempMax = sorted[k];
        }
        return sorted;
    }
}
