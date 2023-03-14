package ru.mail.polis.homework.objects;

import java.util.Arrays;

public class MaxTask {

    /**
     * Вам дан массив и количество элементов в возвращаемом массиве
     * Вернуть нужно массив из count максимальных элементов array, упорядоченный по убыванию.
     * Если массив null или его длина меньше count, то вернуть null
     * Например ({1, 3, 10, 11, 22, 0}, 2) -> {22, 11}
     * ({1, 3, 22, 11, 22, 0}, 3) -> {22, 22, 11}
     * //* НЕЛЬЗЯ СОРТИРОВАТЬ массив array и его копии
     */
    public static int[] getMaxArray(int[] array, int count) {
        if (array == null || array.length < count) {
            return null;
        }
        if (count == 0) {
            return new int[0];
        }
        int[] newArray = Arrays.copyOf(array, count);
        Arrays.sort(newArray);
        int currentMin = newArray[0];
        int insertIndex;
        for (int i = count; i < array.length; i++) {
            if (currentMin >= array[i]) {
                continue;
            }
            insertIndex = Arrays.binarySearch(newArray, array[i]);
            insertIndex = insertIndex < 0 ? -(insertIndex + 1) : insertIndex;
            System.arraycopy(newArray, 1, newArray, 0, insertIndex - 1);
            newArray[insertIndex - 1] = array[i];
            currentMin = array[0];
        }
        int temp;
        for (int i = 0; i < count / 2; i++) {
            temp = newArray[i];
            newArray[i] = newArray[count - i - 1];
            newArray[count - i - 1] = temp;
        }
        return newArray;
    }
}
