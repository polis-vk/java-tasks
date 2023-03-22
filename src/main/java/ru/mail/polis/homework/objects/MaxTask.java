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
        if (array == null || count > array.length) {
            return null;
        }
        if (count == 0) {
            return new int[]{};
        }

        int[] target = new int[count];
        Arrays.fill(target, Integer.MIN_VALUE);

        for (int i : array) {
            putInSorted(i, target);
        }

        reverse(target);

        return target;
    }

    private static void reverse(int[] target) {
        int temp;
        for (int i = 0; i < target.length / 2; i++) {
            temp = target[i];
            target[i] = target[target.length - i - 1];
            target[target.length - i - 1] = temp;
        }
    }

    private static void putInSorted(int currentElement, int[] target) {
        int positionToInsert = lookUpPositionToInsert(currentElement, target);
        if (positionToInsert == target.length) {
            System.arraycopy(target, 1, target, 0, target.length - 1);
            target[target.length - 1] = currentElement;
        } else if (positionToInsert >= 0) {
            System.arraycopy(target, 1, target, 0, positionToInsert);
            target[positionToInsert] = currentElement;
        }
    }

    private static int lookUpPositionToInsert(int currentElement, int[] target) {
        for (int i = -1; i < target.length - 1; i++) {
            if (target[i + 1] > currentElement) {
                return i;
            }
        }
        return target.length;
    }
}
