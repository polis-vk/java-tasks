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
        int[] arr = new int[count];
        if (array.length == 0) {
            return arr;
        }
        int max, index;
        boolean next;
        boolean[] checkList = new boolean[array.length];

        for (int i = 0; i < count; i++) {
            max = array[0];
            index = 0;
            for (int j = 0; j < array.length; j++) {
                if (!checkList[j] && array[j] > max) {
                    max = array[j];
                    index = j;
                }
            }
            checkList[index] = true;
            arr[i] = max;
        }
        return arr;
    }
}
