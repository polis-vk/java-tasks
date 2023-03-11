package ru.mail.polis.homework.objects;

public class MaxTask {

    /**
     * Вам дан массив и количество элементов в возвращаемом массиве
     * Вернуть нужно массив из count максимальных элементов array, упорядоченный по убыванию.
     * Если массив null или его длина меньше count, то вернуть null
     * Например ({1, 3, 10, 11, 22, 0}, 2) -> {22, 11}
     * ({1, 3, 22, 11, 22, 0}, 3) -> {22, 22, 11}
     * НЕЛЬЗЯ СОРТИРОВАТЬ массив array и его копии
     */
    public static int[] getMaxArray(int[] array, int count) {
        int[] arrayMax = null;
        if (array != null && count <= array.length) {
            int[] copyArray = array.clone();
            int maximum = Integer.MIN_VALUE;
            arrayMax = new int[count];
            for (int arrayMaxCounter = 0; arrayMaxCounter < arrayMax.length; arrayMaxCounter++) {
                for (int copyArrayCounter = 0; copyArrayCounter < copyArray.length; copyArrayCounter++) {
                    if (copyArray[copyArrayCounter] > maximum) {
                        maximum = copyArray[copyArrayCounter];
                    }
                }
                arrayMax[arrayMaxCounter] = maximum;
                for (int copyArrayCounter = 0; copyArrayCounter < copyArray.length; copyArrayCounter++) {
                    if (copyArray[copyArrayCounter] == arrayMax[arrayMaxCounter]) {
                        copyArray[copyArrayCounter] = Integer.MIN_VALUE;
                        break;
                    }
                }
                maximum = Integer.MIN_VALUE;
            }
        }
        return arrayMax;
    }

}
