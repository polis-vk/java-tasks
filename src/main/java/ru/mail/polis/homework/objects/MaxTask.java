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

        if (count == 0) {
            return new int[]{};
        }

        int[] tmp = new int[count];
        tmp[0] = array[0];
        int size = 1;
        int left;
        int right;
        int mid;
        for (int i = 1; i < array.length; i++) {
            if (size < count) {
                size++;
            } else {
                if (array[i] < tmp[size - 1]) {
                    continue;
                }
            }

            left = 0;
            right = size - 1;
            mid = (left + right) / 2;
            while (left != right) {
                if (tmp[mid] < array[i]) {
                    right = mid;
                } else {
                    left = mid + 1;
                }
                mid = (left + right) / 2;
            }

            for (int j = size - 1; j > mid; j--) {
                tmp[j] = tmp[j - 1];
            }
            tmp[mid] = array[i];
        }
        return tmp;
    }

}
