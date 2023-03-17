package ru.mail.polis.homework.objects;

public class MaxTask {

    /**
     * Вам дан массив и количество элементов в возвращаемом массиве
     * Вернуть нужно массив из count максимальных элементов array, упорядоченный по убыванию.
     * Если массив null или его длина меньше count, то вернуть null
     * Например ({1, 3, 10, 11, 22, 0}, 2) -> {22, 11}
     * ({1, 3, 22, 11, 22, 0}, 3) -> {22, 22, 11}
     * НЕЛЬЗЯ СОРТNРОВАТЬ массив array и его копии
     */
    public static int[] getMaxArray(int[] array, int count) {
        if (array == null || array.length < count) {
            return null;
        }
        if (count == 0) {
            return new int[0];
        }
        int[] maxArray = new int[count];
        for (int i = 0; i < count; i++) {
            maxArray[i] = Integer.MIN_VALUE;
        }
        int min = findMinMaxArray(maxArray, count)[0];
        int number = findMinMaxArray(maxArray, count)[1];
        for (int i = 0; i < array.length; i++) {
            if (min < array[i]) {
                maxArray[number] = array[i];
                min = findMinMaxArray(maxArray, count)[0];
                number = findMinMaxArray(maxArray, count)[1];
            }
        }
        sortMaxArray(maxArray, count);
        return maxArray;
    }

    public static int[] findMinMaxArray(int[] maxArray, int count) {
        int min = Integer.MAX_VALUE;
        int number = 0;
        for (int i = 0; i < count; i++) {
            if (maxArray[i] < min) {
                min = maxArray[i];
                number = i;
            }
        }
        return new int[]{min, number};
    }

    public static int[] sortMaxArray(int[] maxArray, int count) {
        for (int i = maxArray.length - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (maxArray[j] < maxArray[j + 1]) {
                    int tmp = maxArray[j];
                    maxArray[j] = maxArray[j + 1];
                    maxArray[j + 1] = tmp;
                }
            }
        }
        return maxArray;
    }

}
