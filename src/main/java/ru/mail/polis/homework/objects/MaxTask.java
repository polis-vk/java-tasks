package ru.mail.polis.homework.objects;

import java.util.Arrays;
import java.util.Queue;

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
            return new int[count];
        }

        Heap heap = new Heap(array.length);
        for (int el : array) {
            heap.insert(el);
        }

        int[] res = new int[count];
        for (int i = 0; i < count; ++i) {
            res[i] = heap.getMax();
        }

        return res;
    }

    static class Heap {

        private final int[] heap;
        private int heapSize;

        public Heap(int size) {
            heap = new int[size];
            heapSize = 0;
        }

        public void insert(int el) {
            heapSize++;
            heap[heapSize - 1] = el;
            swim(heapSize - 1);
        }

        public int getMax() {
            int max = heap[0];
            heap[0] = heap[heapSize - 1];
            heapSize--;
            sink(0);
            return max;
        }

        private void sink(int i) {
            int left = 2 * i + 1;
            int right = 2 * i + 2;
            int j = i;

            if (left < heapSize && heap[j] < heap[left]) {
                j = left;
            }

            if (right < heapSize && heap[j] < heap[right]) {
                j = right;
            }

            if (j != i) {
                swap(i, j);
                sink(j);
            }

        }

        private void swim(int i) {
            while ((i - 1) / 2 >= 0 && heap[i] > heap[(i - 1) / 2]) {
                swap(i, (i - 1) / 2);
                i = (i - 1) / 2;
            }
        }

        private void swap(int i, int j) {
            int tmp = heap[i];
            heap[i] = heap[j];
            heap[j] = tmp;
        }

    }
}
