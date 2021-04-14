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
        if (count == 0) {
            return new int[]{};
        }

        int[] copyArray = new int[array.length];
        System.arraycopy(array, 0, copyArray, 0, array.length);
        int[] result = new int[count];

        for (int i = 0; i < count; i++) {
            int maxCount = copyArray[0];
            int maxIndex = 0;
            for (int index = 1; index < copyArray.length; index++) {
                if (copyArray[index] > maxCount){
                    maxCount = copyArray[index];
                    maxIndex = index;
                }
            }
            result[i] = maxCount;
            copyArray[maxIndex] = Integer.MIN_VALUE;
        }
        return result;
    }

}
