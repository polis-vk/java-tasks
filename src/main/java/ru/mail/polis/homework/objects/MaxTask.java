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
        int[] resultArray = new int[count];
        if (count == 0) {
            return resultArray;
        }
        int[] currentArray = array.clone();
        quickSelect(currentArray, 0, currentArray.length - 1, currentArray.length - 1);
        for (int i = 1; i <= count; i++) {
            resultArray[i - 1] = currentArray[currentArray.length - i];
        }
        return resultArray;
    }

    private static void quickSelect(int[] array, int start, int end, int length) {
        int pivotIndex = start + ((end - start) / 2);
        int pivot = array[pivotIndex];
        swap(array, pivotIndex, end);
        pivotIndex = start;
        for (int i = start; i < end; i++) {
            if (array[i] < pivot) {
                swap(array, pivotIndex, i);
                pivotIndex++;
            }
        }
        swap(array, pivotIndex, end);
        if (pivotIndex == length) {
            return;
        }
        if (pivotIndex < length) {
            quickSelect(array, pivotIndex + 1, end, length);
            return;
        }
        quickSelect(array, start, pivotIndex - 1, length);
    }

    private static void swap(int[] array, int firstIndex, int secondIndex) {
        int temp = array[firstIndex];
        array[firstIndex] = array[secondIndex];
        array[secondIndex] = temp;
    }

}
