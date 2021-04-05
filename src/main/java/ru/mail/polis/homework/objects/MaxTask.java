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
        if (count == 0) {
            return result;
        }

        int localMax = array[0];
        int duplicates = 0;

        for (int i = 0; i < array.length; ++i) {
            if (array[i] > localMax) {
                localMax = array[i];
                duplicates = 1;
            } else if (array[i] == localMax) {
                ++duplicates;
            }
        }

        int i = 0;
        for (; i < count && duplicates != 0; ++i, --duplicates) {
            result[i] = localMax;
        }

        for (; i < count; ++i) {
            int newMax = Integer.MIN_VALUE;

            for (int j = 0; j < array.length; ++j) {
                if (array[j] < localMax && array[j] > newMax) {
                    newMax = array[j];
                    duplicates = 1;
                } else if (array[j] == newMax) {
                    ++duplicates;
                }
            }

            for (; i < count && duplicates != 0; ++i, --duplicates) {
                result[i] = newMax;
            }
            --i;

            localMax = newMax;
        }

        return result;
    }
}
