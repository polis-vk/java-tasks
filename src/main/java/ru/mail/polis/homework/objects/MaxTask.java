package ru.mail.polis.homework.objects;

import java.util.Arrays;

public class MaxTask {

    /**
     * Вам дан массив и количество элементов в возвращаемом массиве
     * Вернуть нужно массив из count максимальных элементов array, упорядоченный по убыванию.
     * Если длина массива меньше count, то вернуть null
     * Например ({1, 3, 10, 11, 22, 0}, 2) -> {22, 11}
     * ({1, 3, 22, 11, 22, 0}, 3) -> {22, 22, 11}
     * НЕЛЬЗЯ СОРТИРОВАТЬ массив array и его копиии
     */
    public static int[] getMaxArray(int[] array, int count) {
        if (count > array.length) {
            return null;
        }

        int[] arrayCopy = Arrays.copyOf(array, array.length);
        int[] resultArray = new int[count];
        int maxElementIndex = 0;

        for (int i = 0; i < count; i++) {
            maxElementIndex = getMaxElementIndex(arrayCopy);
            resultArray[i] = arrayCopy[maxElementIndex];
            arrayCopy = removeAtIndex(arrayCopy, maxElementIndex);
        }

        return resultArray;
    }

    public static int[] removeAtIndex(int[] array, int index) {
        int[] anotherArray = new int[array.length - 1];

        for (int i = 0, k = 0; i < array.length; i++) {
            if (i == index) {
                continue;
            }

            anotherArray[k++] = array[i];
        }

        return anotherArray;
    }

    public static int getMaxElementIndex(int[] array) {
        int max = -1;
        int maxElementIndex = 0;

        for (int i = 0; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
                maxElementIndex = i;
            }
        }

        return maxElementIndex;
    }
}
