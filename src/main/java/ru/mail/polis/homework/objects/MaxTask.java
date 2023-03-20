package ru.mail.polis.homework.objects;

public class MaxTask {

    /**
     * Вам дан массив и количество элементов в возвращаемом массиве
     * Вернуть нужно массив из count максимальных элементов array, упорядоченный по убыванию.
     * Если массив null или его длина меньше count, то вернуть null
     * Например ({1, 3, 10, 11, 22, 0}, 2) -> {22, 11}
     * ({1, 3, 22, 11, 22, 0}, 3) -> {22, 22, 11}
     * НЕЛЬЗЯ СОРТИРОВАТЬ массив array и его копии
     *
     */
    public static int[] getMaxArray(int[] array, int count) {
        if (array == null || array.length < count) {
            return null;
        } else if (array.length == 0 || count == 0) {
            return new int[]{};
        }
        int[] workArray = array;
        int[] maxArray = new int[count];

        for (int i = 0; i < count; i++) {
            int maxNum = workArray[0];
            int maxInd = 0;

            for (int j = 0; j < workArray.length; j++) {
                if (workArray[j] > maxNum){
                    maxNum = workArray[j];
                    maxInd = j;
                }
            }
            maxArray[i] = maxNum;
            int[] newArray = new int[workArray.length - 1];

            for (int k = 0, l = 0; k < workArray.length; k++){
                if (k != maxInd){
                    newArray[l] = workArray[k];
                    l++;
                }
            }
            workArray = newArray;
        }
        return maxArray;
    }
}
