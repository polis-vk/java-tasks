package ru.mail.polis.homework.objects;

import java.util.Arrays;
import java.util.Collections;

public class MaxTask {

    /**
     * Вам дан массив и количество элементов в возвращаемом массиве
     * Вернуть нужно массив из count максимальных элементов array, упорядоченный по убыванию.
     * Если длина массива меньше count, то вернуть null
     * Например ({1, 3, 10, 11, 22, 0}, 2) -> {22, 11}
     * ({1, 3, 22, 11, 22, 0}, 3) -> {22, 22, 11}
     *
     */
    public static int[] getMaxArray(int[] array, int count) {
        if (array.length < count){
            return null;
        }
        int[] arrayClone = array.clone();
        Arrays.sort(arrayClone);
        int[] resultArray = new int [count];
        for (int i = 0; i < count; i++){
            resultArray[i] = arrayClone[arrayClone.length - 1 - i];
        }
        return resultArray;
    }

}
