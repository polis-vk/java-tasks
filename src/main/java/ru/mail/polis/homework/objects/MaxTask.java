package ru.mail.polis.homework.objects;

import org.jetbrains.annotations.Nullable;

public class MaxTask {

    /**
     * Вам дан массив и количество элементов в возвращаемом массиве
     * Вернуть нужно массив из count максимальных элементов array, упорядоченный по убыванию.
     * Если длина массива меньше count, то вернуть null
     * Например ({1, 3, 10, 11, 22, 0}, 2) -> {22, 11}
     * ({1, 3, 22, 11, 22, 0}, 3) -> {22, 22, 11}
     * НЕЛЬЗЯ СОРТИРОВАТЬ массив array и его копии
     */
    public static int @Nullable [] getMaxArray(int[] array, int count) {
        int[] gh = new int[count];
        if (array.length < count) {
            return null;
        }
        for (int k : array) {
            int j = 0;
            while (j < gh.length && gh[j] > k && gh[j] != 0) {
                j++;
            }
            if (j < gh.length) {
                if (gh[j] == 0) {
                    gh[j] = k;
                } else {
                    int i = j + 1;
                    while (i < gh.length && gh[i] != 0) {
                        gh[i] += gh[j];
                        gh[j] = gh[i] - gh[j];
                        gh[i] = gh[i] - gh[j];
                        i++;
                    }
                    if (i < gh.length) {
                        gh[i] += gh[j];
                        gh[j] = gh[i] - gh[j];
                        gh[i] = gh[i] - gh[j];
                    }
                    gh[j] = k;
                }
            }
        }
        return gh;
    }

}
