package ru.mail.polis.homework.objects;

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
        if (array == null) 
        {
            return null;
        }
        if (count > array.length) {
            return null;
        }
        int[] indexesUsedValue = new int[count];
        int maxIndexCurrentValue = -1;
        int currentMaxValue = Integer.MIN_VALUE;
        for (int i = 0; i < count; ++i) {
            for (int j = 0; j < array.length; ++j) {
                if (currentMaxValue < array[j]) {
                    boolean checkCopyIndex = false;
                    for (int k = 0; k <= i; ++k) {
                        if (indexesUsedValue[k] == j + 1) {
                            checkCopyIndex = true;
                            break;
                        }
                    }
                    if (!checkCopyIndex) {
                        currentMaxValue = array[j];
                        maxIndexCurrentValue = j;
                    }
                }
            }
            indexesUsedValue[i] = maxIndexCurrentValue + 1;
            currentMaxValue = Integer.MIN_VALUE;
        }
        int[] returnArray = new int[count];
        for (int i = 0; i < count; ++i) {
            returnArray[i] = array[indexesUsedValue[i] - 1];
        }
        return returnArray;
    }
}
