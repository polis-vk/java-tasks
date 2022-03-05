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
        if (count == 0) {
            return new int[0];
        }
        int[] MaxArray = new int[count];
        int[] Index = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            Index[i] = i + 1;
        }
        int max = array[0];
        int tmp = 0;
        for (int j = 0; j < count; j++) {
            for (int i = 0; i < array.length; i++) {
                if ((array[i] >= max) && (Index[i] != 0)) {
                    max = array[i];
                    tmp = i;
                }
            }
            MaxArray[j] = max;
            Index[tmp] = 0;
            for (int i = 0; i < array.length; i++) {
                if (Index[i] != 0) {
                    max = array[i];
                }
            }
        }
        return MaxArray;
    }
}
