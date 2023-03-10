package ru.mail.polis.homework.objects;

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
        if (array == null || count > array.length) {
            return null;
        }
        if (count == 0) {
            return new int[0];
        }
        int[] returnArray = new int[count];
        int tmp;
        for (int i = 0; i < returnArray.length; ++i) {
            returnArray[i] = Integer.MIN_VALUE;
        }
        for (int num : array) {
            if (returnArray[count - 1] > num) {
                continue;
            }
            returnArray[count - 1] = num;
            for (int i = returnArray.length - 1; i >= 1; --i) {
                if (returnArray[i] > returnArray[i - 1]) {
                    tmp = returnArray[i];
                    returnArray[i] = returnArray[i - 1];
                    returnArray[i - 1] = tmp;
                }
            }
        }
        return returnArray;
    }
}
