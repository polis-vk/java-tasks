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
        } else if (array.length == 0 || count == 0) {
            return new int[0];
        }
        int[] arrayOfMaxima = new int[count];
        Arrays.fill(arrayOfMaxima, Integer.MIN_VALUE);
        arrayOfMaxima[0] = array[0];
        int numberOfFilledElements = 1; //Количество заполненных элементов в результирующем массиве
        int arrayLength = array.length;
        for (int i = 1; i < arrayLength; i++) {
            int j = binarySelect(arrayOfMaxima, array[i]); //бинарная вставка элемента
            if (numberOfFilledElements < count) {
                numberOfFilledElements++;
            }
            for (int k = numberOfFilledElements - 1; k > j; k--) {
                arrayOfMaxima[k] = arrayOfMaxima[k - 1];
            }
            if (j < arrayOfMaxima.length) {
                arrayOfMaxima[j] = array[i];
            }
        }
        return arrayOfMaxima;
    }

    public static int binarySelect(int[] arr, int num) {
        if (num >= arr[0]) {
            return 0;
        } else if (num <= arr[arr.length - 1]) {
            return arr.length;
        }
        int low = 0;
        int high = arr.length - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (num == arr[mid])
                return mid + 1;
            else if (num < arr[mid])
                low = mid + 1;
            else
                high = mid - 1;
        }
        return low;
    }
}


/*                if (array[i] >= arrayOfMaxima[j]) {
                    if (numberOfFilledElements < count) {
                        numberOfFilledElements++;
                    }
                    for (int k = numberOfFilledElements - 1; k > j; k--) {
                        arrayOfMaxima[k] = arrayOfMaxima[k - 1];
                    }
                    arrayOfMaxima[j] = array[i];
                    elementAdded = true;
                    break;
                }*/


/*        if (array == null || count > array.length) {
            return null;
        } else if (array.length == 0 || count == 0) {
            return new int[0];
        }
        int[] arrayOfMaxima = new int[count];
        arrayOfMaxima[0] = array[0];
        int numberOfFilledElements = 1; //Количество заполненных элементов в результирующем массиве
        int arrayLength = array.length;
        for (int i = 1; i < arrayLength; i++) {
            boolean elementAdded = false;
            for (int j = 0; j < numberOfFilledElements; j++) {
                if (array[i] >= arrayOfMaxima[j]) {
                    if (numberOfFilledElements < count) {
                        numberOfFilledElements++;
                    }
                    for (int k = numberOfFilledElements - 1; k > j; k--) {
                        arrayOfMaxima[k] = arrayOfMaxima[k - 1];
                    }
                    arrayOfMaxima[j] = array[i];
                    elementAdded = true;
                    break;
                }
            }
            if (!elementAdded && numberOfFilledElements < count) {
                arrayOfMaxima[numberOfFilledElements] = array[i];
                numberOfFilledElements++;
            }
        }
        return arrayOfMaxima;*/