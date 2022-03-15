package ru.mail.polis.homework.objects;

public class MaxTask {

    /**
     * Вам дан массив и количество элементов в возвращаемом массиве
     * Вернуть нужно массив из count максимальных элементов array, упорядоченный по убыванию.
     * Если длина массива меньше count, то вернуть null
     * Например ({1, 3, 10, 11, 22, 0}, 2) -> {22, 11}
     * ({1, 3, 22, 11, 22, 0}, 3) -> {22, 22, 11}
     * НЕЛЬЗЯ СОРТИРОВАТЬ массив array и его копии
     *
     */
    public static int[] getMaxArray(int[] array, int count) {
        if (array.length < count) {
            return null;
        }

        int[] newArray = new int[count];
        int [] sameArray = array.clone();
        int count1 = 0;
        int maxIndex = 0;

        while (count1 < count) {
            int max = Integer.MIN_VALUE;
            for (int i = 0; i < sameArray.length; i++) {
                if (sameArray[i] > max) {
                    max = sameArray[i];
                    maxIndex = i;
                }
            }
            sameArray[maxIndex]=Integer.MIN_VALUE;
            newArray[count1] = max;
            count1++;
        }

        return newArray;

    }

}
