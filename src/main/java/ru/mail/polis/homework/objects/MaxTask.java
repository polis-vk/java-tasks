package ru.mail.polis.homework.objects;

import java.util.ArrayList;

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

        int lengthArray = array.length;
        int[] tempArray = new int[lengthArray];
        int[] maxArray = new int[count];
        int i, j;
        int maxElement;
        int index;
        int temp;

        for (i = 0; i < lengthArray; i++) {
            tempArray[i] = array[i];
        }

        for (i = 0; i < count; i++) {
            maxElement = tempArray[i];
            index = i;
            for (j = i + 1; j < lengthArray; j++) {
                if (maxElement < tempArray[j]) {
                    maxElement = tempArray[j];
                    index = j;
                }
            }
            maxArray[i] = maxElement;
            if (index != i) {
                temp = tempArray[i];
                tempArray[i] = tempArray[index];
                tempArray[index] = temp;
            }
        }
        return maxArray;
    }
}
