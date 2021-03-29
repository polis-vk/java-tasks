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

        int[] result = new int[count];
        for (int i = 0; i < count; ++i) {

            int localMaxIndex = -1;
            for (int j = 0; j < array.length; ++j) {

                if ((localMaxIndex == -1 || array[j] >= array[localMaxIndex]) && (i == 0 || array[j] <= array[result[i - 1]])) {
                    boolean duplication = false;
                    for (int k = 0; k < i; ++k) {
                        if (result[k] == j) {
                            duplication = true;
                            break;
                        }
                    }

                    if (!duplication) {
                        localMaxIndex = j;
                    }
                }
            }
            result[i] = localMaxIndex;
        }

        for (int i = 0; i < count; i++) {
            result[i] = array[result[i]];
        }
        return result;
    }

}
