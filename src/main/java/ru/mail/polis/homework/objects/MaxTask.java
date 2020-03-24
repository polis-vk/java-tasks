package ru.mail.polis.homework.objects;

import com.sun.org.apache.xerces.internal.xs.ItemPSVI;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

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
        if (array.length < count) {
            return null;
        }
        
        int[] tempArray = array.clone();
        
        for (int i = 0; i < count; ++i) {
            int max = tempArray[i];
            int indexOfMax = i;
            for (int j = i; j < tempArray.length; j++) {
                if (tempArray[j] > max) {
                    max = tempArray[j];
                    indexOfMax = j;
                }
            }
            int temp = tempArray[i];
            tempArray[i] = max;
            tempArray[indexOfMax] = temp;
        }
        return Arrays.copyOf(tempArray, count);
    }
    
}
