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
        int arrLength = array.length;
        if (count > arrLength) {
            return null;
        }
        int[] maxArray = new int[count];
        for (int j = 0; j < count; j++) {
            maxArray[j] = Integer.MIN_VALUE;
        }
        int earlyMax = Integer.MAX_VALUE;
        int maxCount = 0;
        int i = 0;
        while (i < count) {
            for (int k : array) {
                if (k > maxArray[i] && earlyMax > k) {
                    maxCount = 1;
                    maxArray[i] = k;
                } else if (maxArray[i] == k) {
                    maxCount++;
                }
            }
            earlyMax = maxArray[i];
            i++;
            while (maxCount > 1 && i < count) {
                maxArray[i] = maxArray[i - 1];
                i++;
                maxCount--;
            }

        }
        return maxArray;
    }

}