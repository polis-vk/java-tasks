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
        for (int i = 0; i < count; i++) {
            ans[i] = Integer.MIN_VALUE;
        }

        for (int number : array) {
            int j = count - 1;
            boolean hasBigger = false;
            while (j >= 0 && number > ans[j]) {
                j--;
                hasBigger = true;
            }

            if (hasBigger) {
                j += 1;
                insertElement(ans, j, number);
            }
        }

        return ans;
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
