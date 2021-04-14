package ru.mail.polis.homework.objects;

public class MaxTask {

    /**
     * Вам дан массив и количество элементов в возвращаемом массиве
     * Вернуть нужно массив из count максимальных элементов array, упорядоченный по убыванию.
     * Если длина массива меньше count, то вернуть null
     * Например ({1, 3, 10, 11, 22, 0}, 2) -> {22, 11}
     * ({1, 3, 22, 11, 22, 0}, 3) -> {22, 22, 11}
     * НЕЛЬЗЯ СОРТИРОВАТЬ массив array и его копии
     */
    public static int[] getMaxArray(int[] array, int count) {
        if (array.length < count) {
            return null;
        }
        boolean[] elementWasUsed = new boolean[array.length];
        int[] resultArray = new int[count];
        for (int i = 0; i < count; i++) {
            int maxElement = 0;//любое значение
            int maxElementIndex = 0;//любое значение
            for (int j = 0; j < elementWasUsed.length; j++) {
                if (elementWasUsed[j] == false) {
                    maxElement = array[j];
                    maxElementIndex = j;
                    break;
                }
            }
            for (int j = 0; j < array.length; j++) {
                if (array[j] > maxElement && !elementWasUsed[j]) {
                    maxElement = array[j];
                    maxElementIndex = j;
                }
            }
            resultArray[i] = maxElement;
            elementWasUsed[maxElementIndex] = true;
        }
        return resultArray;
    }

}
