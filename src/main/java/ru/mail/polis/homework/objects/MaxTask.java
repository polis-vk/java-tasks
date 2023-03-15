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
        if(array == null || count > array.length) {
            return null;
        }
        int[] arrayCopy = new int[array.length];
        System.arraycopy(array, 0, arrayCopy, 0, array.length);
        int[] maxElements = new int[count];
        for(int i = 0; i < count; i++) {
            int nextMaxElement = Integer.MIN_VALUE;
            int nextMaxElIndex = 0;
            for(int j = 0; j < arrayCopy.length; j++) {
                if(nextMaxElement <= arrayCopy[j]) {
                    nextMaxElement = arrayCopy[j];
                    nextMaxElIndex = j;
                }
            }
            maxElements[i] = nextMaxElement;
            arrayCopy[nextMaxElIndex] = Integer.MIN_VALUE;
        }
        return maxElements;
    }

}
