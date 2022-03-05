package ru.mail.polis.homework.objects;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MaxTask {

    /**
     * Вам дан массив и количество элементов в возвращаемом массиве
     * Вернуть нужно массив из count максимальных элементов array, упорядоченный по убыванию.
     * Если длина массива меньше count, то вернуть null
     * Например ({1, 3, 10, 11, 22, 0}, 2) -> {22, 11}
     * ({1, 3, 22, 11, 22, 0}, 3) -> {22, 22, 11}
     * НЕЛЬЗЯ СОРТИРОВАТЬ массив array и его копии
     */
    public static int[] getMaxArray(int[] array, int count) {
        if (array.length < count) {
            return null;
        }
        int[] arrMaxElement = new int[count];
        Set<Integer> indexSet = new HashSet<>();
        for (int i = 0; i < count; i++) {
            int maxNum = Integer.MIN_VALUE;
            int buffJ = 0;
            for (int j = 0; j < array.length; j++) {
                if (array[j] > maxNum && !indexSet.contains(j)) {
                    maxNum = array[j];
                    buffJ = j;
                }
            }
            arrMaxElement[i] = maxNum;
            indexSet.add(buffJ);
        }
        return arrMaxElement;
    }
}
