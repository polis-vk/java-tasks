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
        if (array == null || count > array.length) {
            return null;
        }
        int[] maxArray = new int[count];
        int lastMaxElement = Integer.MAX_VALUE;
        int indexLastMaxElement = -1;
        int indexMaxElement = -1;
        for (int numberMaxElement = 0; numberMaxElement < count; numberMaxElement++) {
            int maxElement = Integer.MIN_VALUE;
            for (int i = 0; i < array.length; i++) {
                int currentElement = array[i];
                if (maxElement < currentElement && (lastMaxElement > currentElement ||
                        (lastMaxElement == currentElement && i > indexLastMaxElement))) {
                    maxElement = currentElement;
                    indexMaxElement = i;
                }
            }
            maxArray[numberMaxElement] = maxElement;
            lastMaxElement = maxElement;
            indexLastMaxElement = indexMaxElement;
        }
        return maxArray;
    }

}
