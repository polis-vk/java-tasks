package ru.mail.polis.homework.objects;

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
        if (count > array.length) {
            return null;
        }
        int[] resultArray = new int[count];
        if ((count == 0) || (array.length == 0)) {
            return resultArray;
        }
        int[] indexesArray = new int[count];
        for (int i = 0; i < resultArray.length; i++) {
            resultArray[i] = Integer.MIN_VALUE;
            indexesArray[i] = -1;
        }
        for (int i = 0; i < resultArray.length; i++) {
            for (int j = 0; j < array.length; j++) {
                boolean newIndex = true;
                for (int m = 0; m < indexesArray.length; m++) {
                    if (indexesArray[m] == j) {
                        newIndex = false;
                    }
                }
                if (newIndex) {
                    if (resultArray[i] < array[j]) {
                        resultArray[i] = array[j];
                        indexesArray[i] = j;
                    }
                }
            }
        }
        return resultArray;
    }

}
