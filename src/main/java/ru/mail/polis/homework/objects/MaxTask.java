package ru.mail.polis.homework.objects;

public class MaxTask {

    /**
     * Вам дан массив и количество элементов в возвращаемом массиве
     * Вернуть нужно массив из count максимальных элементов array, упорядоченный по убыванию.
     * Если длина массива меньше count, то вернуть null
     * Например ({1, 3, 10, 11, 22, 0}, 2) -> {22, 11}
     * ({1, 3, 22, 11, 22, 0}, 3) -> {22, 22, 11}
     * НЕЛЬЗЯ СОРТИРОВАТЬ массив array и его копиии
     */
    public static int[] getMaxArray(int[] array, int count) {
        if (array.length < count || count < 0) {
            return null;
        }

        if (count == 0) {
            return new int[0];
        }

        int[] result = new int[count];
        for (int element : result) {
            element = Integer.MIN_VALUE;
        }

        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < result.length; j++) {
                if (array[i] > result[j]) {
                    int current = result[j];
                    result[j] = array[i];
                    int temp;
                    for (int k = j + 1; k < result.length; k++) {
                        temp = result[k];
                        result[k] = current;
                        current = temp;
                    }
                    break;
                }
            }
        }
        return result;
    }

}
