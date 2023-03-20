package ru.mail.polis.homework.objects;

public class MaxTask {

    /**
     * Вам дан массив и количество элементов в возвращаемом массиве
     * Вернуть нужно массив из count максимальных элементов array, упорядоченный по убыванию.
     * Если массив null или его длина меньше count, то вернуть null
     * Например ({1, 3, 10, 11, 22, 0}, 2) -> {22, 11}
     * ({1, 3, 22, 11, 22, 0}, 3) -> {22, 22, 11}
     * НЕЛЬЗЯ СОРТИРОВАТЬ массив array и его копии
     * 4 тугрика
     */
    public static int[] getMaxArray(int[] array, int count) {
        if (array == null || count > array.length) {
            return null;
        }

        if (count == 0) {
            return new int[]{};
        }

        int[] result = new int[count];
        result[0] = array[0];
        int resultSize = 1;
        int insertIndex;
        for (int i = 1; i < array.length; i++) {
            if (resultSize < count) {
                resultSize++;
            } else if (array[i] < result[resultSize - 1]) {
                continue;
            }
            insertIndex = searchInsertIndex(result, resultSize, array[i]);
            for (int j = resultSize - 1; j > insertIndex; j--) {
                result[j] = result[j - 1];
            }
            result[insertIndex] = array[i];
        }
        return result;
    }

    public static int searchInsertIndex(int[] array, int size, int insertVal) {
        int left = 0;
        int right = size - 1;
        int mid = (left + right) / 2;
        while (left != right) {
            if (array[mid] < insertVal) {
                right = mid;
            } else {
                left = mid + 1;
            }
            mid = (left + right) / 2;
        }
        return mid;
    }
}
