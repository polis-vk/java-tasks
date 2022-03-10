package ru.mail.polis.homework.objects;

import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;

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
        if (array == null || count > array.length) {
            return null;
        }
        if (count == 0) {
            return new int[0];
        }
        PriorityQueue<Integer> currQueue = new PriorityQueue<>();
        for (int i = 0; i < count; i++) {
            currQueue.add(array[i]);
        }
        for (int i = count; i < array.length; i++) {
            if (currQueue.element() < array[i]) {
                currQueue.remove();
                currQueue.add(array[i]);
            }
        }
        Object[] temp = currQueue.toArray();
        Arrays.sort(temp, Collections.reverseOrder());
        int[] ans = new int[count];
        for (int i = 0; i < count; i++) {
            ans[i] = (int) temp[i];
        }
        return ans;
    }
}
