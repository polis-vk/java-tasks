package ru.mail.polis.homework.objects;

public class MaxTask {

    /**
     * Вам дан массив и количество элементов в возвращаемом массиве
     * Вернуть нужно массив из count максимальных элементов array, упорядоченный по убыванию.
     * Если длина массива меньше count, то вернуть null
     * Например ({1, 3, 10, 11, 22, 0}, 2) -> {22, 11}
     * ({1, 3, 22, 11, 22, 0}, 3) -> {22, 22, 11}
     * НЕЛЬЗЯ СОРТРОВАТЬ массив array и его копиии
     */
    public static int[] getMaxArray(int[] array, int count) {
        int[] maxArray = new int[count];
        int[] copy = array.clone();
        if (count == 0) {
            return maxArray;
        }
        if (array.length < count) {
            return null;
        }
        int max = copy[0];
        int index = -1;
        for (int j = 0; j < count; j++) {
            for (int i = 0; i < copy.length; i++) {
                if (copy[i] > max) {
                    max = copy[i];
                    index = i;
                }
            }
            maxArray[j] = max;
            max = Integer.MIN_VALUE;
            copy[index] = Integer.MIN_VALUE;
        }
        return maxArray;
    }
}
