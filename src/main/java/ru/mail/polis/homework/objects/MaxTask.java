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
        if (count > array.length) {
            return null;
        }
        int[] max = Arrays.copyOf(array, count);
        Arrays.sort(max);
        for (int i = 0; i < count / 2; i++) {
            int buf = max[i];
            max[i] = max[count - i - 1];
            max[count - i - 1] = buf;
        }
        for (int i = count; i < array.length; i++) {
            for (int j = 0; j < count; j++) {
                if (array[i] > max[j]) {
                    if (count - 1 - j >= 0) System.arraycopy(max, j, max, j + 1, count - 1 - j);
                    max[j] = array[i];
                    break;
                }
            }
        }
        return max;
    }
}


