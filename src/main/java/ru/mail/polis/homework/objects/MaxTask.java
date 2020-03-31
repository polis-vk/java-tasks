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

        int[] tempArray = array.clone();
        int[] maxArray = new int[count];
        int currentMaxElement;
        int indexMaxElement;
        int temp;

        for (int i = 0; i < count; i++) {
            currentMaxElement = tempArray[i];
            indexMaxElement = i;
            for (int j = i + 1; j < array.length; j++) {
                if (currentMaxElement < tempArray[j]) {
                    currentMaxElement = tempArray[j];
                    indexMaxElement = j;
                }
            }
            maxArray[i] = currentMaxElement;
            if (indexMaxElement != i) {
                temp = tempArray[i];
                tempArray[i] = tempArray[indexMaxElement];
                tempArray[indexMaxElement] = temp;
            }
        }
        return maxArray;
    }
}
