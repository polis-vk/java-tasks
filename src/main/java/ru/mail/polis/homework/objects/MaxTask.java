package ru.mail.polis.homework.objects;

public class MaxTask {

    /**
     * Вам дан массив и количество элементов в возвращаемом массиве
     * Вернуть нужно массив из count максимальных элементов array, упорядоченный по убыванию.
     * Если массив null или его длина меньше count, то вернуть null
     * Например ({1, 3, 10, 11, 22, 0}, 2) -> {22, 11}
     * ({1, 3, 22, 11, 22, 0}, 3) -> {22, 22, 11}
     * НЕЛЬЗЯ СОРТИРОВАТЬ массив array и его копии
     *
     */
    public static int[] getMaxArray(int[] array, int count) {
        int[] maxs = new int[count];

        if (array == null || array.length < count) {
            return null;
        }
//        if (array.length == 0) {
//            return maxs;
//        }

        for (int i = 0; i < count; i++) {
            maxs[i] = array[i];
        }

        // Отсортируем массив максимумов по убыванию
        for (int i = 0; i < maxs.length; i++) {
            for (int j = i + 1; j < maxs.length; j++) {
                if (maxs[i] < maxs[j]) {
                    int tmp = maxs[i];
                    maxs[i] = maxs[j];
                    maxs[j] = tmp;
                }
            }
        }

        for (int i = count; i < array.length; i++) {
            for (int j = 0; j < maxs.length; j++) {
                if (array[i] > maxs[j]) {
                    for (int k = maxs.length - 1; k > j; k--) {
                        maxs[k] = maxs[k - 1];
                    }
                    maxs[j] = array[i];
                    break;
                }
            }
        }

        return maxs;
    }

}
