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


    public static int[] push_heap(int[] array) {
        int lenArray = array.length;
        int[] heap = new int[lenArray];
        int pos = 0;
        int t;
        int position = 0;
        for (int x : array) {
            heap[position] = -x;
            while (pos > 0 && heap[pos] < heap[(pos - 1) / 2]) {
                t = heap[(pos - 1) / 2];
                heap[(pos - 1) / 2] = heap[pos];
                heap[pos] = t;
                pos = (pos - 1) / 2;
            }
            position += 1;
            pos = position;
        }
        return heap;
    }

    public static int pop_heap(int[] heap) {
        int ans = heap[0];
        int pos = 0;
        int t;
        int heapSize = heap.length;
        heap[0] = heap[heapSize - 1];
        while (2 * pos + 1 < heapSize - 1) {
            int m = pos * 2 + 1;
            if (heap[pos * 2 + 2] < heap[m]) {
                m = pos * 2 + 2;
            }
            if (heap[pos] > heap[m]) {
                t = heap[pos];
                heap[pos] = heap[m];
                heap[m] = t;
                pos = m;
            } else break;
        }
        heap[heapSize - 1] = 1;
        return ans;
    }

    public static int[] getMaxArray(int[] array, int count) {
        if (array == null || count > array.length) {
            return null;
        }
//        запись всего массива в кучу - nlogn; вытаскивание минимальных элементов klogn(максимум nlogn) -> сложность = nlogn
        int[] newArray = push_heap(array);

        int[] maxArray = new int[count];
        for (int i = 0; i < count; i++) {
            maxArray[i] = -pop_heap(newArray);
            newArray[newArray.length - 1] = Integer.MAX_VALUE;
        }
        return maxArray;
    }

}
