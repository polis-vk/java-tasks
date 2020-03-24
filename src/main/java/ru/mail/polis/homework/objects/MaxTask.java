package ru.mail.polis.homework.objects;

public class MaxTask {
    public static int[] findMax(int[] table) {
        int maxValue = table[0];
        int indexOfMax = 0;
        for (int i = 1; i < table.length; i++) {
            if (table[i] >= maxValue) {
                indexOfMax = i;
                maxValue = table[i];
            }
        }
        int[] arrayExport = {maxValue, indexOfMax};
        return arrayExport;
    }

    /**
     * Вам дан массив и количество элементов в возвращаемом массиве
     * Вернуть нужно массив из count максимальных элементов array, упорядоченный по убыванию.
     * Если длина массива меньше count, то вернуть null
     * Например ({1, 3, 10, 11, 22, 0}, 2) -> {22, 11}
     * ({1, 3, 22, 11, 22, 0}, 3) -> {22, 22, 11}
     * НЕЛЬЗЯ СОРТИРОВАТЬ массив array и его копиии
     */
    public static int[] getMaxArray(int[] array, int count) {
        if (array.length < count) {
            return null;
        }

        int[] maxArray = new int[count];
        int[] copyArray = array;
        for (int i = 0; i < count; i++) {
            maxArray[i] = findMax(copyArray)[0];
            copyArray[findMax(copyArray)[1]] = Integer.MIN_VALUE;
        }
        return maxArray;
    }
}


