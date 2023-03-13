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
        int max;
        int maxIndex = 0;
        int[] temp = array;
        int[] maxValues = new int[count];
        if (array == null || count > array.length) return null;

        for (int i = 0; i < count; i++) {
            max = Integer.MIN_VALUE;
            for (int j = 0; j < temp.length; j++) {
                if (temp[j] > max) {
                    max = temp[j];
                    maxIndex = j;
                }
            }
            maxValues[i] = max;
            temp[maxIndex] = Integer.MIN_VALUE;
        }
        return maxValues;
    }

}
