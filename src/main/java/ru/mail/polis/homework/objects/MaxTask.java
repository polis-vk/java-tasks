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
        if (array == null || array.length < count) {
            return null;
        }
        if (count == 0) {
            return new int[0];
        }
        int[] nMaxNums = new int[count];
        java.util.Arrays.fill(nMaxNums, Integer.MIN_VALUE);
        for (int num : array) {
            if (nMaxNums[count - 1] > num) {
                continue;
            }
            nMaxNums[count - 1] = num;
            for (int i = count - 2; i >= 0; i--) {
                if (nMaxNums[i] < num) {
                    nMaxNums[i + 1] = nMaxNums[i];
                    nMaxNums[i] = num;
                } else {
                    break;
                }
            }
        }
        return nMaxNums;
    }

}
