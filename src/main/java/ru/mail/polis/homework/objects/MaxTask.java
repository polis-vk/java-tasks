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
        if (array.length < count) return null;
        if (count == 0) return new int[]{};

        int[] max = new int[count];
        for (int i = 0; i < count; i++) {
            max[i] = Integer.MIN_VALUE;
        }

        for (int element : array) {
            for (int j = 0; j < max.length; j++) {
                if (element > max[j]) {
                    for (int k = count - 1; k > j; k--) {
                        max[k] = max[k - 1];
                    }
                    max[j] = element;
                    break;
                }
            }
        }
        return max;
    }
}
