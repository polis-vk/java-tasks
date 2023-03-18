package ru.mail.polis.homework.objects;

public class MaxTask {

    /**
     * Вам дан массив и количество элементов в возвращаемом массиве
     * Вернуть нужно массив из count максимальных элементов array, упорядоченный по убыванию.
     * Если массив null или его длина меньше count, то вернуть null
     * Например ({1, 3, 10, 11, 22, 0}, 2) -> {22, 11}
     * ({1, 3, 22, 11, 22, 0}, 3) -> {22, 22, 11}
     * НЕЛЬЗЯ СОРТИРОВАТЬ массив array и его копии
     *
     */
    public static int[] getMaxArray(int[] array, int count) {
        if (array == null || count > array.length){
            return null;
        }
        int[] finalArray = new int[count];
        int indexMaxNum = 0;
        int newMaxNum = Integer.MIN_VALUE;
        for (int i = 0; i < count; i++) {
            finalArray[i] = Integer.MIN_VALUE;
            for (int j = 0; j < array.length; j++){
                if (newMaxNum < array[j]){
                    newMaxNum = array[j];
                    indexMaxNum = j;
                }
            }
            finalArray[i] = newMaxNum;
            newMaxNum = Integer.MIN_VALUE;
            array[indexMaxNum] = Integer.MIN_VALUE;
        }
        return finalArray;
    }

}
