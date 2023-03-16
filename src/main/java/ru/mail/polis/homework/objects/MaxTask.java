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

        int[] result = new int[count];
        boolean[] takeElement = new boolean[array.length];
        int indexResult = 0;
        int indexTakeElement = 0;

        while (indexResult < count) {
            int curMax = Integer.MIN_VALUE;
            for (int i = 0; i < array.length; i++) {
                if (takeElement[i]) {
                    continue;
                }
                indexTakeElement = array[i] > curMax ? i : indexTakeElement;
                curMax = Math.max(array[i], curMax);
            }

            takeElement[indexTakeElement] = true;
            result[indexResult] = curMax;
            indexResult++;
        }

        return result;
    }
}
