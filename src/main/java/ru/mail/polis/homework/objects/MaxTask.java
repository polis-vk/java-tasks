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

        for (int i = 0; i < count / 2; ++i) {
            int buffer = result[i];
            result[i] = result[count - i - 1];
            result[count - i - 1] = buffer;
        }

        for (int i = count; i < array.length; ++i) {
            int element = array[i];
            if (element <= result[count - 1]) {
                continue;
            }
            int startSearch = 0;
            int finSearch = count - 1;
            int middle = 0;
            int indexForNewVal;

            while (finSearch >= startSearch) {
                middle = (startSearch + finSearch) / 2;
                if (result[middle] > element) {
                    startSearch = middle + 1;
                } else if (result[middle] < element) {
                    finSearch = middle - 1;
                } else if (result[middle] == element) {
                    break;
                }
            }

            if (result[middle] >= element) {
                indexForNewVal = middle + 1;
            } else {
                indexForNewVal = middle;
            }

            System.arraycopy(result, indexForNewVal, result,
                    indexForNewVal + 1, count - indexForNewVal - 1);
            result[indexForNewVal] = element;
        }
        return result;
    }
}
