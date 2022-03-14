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
     */
    public static int[] getMaxArray(int[] array, int count) {
        if (array == null || array.length < count) { return null; }

        int[] arrMaxNum = Arrays.copyOf(array, count);
        int minValOfArrMaxNum = Integer.MAX_VALUE;
        int indexMinValOfArrMaxNum = 0;
        for (int i = 0; i < arrMaxNum.length; i++) {
            if (arrMaxNum[i] < minValOfArrMaxNum) {
                minValOfArrMaxNum = arrMaxNum[i];
                indexMinValOfArrMaxNum = i;
            }
        }
        for (int j = count; j < array.length; j++) {
            if (array[j] > minValOfArrMaxNum) {
                arrMaxNum[indexMinValOfArrMaxNum] = array[j];
            }
            minValOfArrMaxNum = Integer.MAX_VALUE;
            for (int i = 0; i < arrMaxNum.length; i++) {
                if (arrMaxNum[i] < minValOfArrMaxNum) {
                    minValOfArrMaxNum = arrMaxNum[i];
                    indexMinValOfArrMaxNum = i;
                }
            }
        }
        //сортируем в обратном порядке
        for (int i = 0; i < arrMaxNum.length; i++) {
            arrMaxNum[i] = -arrMaxNum[i];
        }
        Arrays.sort(arrMaxNum);
        for (int i = 0; i < arrMaxNum.length; i++) {
            arrMaxNum[i] = -arrMaxNum[i];
        }
        return arrMaxNum;
    }
}
