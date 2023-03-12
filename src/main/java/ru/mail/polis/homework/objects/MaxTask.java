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
        int[] result = new int[count];
        int[] tempArray = array.clone();
        for (int i = 0; i < count; i++) {
            int maxIndex = i;
            for (int j = i + 1; j < tempArray.length; j++) {
                if (tempArray[j] > tempArray[maxIndex]) {
                    maxIndex = j;
                }
            }
            int temp = tempArray[i];
            tempArray[i] = tempArray[maxIndex];
            tempArray[maxIndex] = temp;
            result[i] = tempArray[i];
        }
        return result;
    }

}
