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
        if (array.length < count) {
            return null;
        }
        if (count == 0) {
            return new int[]{};
        }
        int[] max = new int[count];
        for (int i = 0; i < count; i++) {
            max[i] = Integer.MIN_VALUE;
        }

        for (int element : array) {
            if (element > max[count - 1]) {
                max[count - 1] = element;
                for (int i = count - 2; i >= 0; i--) {
                    if (max[i + 1] > max[i]) {
                        int temp = max[i + 1];
                        max[i + 1] = max[i];
                        max[i] = temp;
                    } else {
                        break;
                    }
                }
            }
        }
        return max;
    }
}
