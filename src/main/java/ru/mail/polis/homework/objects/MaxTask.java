package ru.mail.polis.homework.objects;

import java.util.*;

public class MaxTask {

    /**
     * Вам дан массив и количество элементов в возвращаемом массиве
     * Вернуть нужно массив из count максимальных элементов array, упорядоченный по убыванию.
     * Если массив null или его длина меньше count, то вернуть null
     * Например ({1, 3, 10, 11, 22, 0}, 2) -> {22, 11}
     * ({1, 3, 22, 11, 22, 0}, 3) -> {22, 22, 11}
     * НЕЛЬЗЯ СОРТИРОВАТЬ массив array и его копии
     */
    public static int[] getMaxArray(int[] array, int count) {
        if (array == null || array.length < count) {
            return null;
        }

        PriorityQueue<Integer> pq = new PriorityQueue<Integer>();
        for (int i = 0; i < count; i++) {
            pq.add(Integer.MIN_VALUE);
        }

        for (int j : array) {
            if (pq.isEmpty()) {
                pq.add(j);
                continue;
            }
            if (j > pq.peek()) {
                pq.remove();
                pq.add(j);
            }
        }

        int[] ans = new int[count];
        for (int i = 0; i < count; i++) {
            ans[count - i - 1] = pq.peek();
            pq.remove();
        }

        return ans;
    }

}
