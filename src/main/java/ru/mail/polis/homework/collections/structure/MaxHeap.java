package ru.mail.polis.homework.collections.structure;

public class MaxHeap {
    private int[] maxHeap = new int[10];
    private int heapSize = 0;

    public int peek() {
        return maxHeap[1];
    }

    public void heapInsert(int e) {
        arrayIncreaseIfNeed();
        maxHeap[++heapSize] = e;
        swim(heapSize);
    }

    public void remove(int e) {
        int index = find(e);
        if (index == -1) {
            return;
        }
        maxHeap[index] = maxHeap[heapSize--];
        sink(index);
    }

    private void sink(int i) {
        int k = i;
        while (2 * k <= heapSize) {
            int j = 2 * k; // left child
            if (j < heapSize && maxHeap[j] < maxHeap[j + 1]) j++; //right child
            if (maxHeap[k] >= maxHeap[j]) break; // invariant holds
            swap(k, j);
            k = j;
        }
    }

    private void swim(int i) {
        int k = i;
        while (k > 1 && maxHeap[k] > maxHeap[k / 2]) {
            swap(k, k / 2);
            k /= 2;
        }
    }

    private int find(int e) {
        int i = 1;
        if (maxHeap[1] == e) return 1;
        while (2 * i <= heapSize) {
            int j = 2 * i; // left child
            if (j < heapSize && maxHeap[j] < maxHeap[j + 1]) j++; //right child
            if (e == maxHeap[j]) return j;
            i = j;
        }
        return -1;
    }

    private void swap(int i1, int i2) {
        int temp = maxHeap[i1];
        maxHeap[i1] = maxHeap[i2];
        maxHeap[i2] = temp;
    }

    private void arrayIncreaseIfNeed() {
        if (maxHeap.length - 1 == heapSize) {
            int[] increased = new int[heapSize * 2];
            System.arraycopy(maxHeap, 0, increased, 0, maxHeap.length);
            maxHeap = increased;
        }
    }
}
