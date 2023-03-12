package ru.mail.polis.homework.objects;

import java.util.Arrays;

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
    // проверяем по индексу, есть ли очередное число в списке максимальных
    public static boolean isInRestricted(int index, int[] restrictedIndexesArray, int size) {
        for (int i = 0; i <= size; i++) {
            if (restrictedIndexesArray[i] == index) {
                return true;
            }
        }
        return false;
    }

    public static int[] getMaxArray(int[] array, int count) {
        if (array == null || count > array.length) {
            return null;
        }
        int[] result = new int[count];
        int restrictedIndexesSize = 0;
        // массив с индексами чисел, которые уже были выбраны в качестве МАКСИМАЛЬНОГО
        int[] restrictedIndexes = new int[array.length];
        Arrays.fill(restrictedIndexes, -1);
        int max = Integer.MIN_VALUE;
        int maxIndex = 0;
        for (int i = 0; i < count; i++) {
            for (int j = 0; j < array.length; j++) {
                if (array[j] > max && !isInRestricted(j, restrictedIndexes, restrictedIndexesSize)) {
                    max = array[j];
                    maxIndex = j;
                }
            }
            // заносим полученный максимум в результирующий массив и дополняем массив индексов максимальных чисел
            restrictedIndexes[restrictedIndexesSize] = maxIndex;
            result[restrictedIndexesSize] = max;
            restrictedIndexesSize++;
            // устанавливаем значения по-умолчанию для служебных переменных на следующий шаг
            max = Integer.MIN_VALUE;
            maxIndex = 0;
        }
        return result;
    }
}
