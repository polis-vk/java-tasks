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
        int[] newArray = array.clone();
        if (newArray.length < count) {
            return null;
        }

        int indexMaxNumberArray = 0;
        int[] maxNumbers = new int[count];

        int lastMaxNumber = Integer.MAX_VALUE;
        for (int i = 0; i < count; i++) {
            int maxIndex = 0;
            int currentMaxElement = Integer.MIN_VALUE;
            for (int j = 0; j < newArray.length; j++) {
                if (newArray[j] > currentMaxElement && newArray[j] <= lastMaxNumber) {
                    currentMaxElement = newArray[j];
                    maxIndex = j;
                }
            }

            lastMaxNumber = currentMaxElement;
            maxNumbers[indexMaxNumberArray++] = currentMaxElement;
            newArray[maxIndex] = Integer.MIN_VALUE;
        }

        return maxNumbers;
    }

}
