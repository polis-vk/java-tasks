package ru.mail.polis.homework.objects;

public class MaxTask {

    /**
     * Вам дан массив и количество элементов в возвращаемом массиве
     * Вернуть нужно массив из count максимальных элементов array, упорядоченный по убыванию.
     * Если длина массива меньше count, то вернуть null
     * Например ({1, 3, 10, 11, 22, 0}, 2) -> {22, 11}
     * ({1, 3, 22, 11, 22, 0}, 3) -> {22, 22, 11}
     * НЕЛЬЗЯ СОРТИРОВАТЬ массив array и его копиии
     */
    public static int[] getMaxArray(int[] array, int count) {
        if (array.length < count) {
            return null;
        }
        int[] maxArray = new int[count];
        for (int b = 0; b < count; b++) {
            for (int a = 0; a < count; a++) {
                if (maxArray[a] < array[b] || a == b) {
                    for (int i = b; i > a; i--){
                    maxArray[i] = maxArray[i-1];
                    }
                    maxArray[a] = array[b];
                    break;
                }
            }
        }
        for (int b = count; b < array.length; b++) {
            for (int a = 0; a < count; a++) {
                if (maxArray[a] < array[b]) {
                    for (int i = count-1; i > a; i--){
                        maxArray[i] = maxArray[i-1];
                    }
                    maxArray[a] = array[b];
                    break;
                }
            }
        }
        return maxArray;
    }

}
