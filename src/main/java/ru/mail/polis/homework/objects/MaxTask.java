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
        int[] maxArray = new int[count];
        int[] unsortedArray = new int[count + 1];
        for (int i = 0; i < count + 1; i++) {
            unsortedArray[i] = Integer.MIN_VALUE;
        }
        if (array.length < count) {
            return null;
        }
        if (count == 0) {
            int arr[] = {};
            return arr;
        }
        int length = array.length;
        for (int i = 0; i < length; i++) {
            findMaxArray(array[i], maxArray, count, unsortedArray);
        }
        sortMaxArray(maxArray, count - 1);
        return maxArray;
    }

    public static int[] findMaxArray(int a, int[] maxArray, int count, int[] unsortedArray) {
        unsortedArray[count] = a;
        for (int i = 0; i < count; i++) {
            sortMaxArray(unsortedArray, count);
        }
        for (int i = 0; i < count; i++) {
            maxArray[i] = unsortedArray[i];
        }
        return maxArray;
    }

    public static int[] sortMaxArray(int[] maxArray, int count) {
        int tmp;
        for (int i = 0; i < count; i++) {
            if (maxArray[i] < maxArray[i + 1]) {
                tmp = maxArray[i];
                maxArray[i] = maxArray[i + 1];
                maxArray[i + 1] = tmp;
            }
        }
        return maxArray;
    }
}
