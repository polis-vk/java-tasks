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
        if (array == null || array.length < count) return null;
        else if (count == 0) return new int[0];

        int[] res = new int[count];
        java.util.Arrays.fill(res, Integer.MIN_VALUE);
        int[] tmp = array;

        for (int i = 0; i < count; i++) {
            int index = 0;
            for (int j = 0; j < array.length; j++) {
                if (tmp[j] > res[i]) {
                    res[i] = tmp[j];
                    index = j;
                }
            }
            tmp[index] = Integer.MIN_VALUE;
        }
        return res;
    }

}
