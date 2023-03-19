package ru.mail.polis.homework.objects;


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
        if (array == null || count > array.length) {
            return null;
        }
        int[] maxNums = new int[count];
        for (int i = 0; i < array.length; i++) {
            if (i < count) {
                if (i == 0) {
                    maxNums[i] = array[i];
                } else {
                    int insertPosition = -1;
                    for (int j = i - 1; j >= 0; j--) {
                        if (array[i] < maxNums[j]) {
                            break;
                        } else {
                            insertPosition = j;
                        }
                    }
                    if (insertPosition == -1) {
                        maxNums[i] = array[i];
                    } else {
                        shift(maxNums, insertPosition, i);
                        maxNums[insertPosition] = array[i];
                    }
                }
            } else {
                int insertPosition = -1;
                for (int j = count - 1; j >= 0; j--) {
                    if (array[i] < maxNums[j]) {
                        break;
                    } else {
                        insertPosition = j;
                    }
                }
                if (insertPosition != -1) {
                    shift(maxNums, insertPosition, count - 1);
                    maxNums[insertPosition] = array[i];
                }
            }
        }
        return maxNums;
    }

    public static void shift(int[] arr, int num, int length) {
        for (int i = length; i > num; i--) {
            arr[i] = arr[i - 1];
        }

    }

}
