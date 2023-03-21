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
        if (array == null || array.length < count) {
            return null;
        }
        int[] tempArr = new int[array.length];
        for (int i = 0; i < tempArr.length; i++) {
            tempArr[i] = Integer.MIN_VALUE;
        }
        for (int j = 0; j < array.length; j++) {
            int left = 0;
            int right = j;
            while (left <= right) {
                int mid = (left + right) / 2;
                if (array[j] < tempArr[mid]) {
                    left = mid + 1;
                } else if (array[j] > tempArr[mid]) {
                    right = mid - 1;
                } else {
                    left = mid;
                    break;
                }
            }
            System.arraycopy(tempArr, left, tempArr, left + 1, j - left);
            tempArr[left] = array[j];
        }
        int[] res = new int[count];
        System.arraycopy(tempArr, 0, res, 0, count);
        return res;
    }
}