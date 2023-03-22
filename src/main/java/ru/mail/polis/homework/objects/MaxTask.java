package ru.mail.polis.homework.objects;

import java.util.Arrays;

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
        int[] max = new int[count];
        int indexMaxValue = -1;
        int[] lastMaxValue = new int[count];
        Arrays.fill(lastMaxValue, -1);
        for (int j = 0; j < count; j++) {
            int i = 0;
            int tmpMax = Integer.MIN_VALUE;
            while (i < array.length) {
                boolean isLastMaxIndex = false;
                for (int k = 0; k < lastMaxValue.length; k++) {
                    if (i == lastMaxValue[k]) {
                        isLastMaxIndex = true;
                        break;
                    }
                }
                if (isLastMaxIndex) {
                    i++;
                    continue;
                }
                if (tmpMax < array[i]) {
                    tmpMax = array[i];
                    indexMaxValue = i;
                }
                i++;
            }
            max[j] = tmpMax;
            lastMaxValue[j] = indexMaxValue;
        }
        return max;
    }

}
