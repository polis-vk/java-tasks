package ru.mail.polis.homework.objects;

import java.util.PriorityQueue;

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
        PriorityQueue<Integer> maxNums = new PriorityQueue<>();
        for (int i : array) {
            maxNums.add(i);
            if (maxNums.size() > count) maxNums.poll();
        }
        int[] topKNums = new int[count];
        for (int i = topKNums.length - 1; i >= 0; i--) {
            topKNums[i] = maxNums.poll();
        }
        return topKNums;
    }

}
