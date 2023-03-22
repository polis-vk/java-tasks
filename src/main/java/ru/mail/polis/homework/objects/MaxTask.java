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
            return new int[0];
        }

        int[] result = Arrays.copyOf(array, count);
        Arrays.sort(result);
        result = reverseTheArray(result);

        for (int i = count; i < array.length; ++i) {
            int element = array[i];
            if (element <= result[count - 1]) {
                continue;
            }

            int indexForNewVal = searchBinaryIndexForAdd(result, element);

            System.arraycopy(result, indexForNewVal, result,
                    indexForNewVal + 1, count - indexForNewVal - 1);
            result[indexForNewVal] = element;
        }
        return result;
    }

    private static int[] reverseTheArray(int[] array) {
        int[] result = array;
        int count = result.length;
        for (int i = 0; i < count / 2; ++i) {
            int buffer = result[i];
            result[i] = result[count - i - 1];
            result[count - i - 1] = buffer;
        }
        return result;
    }

    private static int searchBinaryIndexForAdd(int[] array, int element) {
        int startSearch = 0;
        int finSearch = array.length - 1;
        int middle = 0;
        int indexForAdd;

        while (finSearch >= startSearch) {
            middle = (startSearch + finSearch) / 2;
            if (array[middle] > element) {
                startSearch = middle + 1;
            } else if (array[middle] < element) {
                finSearch = middle - 1;
            } else if (array[middle] == element) {
                break;
            }
        }

        if (array[middle] >= element) {
            indexForAdd = middle + 1;
        } else {
            indexForAdd = middle;
        }

        return indexForAdd;
    }
}
