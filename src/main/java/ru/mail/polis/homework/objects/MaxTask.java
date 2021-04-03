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
        if (count < 0 || count > array.length) {
            return null;
        }

        int[] ans = new int[count];
        System.arraycopy(array, 0, ans, 0, count);
        executeBubbleSortArray(ans);

        for (int i = count; i < array.length; i++) {
            int j = count - 1;
            boolean hasBigger = false;
            while (j >= 0 && array[i] > ans[j]) {
                j--;
                hasBigger = true;
            }

            if (hasBigger) {
                j += 1;
                insertElement(ans, j, array[i]);
            }
        }

        return ans;
    }

    private static void executeBubbleSortArray(int[] array) {
        boolean sorted = false;
        int temp;
        while (!sorted) {
            sorted = true;
            for (int i = 0; i < array.length - 1; i++) {
                if (array[i] < array[i + 1]) {
                    temp = array[i];
                    array[i] = array[i + 1];
                    array[i + 1] = temp;
                    sorted = false;
                }
            }
        }
    }

    private static void insertElement(int[] array, int index, int value) {
        if (array.length == 1) {
            array[index] = value;
            return;
        }

        if (array.length - 1 - index >= 0) {
            System.arraycopy(array, index, array, index + 1, array.length - 1 - index);
        }

        array[index] = value;
    }
}
