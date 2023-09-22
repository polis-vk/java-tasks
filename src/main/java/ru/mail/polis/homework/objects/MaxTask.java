package ru.mail.polis.homework.objects;

import java.util.Iterator;

public class MaxTask  {

    /**
     * Вам дан массив и количество элементов в возвращаемом массиве
     * Вернуть нужно массив из count максимальных элементов array, упорядоченный по убыванию.
     * Если массив null или его длина меньше count, то вернуть null
     * Например ({1, 3, 10, 11, 22, 0}, 2) -> {22, 11}
     * ({1, 3, 22, 11, 22, 0}, 3) -> {22, 22, 11}
     * НЕЛЬЗЯ СОРТИРОВАТЬ массив array и его копии
     * 4 тугрика
     */

    static int partition(int arr[],
                         int low, int high)
    {
        int temp;
        int pivot = arr[high];
        int i = (low - 1);
        for (int j = low; j <= high - 1; j++)
        {
            if (arr[j] <= pivot)
            {
                i++;
                temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }

        temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;

        return (i + 1);
    }

    static int kthSmallest(int a[], int left,
                           int right, int k)
    {
        while (left <= right)
        {
            int pivotIndex = partition(a, left, right);
            if (pivotIndex == k - 1)
                return a[pivotIndex];
            else if (pivotIndex > k - 1)
                right = pivotIndex - 1;
            else
                left = pivotIndex + 1;
        }
        return -1;
    }

    public static int[] getMaxArray(int[] array, int count)
    {
        int[] ans;
        if(array == null || count > array.length) return null;
        if(count == 0) {ans = new int[]{}; return ans;}
        ans = new int[count];
        for(int i = 0; i < count; ++i)
        {
            ans[i] =  kthSmallest(array,0, array.length-1, array.length-i);
        }
        return ans;
    }
}
