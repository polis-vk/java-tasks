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
     * НЕЛЬЗЯ СОРТИРОВАТЬ массив array и его копиии
     *
     */
    public static int[] getMaxArray(int[] array, int count) {
        if (array.length < count) {
            return null;
        }
        int[] resultArray = new int [count];
        for (int i = 0; i < count; i++){
            int k = i;
            for (int j = i; j < array.length; j++) {
                if (array[j] > array[k]){
                    k = j;
                }
            }
            int temp = array[i];
            array[i] = array[k];
            resultArray[i] = array[k];
            array[k] = temp;
        }
        return resultArray;
    }
}
