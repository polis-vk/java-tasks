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
        if (array == null || count > array.length) {
            return null;
        }
        if (array.length == 0 || count == 0) {
            return new int[]{};
        }
        int[] arrayCopy = new int[array.length];
        System.arraycopy(array, 0, arrayCopy, 0, array.length);
        int[] maxElements = new int[count];
        //Сортировка за NlogN
        mergeSort(arrayCopy, 0, arrayCopy.length);
        System.arraycopy(arrayCopy, arrayCopy.length - count, maxElements, 0, maxElements.length);
        for (int i = 0; i < maxElements.length / 2; i++) {
            int temp = maxElements[i];
            maxElements[i] = maxElements[maxElements.length - i - 1];
            maxElements[maxElements.length - i - 1] = temp;
        }
        return maxElements;
    }

    private static void mergeSort(int[] array, int fromInclusive, int toExclusive) {
        if (fromInclusive == toExclusive - 1) {
            return;
        }
        int mid = fromInclusive + ((toExclusive - fromInclusive) >> 1);
        mergeSort(array, fromInclusive, mid);
        mergeSort(array, mid, toExclusive);
        merge(array, fromInclusive, mid, toExclusive);
    }

    private static void merge(int[] array, int fromInclusive, int mid, int toExclusive) {

        int positionA = 0, positionB = 0;
        int[] arrayCopy = new int[toExclusive - fromInclusive];
        for (int i = 0; i < arrayCopy.length; i++) {
            if (positionA == mid - fromInclusive) {
                arrayCopy[i] = array[mid + positionB];
                positionB++;
            } else if (positionB == toExclusive - mid) {
                arrayCopy[i] = array[fromInclusive + positionA];
                positionA++;
            } else if (array[fromInclusive + positionA] < array[mid + positionB]) {
                arrayCopy[i] = array[fromInclusive + positionA];
                positionA++;
            } else {
                arrayCopy[i] = array[mid + positionB];
                positionB++;
            }
        }
        for (int i = 0; i < arrayCopy.length; i++) {
            array[fromInclusive + i] = arrayCopy[i];
        }

    }

}
