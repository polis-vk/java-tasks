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
        if (array.length < count) {
            return null;
        }
        int[] copyOfArray = array.clone(); // тк нельзя изменять парамтеры метода
        int[] tmpArray = new int[count];
        int maxNumber;
        int zeroingIndex;

        for (int i = 0; i < count; i++) {
            maxNumber = Integer.MIN_VALUE;
            zeroingIndex = 0;
            for (int j = 0; j < array.length; j++) {
                if (copyOfArray[j] >= maxNumber) {
                    maxNumber = copyOfArray[j];
                    zeroingIndex = j;
                }
            }
            tmpArray[i] = maxNumber;
            copyOfArray[zeroingIndex] = Integer.MIN_VALUE;
        }
        return tmpArray;
    }
}
