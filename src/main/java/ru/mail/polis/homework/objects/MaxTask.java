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
    public static int[] getMaxArray(int[] array, int count) {
        if (array == null || array.length < count) {
            return null;
        }
        if (count == 0) {
            return new int[0];
        }

        int[] copy = array;
        int[] result = new int[count];
        int counter = 0;
        int maxId;
        int curMax;

        do {
            curMax = copy[0];
            maxId = 0;
            for (int id = 1; id < copy.length; id++) {
                if (copy[id] > curMax) {
                    curMax = copy[id];
                    maxId = id;
                }
            }
            copy[maxId] = Integer.MIN_VALUE;
            result[counter] = curMax;
            counter++;
        } while (counter < count);

        return result;
    }

}
