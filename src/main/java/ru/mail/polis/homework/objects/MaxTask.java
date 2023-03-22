package ru.mail.polis.homework.objects;

import java.util.Arrays;

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
        if (array == null || array.length < count) {
            return null;
        }
        if (count == 0) {
            return new int[count];
        }
        int[] arrMaxElements = Arrays.copyOf(array, count);
        Arrays.sort(arrMaxElements);
        for (int i = count; i < array.length; i++) {
            if (array[i] <= arrMaxElements[0]) {
                continue;
            }
            int indexNewNumber = searchBinaryForIndex(arrMaxElements, array[i]);
            System.arraycopy(arrMaxElements, 1, arrMaxElements, 0, indexNewNumber);
            arrMaxElements[indexNewNumber] = array[i];
        }
        int temp = 0;
        for (int i = 0; i < count / 2; i++) {
            temp = arrMaxElements[i];
            arrMaxElements[i] = arrMaxElements[count - i - 1];
            arrMaxElements[count - i - 1] = temp;
        }
        return arrMaxElements;
    }

    private static int searchBinaryForIndex(int[] array, int number) {
        int left = 0;
        int right = array.length - 1;
        int middle = 0;
        int indexInsert = -1;
        while (left <= right) {
            middle = (left + right) / 2;
            if (array[middle] == number) {
                break;
            } else if (array[middle] < number) {
                left = middle + 1;
            } else if (array[middle] > number) {
                right = middle - 1;
            }
        }
        if (array[middle] >= number) {
            indexInsert = middle - 1;
        } else {
            indexInsert = middle;
        }
        return indexInsert;
    }
}
