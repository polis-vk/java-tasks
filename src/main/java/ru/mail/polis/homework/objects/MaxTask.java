package ru.mail.polis.homework.objects;

public class MaxTask {

    /**
     * Вам дан массив и количество элементов в возвращаемом массиве
     * Вернуть нужно массив из count максимальных элементов array, упорядоченный по убыванию.
     * Если длина массива меньше count, то вернуть null
     * Например ({1, 3, 10, 11, 22, 0}, 2) -> {22, 11}
     * ({1, 3, 22, 11, 22, 0}, 3) -> {22, 22, 11}
     * НЕЛЬЗЯ СОРТИРОВАТЬ массив array и его копиии
     *
     */
    public static int[] getMaxArray(int[] array, int count) {
        if (array.length < count) {
            return null;
        }

        int[] finalArray = new int[count];
        int[] checkArray = new int[array.length];
        for (int newMax = 0; newMax < count; newMax++) {
            int max = array[0];
            int maxIndex = 0;
            for (int chooseMax = 1; checkArray[chooseMax - 1] != 0 && chooseMax < array.length; chooseMax++){
                max = array[chooseMax];
                maxIndex = chooseMax;
            }
            for (int i = 0; i < array.length; i++) {
                if (array[i] > max && checkArray[i] == 0) {
                    max = array[i];
                    maxIndex = i;
                }
            }
            finalArray[newMax] = max;
            checkArray[maxIndex] = 1;
        }
        return finalArray;
    }

}
