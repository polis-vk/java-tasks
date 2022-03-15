package ru.mail.polis.homework.objects;

import java.util.Arrays;

public class MaxTask {

    /**
     * Вам дан массив и количество элементов в возвращаемом массиве
     * Вернуть нужно массив из count максимальных элементов array, упорядоченный по убыванию.
     * Если длина массива меньше count, то вернуть null
     * Например ({1, 3, 10, 11, 22, 0}, 2) -> {22, 11}
     * ({1, 3, 22, 11, 22, 0}, 3) -> {22, 22, 11}
     */
    public static int[] getMaxArray(int[] array, int count) {
        if (array.length < count) {
            return null;
        }

        int[] resultArray = new int[count];
        int[] cloneArray = array.clone();
        int maxValue = Integer.MIN_VALUE;
        int maxValuePos = -1;

        for (int i = 0; i < count; i++) {
            for (int j = 0; j < cloneArray.length; j++) {
                if (cloneArray[j] >= maxValue) {
                    maxValue = cloneArray[j];
                    maxValuePos = j;
                }
            }
            resultArray[i] = maxValue;
            cloneArray[maxValuePos] = Integer.MIN_VALUE;
            maxValue = Integer.MIN_VALUE;
        }



        return resultArray;
    }

}
