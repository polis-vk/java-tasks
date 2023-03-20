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

        int[] maxArray = new int[count];

        if (count == 0) {
            return maxArray;
        }

        Arrays.fill(maxArray, Integer.MIN_VALUE);

        maxArray[0] = array[0];
        int iMin = 0;
        int iMax = 0;
        for (int i = 1; i < array.length; ++i) {
            if (array[i] >= maxArray[iMax]) {
                if (iMin <= iMax) {
                    iMax = iMax == 0 ? count - 1 : iMax - 1;
                    if (iMax == iMin) {
                        iMin = iMin == 0 ? count - 1 : iMin - 1;
                    }
                } else {
                    iMax = iMax + 1 == count ? 0 : iMax + 1;
                    if (iMax == iMin) {
                        iMin = iMin + 1 == count ? 0 : iMin + 1;
                    }
                }
                maxArray[iMax] = array[i];
            } else {
                int iMinNext = iMin + 1 == count ? 0 : iMin + 1;
                if (array[i] > maxArray[iMin]) {
                    if (maxArray[iMinNext] == Integer.MIN_VALUE) {
                        maxArray[iMinNext] = maxArray[iMin];
                        maxArray[iMin] = array[i];
                        iMin = iMinNext;
                    } else {
                        maxArray[iMin] = array[i];
                        int iPrevious = iMin == 0 ? count - 1 : iMin - 1;
                        if (maxArray[iMin] > maxArray[iPrevious]) {
                            iMin = iPrevious;
                        }
                    }

                } else {
                    if (maxArray[iMinNext] == Integer.MIN_VALUE) {
                        iMin = iMinNext;
                        maxArray[iMin] = array[i];
                    }
                }
            }
        }
        bubbleSort(maxArray);
        return maxArray;
    }

    public static void bubbleSort(int[] array) {
        boolean isSorted = false;
        while (!isSorted) {
            isSorted = true;
            for (int i = 0; i < array.length - 1; ++i) {
                if (array[i] < array[i + 1]) {
                    swap(array, i, i + 1);
                    isSorted = false;
                }
            }
        }
    }

    public static void swap(int[] array, int i1, int i2) {
        int temp = array[i1];
        array[i1] = array[i2];
        array[i2] = temp;
    }

}
