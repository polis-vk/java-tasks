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
        int[] result = new int[count];
        // сразу обработаем исключения
        if (array.length < count) {
            return null;
        }
        if (count == 0) {
            return result;
        }

        int[] tempArray = array.clone();    // клонируем array
        int maxValue = Integer.MIN_VALUE;
        int index = 0;      // индекс максимального элемента
        for (int i = 0; i < count; i++) {
            for (int j = 0; j < tempArray.length; j++) {
                if (maxValue < tempArray[j]) {
                    maxValue = tempArray[j];
                    index = j;
                }
            }
            result[i] = maxValue;
            tempArray[index] = Integer.MIN_VALUE;
            maxValue = Integer.MIN_VALUE;
        }

        return result;
    }

}
