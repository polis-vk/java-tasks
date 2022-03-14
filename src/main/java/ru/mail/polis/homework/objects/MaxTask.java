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

        if (count == 0) {
            return new int[]{};
        }

        int[] currentArray = array.clone();
        if (currentArray.length == count) {
            quickSort(currentArray, 0, currentArray.length - 1);
            return currentArray;
        }

        quickSelect(currentArray, 0, currentArray.length - 1, currentArray.length - count - 1);
        int[] resultArray = new int[count];
        int j = 0;
        for (int i = currentArray.length - count; i < array.length; i++) {
            resultArray[j] = currentArray[i];
            j++;
        }

        quickSort(resultArray, 0, resultArray.length - 1);
        return resultArray;
    }

    static void quickSort(int[] arr, int start, int end) {
        if (start < end) {
            int pi = partition(arr, start, end);
            quickSort(arr, start, pi - 1);
            quickSort(arr, pi + 1, end);
        }
    }

    static int partition(int[] arr, int start, int end) {
        int pivot = arr[end];
        int i = (start - 1);

        for (int j = start; j <= end; j++) {
            if (arr[j] > pivot) {
                i++;
                swap(arr, i, j);
            }
        }

        swap(arr, i + 1, end);
        return (i + 1);
    }

    private static void quickSelect(int[] array, int start, int end, int k) {
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
        if (pivotIndex == k) {
            return;
        }

        if (pivotIndex < k) {
            quickSelect(array, pivotIndex + 1, end, k);
            return;
        }

        quickSelect(array, start, pivotIndex - 1, k);
    }

    private static void swap(int[] array, int firstIndex, int secondIndex) {
        int temp = array[firstIndex];
        array[firstIndex] = array[secondIndex];
        array[secondIndex] = temp;
    }

}
