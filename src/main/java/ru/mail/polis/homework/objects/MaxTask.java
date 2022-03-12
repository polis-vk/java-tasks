package ru.mail.polis.homework.objects;

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
        if (array == null || array.length < count ) {
            return null; // если длина массива меньше, чем количество чисел для возвращения - вернуть null
        }
        int[] result = new int[count]; // массив значений, который вернется
        if (count == 0) {
            return result; // если количество максимальных значений равно 0, вернуть нулевой массив
        }
        int[][] temporary = new int[2][array.length]; // двумерный массив для отслеживания записанных уже чисел
        for (int i = 0; i < array.length; i++) {
            temporary[0][i] = array[i]; // первую строку делаю копией изначального массива
            temporary[1][i] = 1; // заполняю единицами для отслеживания изменений
        }
        int buff = 0;
        int indexOfMaxNumber = 0;
        for (int k = 0; k < count; k++) {
            buff = Integer.MIN_VALUE;
            for (int i = 0; i < array.length; i++) {
                if (temporary[0][i] >= buff && temporary[1][i] != -1) {
                    buff = temporary[0][i];
                    indexOfMaxNumber = i; // индекс наибольшего найденного числа согласно условиям
                }
            }
            temporary[1][indexOfMaxNumber] = -1; // изменение для понимания того, что данное число уже было записано
            result[k] = buff;
        }
        return result;
    }
}
