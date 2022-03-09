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
        if (array == null) {
            return null;
        }
        int length = array.length;
        if (length < count) {
            return null;
        }
        int[] arrayClone = array.clone();
        int[] maxArray = new int[count];
        int max;
        int maxIndex;
        for (int i = 0; i < count; i++) {
            max = Integer.MIN_VALUE;
            maxIndex = 0;
            for (int j = 0; j < length; j++) {
                if (arrayClone[j] > max) {
                    max = arrayClone[j];
                    maxIndex = j;
                }
            }
            maxArray[i] = max;
            arrayClone[maxIndex] = Integer.MIN_VALUE;
        }
        return maxArray;
    }

}
