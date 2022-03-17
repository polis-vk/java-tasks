package ru.mail.polis.homework.objects;

public class MaxTask {

    /**
     * Вам дан массив и количество элементов в возвращаемом массиве
     * Вернуть нужно массив из count максимальных элементов array, упорядоченный по убыванию.
     * Если массив null или его длина меньше count, то вернуть null
     * Например ({1, 3, 10, 11, 22, 0}, 2) -> {22, 11}
     * ({1, 3, 22, 11, 22, 0}, 3) -> {22, 22, 11}
     * НЕЛЬЗЯ СОРТИРОВАТЬ массив array и его копии
     *
     */
    public static int[] getMaxArray(int[] array, int count) {
        if (array == null || array.length < count) {
            return null;
        }
        int[] mas = new int[count];
        int max;
        int index;
        for (int j = 0; j < count; j++) {
            max = array[0];
            index = 0;
            for (int i = 1; i < array.length; i++) {
                if (array[i] > max) {
                    max = array[i];
                    index = i;
                }
            }
            mas[j] = max;
            array[index] = Integer.MIN_VALUE;
        }
        return mas;
    }
}
