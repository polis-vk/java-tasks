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
        if (array.length == 0 || count == 0) {
            return new int[0];
        }

        int[] newArray = new int[count];
        int length = array.length;
        int[] arrayOfIndex = new int[count];
        int maxValue = Integer.MIN_VALUE;

        for (int i = 0; i < count; i++) {
            arrayOfIndex[i] = -1;
        }

        int index = 0;
        boolean isAlreadyAdd = false;  //Добавление в arrayOfIndex

        for (int j = 0; j < count; j++) {
            for (int i = 0; i < length; i++) {
                if (maxValue <= array[i]) {
                    for (int k : arrayOfIndex) {
                        if (k == -1) {
                            break;
                        } else if (i == k) {
                            isAlreadyAdd = true;
                            break;
                        }
                    }
                    if (!isAlreadyAdd) {
                        maxValue = array[i];
                        index = i;
                    }
                }
                isAlreadyAdd = false;
            }
            arrayOfIndex[j] = index;
            newArray[j] = maxValue;
            maxValue = Integer.MIN_VALUE;
        }

        return newArray;
    }
}
