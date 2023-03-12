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
        int[] maxArray = new int[count];
        if (array == null) {
            return null;
        }
        if (count > array.length) {
            return null;
        }
        for (int i = 0; i < count; i++) {
            int index = 0;
            int maxNumber = Integer.MIN_VALUE;
            for (int j = 0; j < array.length; j++) {
                if (array[j] > maxNumber) {
                    index = j;
                    maxNumber = array[j];
                }
            }
            maxArray[i] = maxNumber;
            array[index] = Integer.MIN_VALUE;
        }
        return maxArray;
    }

}
