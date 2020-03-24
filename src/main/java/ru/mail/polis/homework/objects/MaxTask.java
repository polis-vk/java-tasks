package ru.mail.polis.homework.objects;

import com.sun.tools.javac.util.ArrayUtils;
import sun.security.util.ArrayUtil;

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
        int[] finalArray = new int[count];
        for (int i = 0; i < count; i++) {
            finalArray[i] = array[i];
        }
        Arrays.sort(finalArray);
        for (int i = count; i < array.length; i++) {
            for (int j = 0; j < count && array[i] > finalArray[j]; j++) {
                if (j == 0) {
                    finalArray[j] = array[i];
                } else {
                    int swap = finalArray[j];
                    finalArray[j] = finalArray[j - 1];
                    finalArray[j - 1] = swap;
                }
            }
        }
        for (int i = 0; i < count / 2; i++) {
            int swap = finalArray[i];
            finalArray[i] = finalArray[count - i - 1];
            finalArray[count - i - 1] = swap;
        }
        return finalArray;
    }
}