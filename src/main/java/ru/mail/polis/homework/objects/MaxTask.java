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
        if (count == 0) {
            return new int[0];
        }

        int[] result = new int[count];
        for (int i = 0; i < count; i++) {
            result[i] = Integer.MIN_VALUE;
        }

        for (int element : array) {
            if (result[count - 1] > element) {
                continue;
            }
            result[count - 1] = element;
            for (int i = count - 1; i > 0; i--) {
                if (result[i - 1] >= element) {
                    break;
                }
                result[i] = result[i - 1];
                result[i - 1] = element;
            }
        }
        return result;
    }
}
