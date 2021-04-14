package ru.mail.polis.homework.objects;

import java.util.Arrays;

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

        int[] numbers = new int[count];
        for (int i = 0; i < count; i++) {
            numbers[i] = Integer.MIN_VALUE;
        }
        int[] arrayCopy = array;
        int maxIndex = -1;
        for (int i = 0; i < count; i++) {
            for (int j = 0; j < arrayCopy.length; j++) {
                if (numbers[i] < arrayCopy[j]) {
                    numbers[i] = arrayCopy[j];
                    maxIndex = j;
                }
            }
            arrayCopy[maxIndex] = Integer.MIN_VALUE;
        }

        return numbers;
    }

}
