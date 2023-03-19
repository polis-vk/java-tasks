package ru.mail.polis.homework.objects;

import java.lang.reflect.Array;
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

        if (array == null || count > array.length){
            return null;
        }
        if (count == 0){
            return new int[0];
        }
        int[] maxNumbersArray = Arrays.copyOf(array, count);
        Arrays.sort(maxNumbersArray);
        int index;
        for (int i = count; i < array.length; ++i) {
            if (array[i] > maxNumbersArray[0]) {
                index = Arrays.binarySearch(maxNumbersArray, array[i]);
                index = index < 0? -(index + 1): index;

                System.arraycopy(maxNumbersArray, 1, maxNumbersArray, 0, index - 1);
                maxNumbersArray[index - 1] = array[i];
            }
        }
        int temp;
        for (int i = 0; i < maxNumbersArray.length / 2; ++i) {
            temp = maxNumbersArray[i];
            maxNumbersArray[i] = maxNumbersArray[maxNumbersArray.length - 1 - i];
            maxNumbersArray[maxNumbersArray.length - 1 - i] = temp;
        }
        return maxNumbersArray;
    }
}
