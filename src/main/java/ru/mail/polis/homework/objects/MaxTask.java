package ru.mail.polis.homework.objects;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

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
        int[] result = null;
        if (Objects.nonNull(array) && (array.length >= count)) {
            result = new int[count];
            if (result.length != 0) {
                int[] tempArray = Arrays.copyOf(array, array.length);
                int tempCount = count;
                int leftIndex = 0;
                int rightIndex = tempArray.length - 1;
                int pivotIndex = rightIndex;
                while (leftIndex <= rightIndex) {
                    int pivotValue = tempArray[rightIndex];
                    int tempLeftIndex = leftIndex;
                    for (int i = leftIndex; i < rightIndex; i++) {
                        if (tempArray[i] >= pivotValue) {
                            swap(tempArray, tempLeftIndex, i);
                            tempLeftIndex++;
                        }
                    }
                    swap(tempArray, tempLeftIndex, rightIndex);
                    pivotIndex = tempLeftIndex;

                    int greaterPivotNumber = pivotIndex - leftIndex + 1;
                    if (tempCount > greaterPivotNumber) {
                        leftIndex = pivotIndex + 1;
                        tempCount -= greaterPivotNumber;
                    } else if (tempCount < greaterPivotNumber) {
                        rightIndex = pivotIndex - 1;
                    } else {
                        break;
                    }
                }
                System.arraycopy(tempArray, 0, result, 0, count);
                Arrays.sort(result);
                for (int i = 0; i < result.length / 2; i++) {
                    swap(result, i, result.length - i - 1);
                }
            }
        }
        return result;
    }

    private static void swap(int[] array, int leftIndex, int rightIndex) {
        int temp = array[leftIndex];
        array[leftIndex] = array[rightIndex];
        array[rightIndex] = temp;
    }
}
