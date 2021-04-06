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

        int[] origin = array;
        int maxValue;
        int minValue;
        int indMaxValue;
        int[] result = new int[count];

        for (int i = 0; i < count; i++) {
            maxValue = origin[0];
            minValue = origin[0];
            indMaxValue = 0;

            for (int j = 1; j < origin.length; j++) {

                if (origin[j] > maxValue) {
                    maxValue = origin[j];
                    indMaxValue = j;
                }

                if (origin[j] < minValue) {
                    minValue = origin[j];
                }
            }

            result[i] = maxValue;
            origin[indMaxValue] = minValue;
        }

        return result;
    }
}
