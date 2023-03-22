package ru.mail.polis.homework.objects;

import java.util.Arrays;
import java.util.Collections;
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


    public static int[] getMaxArray(int[] array, int count) {

        if (array == null || array.length < count) {
            return null;
        }
        if (count == 0) {
            return new int[0];
        }

        int[] result = Arrays.copyOf(array, count);
        Arrays.sort(result);

        for (int i = count; i < array.length; i++) {
            if (result[0] > array[i]) {
                continue;
            }
            int index = Arrays.binarySearch(result, array[i]);
            if (index < 0) {
                index *= (-1);
                index = Math.min(index - 2, result.length - 1);
            } else {
                index -= 1;
            }
            if (index < 0) {
                continue;
            }
            for (int j = 1; j <= index; j++) {
                result[j - 1] = result[j];
            }
            result[index] = array[i];
        }

        for (int i = 0; i < (result.length + 1) / 2; i++) {
            int temp = result[i];
            result[i] = result[result.length - 1 - i];
            result[result.length - 1 - i] = temp;
        }

        return result;
    }

    public static class MyHeapMax {
        private final int size;
        private final int[] elements;
        private int current;

        public MyHeapMax(int size) {
            this.size = size;
            elements = new int[this.size + 1];
            current = -1;
        }

        public void insert(int x) {
            if (current == -1) {
                elements[++current] = x;
                return;
            }
            elements[++current] = x;
            up();
            current -= current == size ? 1 : 0;
//            if (current == size - 1) {
//                elements[current + 1] = x;
//            } else {
//                elements[++current] = x;
//            }
//            up();
        }

        public int extract() {
            int result = elements[0];
            elements[0] = elements[current];
            elements[current] = 0;
            current--;
            down();
            return result;
        }

        public int getMin() {
            return elements[current];
        }

        private void up() {
            int index = current;
            while ((index - 1) / 2 >= 0 && elements[(index - 1) / 2] < elements[index]) {
                int temp = elements[(index - 1) / 2];
                elements[(index - 1) / 2] = elements[index];
                elements[index] = temp;
                index = (index - 1) / 2;
            }
        }

        private void down() {
            int index = 0;

            while (2 * index + 2 <= current) {
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
            if (first <= current && (elements[2 * index + 1] > elements[index])) {
                int temp = elements[first];
                elements[first] = elements[index];
                elements[index] = temp;
            }
        }
    }
}

