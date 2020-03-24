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
        int[] result = new int[count];
        if (count == 0) {
            return result;
        }

        int index = 0;
        int newMaxValue;
        int oldMaxValue = Integer.MAX_VALUE;
        boolean continuation = true;

        while (continuation) {
            int counter = 0;
            newMaxValue = -Integer.MAX_VALUE;
            for (int j = 0; j < array.length; j++) {
                if (array[j] > newMaxValue & array[j] < oldMaxValue) {
                    newMaxValue = array[j];
                    counter = 0;
                }
                if (array[j] == newMaxValue) {
                    counter++;
                }
            }
            for (int k = 0; k < counter; k++) {
                result[index] = newMaxValue;
                index++;
                if (index >= result.length) {
                    continuation = false;
                    break;
                }
            }
            oldMaxValue = newMaxValue;
        }
        return result;
    }

}
