package ru.mail.polis.homework.objects;

import java.util.Arrays;

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
        if (array == null || array.length < count) {
            return null;
        }
        if (count == 0) {
            return new int[0];
        }

        int[] copied = Arrays.copyOf(array, array.length);
        kThSmallest(copied, 0, copied.length - 1, copied.length + 1 - count); // ~O(n)
        Arrays.sort(copied, copied.length - count, copied.length); // O(count * log(count))

        int[] result = new int[count];
        for (int i = 0; i < count; i++) {
            result[i] = copied[copied.length - 1 - i];
        }
        return result;
    }
    public static int kThSmallest(int[] array, int left, int right, int k)
    {
        int pos = partition(array, left, right);

        if (pos-left == k - 1) {
            return array[pos];
        }
        if (pos-left > k - 1) {
            return kThSmallest(array, left, pos - 1, k);
        }
        return kThSmallest(array, pos + 1, right, k - pos + left - 1);
    }
    public static void swap(int[] array, int i, int j)
    {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
    static int partition(int[] array, int left, int right)
    {
        int i = left;
        for (int j = left; j <= right - 1; j++) {
            if (array[j] <= array[right]) {
                swap(array, i, j);
                i++;
            }
        }
        swap(array, i, right);
        return i;
    }
}
