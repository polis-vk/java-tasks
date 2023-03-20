
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
        int[] finallyArray = new int[count];

        if (array == null || count > array.length) {
            return null;
        }

        for (int i = 0; i < count; i++) {
            finallyArray[i] = Integer.MIN_VALUE;
            int maxNumber = Integer.MIN_VALUE;
            int maxIndex = 0;
            for (int j = 0; j < array.length; j++) {
                if (maxNumber < array[j]) {
                    maxNumber = array[j];
                    maxIndex = j;
                }
            }
            array[maxIndex] = Integer.MIN_VALUE;
            finallyArray[i] = maxNumber;
        }
        return finallyArray;
    }
}
