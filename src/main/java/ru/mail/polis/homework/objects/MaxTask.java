package ru.mail.polis.homework.objects;

public class MaxTask {

    /**
     * Вам дан массив и количество элементов в возвращаемом массиве
     * Вернуть нужно массив из count максимальных элементов array, упорядоченный по убыванию.
     * Если длина массива меньше count, то вернуть null
     * Например ({1, 3, 10, 11, 22, 0}, 2) -> {22, 11}
     * ({1, 3, 22, 11, 22, 0}, 3) -> {22, 22, 11}
     * НЕЛЬЗЯ СОРТИРОВАТЬ массив array и его копии
     */
    public static int[] getMaxArray(int[] array, int count) {
        int length = array.length;
        if (length < count) {
            return null;
        }

        int[] maxElems = new int[count];
        int[] copyArray = new int[length];
        System.arraycopy(array, 0, copyArray, 0, length);
        int imax = 0;
        for (int i = 0; i < count; i++) {
            for (int k = 0; k < length; k++) {
                if (copyArray[imax] < copyArray[k]) {
                    imax = k;
                }
            }
            maxElems[i] = copyArray[imax];
            copyArray[imax] = Integer.MIN_VALUE;
        }
        return maxElems;
    }

}
