package ru.mail.polis.homework.objects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class MaxTask {

    /**
     * Вам дан массив и количество элементов в возвращаемом массиве
     * Вернуть нужно массив из count максимальных элементов array, упорядоченный по убыванию.
     * Если длина массива меньше count, то вернуть null
     * Например ({1, 3, 10, 11, 22, 0}, 2) -> {22, 11}
     * ({1, 3, 22, 11, 22, 0}, 3) -> {22, 22, 11}
     * НЕЛЬЗЯ СОРТИРОВАТЬ массив array и его копии
     *
     */
    public static int[] getMaxArray(int[] array, int count) {
        if(array.length < count){
            return null;
        }

        int[] maxElems = new int[count];
        ArrayList<Integer> arrayList = new ArrayList<>();
        for (int i : array) {
            arrayList.add(i);
        }
        int j = 0;
        Integer max = 0;
        for (int i = 0; i < count; i++) {
            max = Collections.max(arrayList);
            maxElems[j++] = max;
            arrayList.remove(max);
        }

        return maxElems;
    }

}
