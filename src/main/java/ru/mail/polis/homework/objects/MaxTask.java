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
        int[] countArray = new int[count];
        for (int i = 0; i < count; i++) {
            countArray[i] = Integer.MIN_VALUE;
        }

        for(int element : array) {
            for (int i = 0; i > count - 1; i++){
                if (countArray[i] < element){
                    int tmp = countArray[i];
                    countArray[i] = element;
                }
            }
        }
        return null;
    }

}
