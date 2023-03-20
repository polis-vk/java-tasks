package ru.mail.polis.homework.objects;

import java.util.Arrays;
import java.util.Comparator;

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

    // O(N*logN) -> N - обход массива, logN - сортировка heap
    public static int[] getMaxArray(int[] array, int count) {

        if (array == null || array.length < count) {
            return null;
        }

        MyHeapMax heap = new MyHeapMax(array.length);

        for (int cur : array) {
            heap.insert(cur);
        }

        int[] result = new int[count];
        for (int i = 0; i < result.length; i++) {
            result[i] = heap.extract();
        }

        return result;
    }

    public static class MyHeapMax {
        private final int[] elements;
        private int current;

        public MyHeapMax(int size) {
            elements = new int[size];
            current = -1;
        }

        public void insert(int x) {
            if (current == -1) {
                elements[++current] = x;
                return;
            }
            elements[++current] = x;
            up();
        }

        public Integer extract() {
            if (current == -1) {
                return null;
            }
            int result = elements[0];
            elements[0] = elements[current];
            elements[current] = 0;
            current--;
            down();
            return result;
        }

        private void up() {
            int index = current;
            while (((index - 1) / 2 >= 0) && elements[(index - 1) / 2] < elements[index]) {
                int temp = elements[(index - 1) / 2];
                elements[(index - 1) / 2] = elements[index];
                elements[index] = temp;
                index = (index - 1) / 2;
            }
        }

        private void down() {
            int index = 0;

            while ((2 * index + 2 <= current)) {
                int first = 2 * index + 1;
                int second = 2 * index + 2;
                if (elements[second] > elements[index]
                        && elements[second] > elements[first]) {

                    int temp = elements[second];
                    elements[second] = elements[index];
                    elements[index] = temp;

                    index = second;

                } else if (elements[first] > elements[index]) {

                    int temp = elements[first];
                    elements[first] = elements[index];
                    elements[index] = temp;

                    index = first;

                } else {
                    break;
                }
            }

            int first = 2 * index + 1;
            if ((first <= current) && (elements[2 * index + 1] > elements[index])) {
                int temp = elements[first];
                elements[first] = elements[index];
                elements[index] = temp;
            }
        }
    }
}

