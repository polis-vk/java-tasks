package ru.mail.polis.homework.objects;

public class MaxTask {

    /**
     * Вам дан массив и количество элементов в возвращаемом массиве
     * Вернуть нужно массив из count максимальных элементов array, упорядоченный по убыванию.
     * Если длина массива меньше count, то вернуть null
     * Например ({1, 3, 10, 11, 22, 0}, 2) -> {22, 11}
     * ({1, 3, 22, 11, 22, 0}, 3) -> {22, 22, 11}
     * НЕЛЬЗЯ СОРТИРОВАТЬ массив array и его копиии
     *
     */
    public static int[] getMaxArray(int[] array, int count) {
        if (count > array.length) return null;
        int[] temp = new int[count];
        int max = -1;
        int indexMax = 0;
        for (int i = 0; i < temp.length; i++)
        {
            for (int j = 0; j < array.length; j++)
            {
                if (array[j] > max)
                {
                    max = array[j];
                    indexMax = j;
                }
            }
            temp[i] = max;
            array[indexMax] = -1 * array[indexMax];
            max = -1;
        }
        return temp;
    }

}
