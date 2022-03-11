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
        int[] currencyArray = array.clone();
        int[] arrMaxElement = new int[count];
        for (int i = 0; i < count; i++) {
            int maxNum = Integer.MIN_VALUE;
            int maxIndex = 0;
            for (int j = 0; j < currencyArray.length; j++) {
                if (currencyArray[j] > maxNum) {
                    maxNum = currencyArray[j];
                    maxIndex = j;
                }
            }
            arrMaxElement[i] = maxNum;
            currencyArray[maxIndex] = Integer.MIN_VALUE;
        }
        return arrMaxElement;
    }
}
