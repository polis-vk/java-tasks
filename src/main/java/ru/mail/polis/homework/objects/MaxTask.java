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
<<<<<<< HEAD
=======
     * 4 тугрика
>>>>>>> master
     */
    public static int[] getMaxArray(int[] array, int count) {
        if (array == null || array.length < count)
        {
            return null;
        }
        int copyLength = array.length;
        int[] arrayCopy = Arrays.copyOf(array, copyLength);
        int[] max = new int[count];
        int indexMaxValue = -1;
        int []lastMaxValue = new int[count];
        for (int i = 0; i < lastMaxValue.length; i++)
        {
            lastMaxValue[i] = -1;
        }
        for (int j = 0; j < count; j++) {
            int i = 0;
            int tmpMax = Integer.MIN_VALUE;
            while(i < arrayCopy.length)
            {
                boolean flag = false;
                for (int k = 0; k < lastMaxValue.length; k++) {
                    if (i == lastMaxValue[k]) {
                        flag = true;
                        break;
                    }
                }
                if (flag == true)
                {
                    i++;
                    continue;
                }
                if (tmpMax < arrayCopy[i])
                {
                    tmpMax = arrayCopy[i];
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
