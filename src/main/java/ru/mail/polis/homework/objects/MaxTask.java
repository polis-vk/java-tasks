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
        if (count > array.length) {
            return null;
        }
        int[] maxArray = new int[count];
        if (count == 0){
            return maxArray;
        }

        int insertIndex;
        for (int i = 0; i < array.length; i++) {
            insertIndex = 0;
            while (array[i] <= maxArray[insertIndex]) {
                insertIndex++;
                if (insertIndex >= maxArray.length) {
                    break;
                }
            }
            if (insertIndex >= maxArray.length){
                continue;
            }

            for (int j = maxArray.length-1; j > insertIndex; j--){
                maxArray[j] = maxArray[j-1];
            }
            maxArray[insertIndex] = array[i];
        }

        return maxArray;
    }

}
