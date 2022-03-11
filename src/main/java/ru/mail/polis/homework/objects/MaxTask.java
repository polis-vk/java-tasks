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
     */
    public static int[] getMaxArray(int[] array, int count) {
        if (array == null || count > array.length) {
            return null;
        }
        int[] max = new int[count];
        System.arraycopy(array, 0, max, 0, count);
        Arrays.sort(max);
        for (int i = count; i < array.length; i++) {
            for (int j = 0; j < count; j++) {
                if (array[i] <= max[j]) {
                    if (j == 0) {
                        break;
                    }
                    System.arraycopy(max, 1, max, 0, j);
                    max[j - 1] = array[i];
                    break;
                }
                if (j == count - 1) {
                    System.arraycopy(max, 1, max, 0, j);
                    max[j] = array[i];
                }
            }
        }
        for (int i = 0; i < count / 2; i++) {
            int buf = max[i];
            max[i] = max[count - i - 1];
            max[count - i - 1] = buf;
        }
        return max;
    }
}


