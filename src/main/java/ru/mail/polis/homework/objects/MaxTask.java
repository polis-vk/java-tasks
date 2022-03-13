package ru.mail.polis.homework.objects;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MaxTask {

    /**
     * Вам дан массив и количество элементов в возвращаемом массиве
     * Вернуть нужно массив из count максимальных элементов array, упорядоченный по убыванию.
     * Если массив null или его длина меньше count, то вернуть null
     * Например ({1, 3, 10, 11, 22, 0}, 2) -> {22, 11}
     * ({1, 3, 22, 11, 22, 0}, 3) -> {22, 22, 11}
     * НЕЛЬЗЯ СОРТИРОВАТЬ массив array и его копии
     */
    public static int[] getMaxArray(int[] array, int count) {
        if (array.length < count) {
            return null;
        }

        int[] resultArray = new int[count];
        int[] tmpArray = array.clone();

        for (int i = 0; i < count; i++) {
            int maxValue = Integer.MIN_VALUE;
            int indexMaxValue = 0;
            for (int j = 0; j < tmpArray.length; j++) {
                if (tmpArray[j] > maxValue) {
                    maxValue = array[j];
                    indexMaxValue = j;
                }
            }

            tmpArray[indexMaxValue] = Integer.MIN_VALUE;
            resultArray[i] = maxValue;
        }

        return resultArray;
    }
}


