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

        int[] results = new int[count];
        if (count == 0) {
            return results;
        }

        int[] resultsIndexes = new int[count];
        boolean match = false;
        int elemsFound = 0;
        int tempMax = Integer.MIN_VALUE;
        int tempMaxIndex = -1;
        while (elemsFound != count) {
            for (int i = 0; i < array.length; i++) {
                if (array[i] > tempMax) {
                    for (int j = 0; j < elemsFound; j++) {
                        if (i == resultsIndexes[j]) {
                            match = true;
                        }
                    }
                    if (!match){
                        tempMax = array[i];
                        tempMaxIndex = i;
                    }
                    match = false;
                }
            }
            results[elemsFound] = tempMax;
            resultsIndexes[elemsFound] = tempMaxIndex;
            elemsFound++;
            tempMax = Integer.MIN_VALUE;
            tempMaxIndex = -1;
        }
        return results;
    }

}
