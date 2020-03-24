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
        if (count > array.length) {
            return null;
        }
        if (count == 0) {
            return new int[0];
        }
        int[] result = new int[count];
        result[0] = array[0];
        int fullSlots = 1;
        for (int i = 1; i < array.length; ++i) {
            if (fullSlots != count) {
                int newSlot = 0;
                while (newSlot <= fullSlots && result[newSlot] >= array[i]) {
                    newSlot++;
                }
                if (newSlot <= fullSlots) {
                    for (int temp = fullSlots; temp > newSlot; --temp) {
                        result[temp] = result[temp - 1];
                    }
                }
                result[newSlot] = array[i];
                fullSlots++;
            } else if (result[fullSlots - 1] < array[i]) {
                int newSlot = 0;
                while (array[i] <= result[newSlot]) {
                    newSlot++;
                }
                for (int temp = count - 1; temp > newSlot; --temp) {
                    result[temp] = result[temp - 1];
                }
                result[newSlot] = array[i];
            }
        }
        return result;
    }

}
