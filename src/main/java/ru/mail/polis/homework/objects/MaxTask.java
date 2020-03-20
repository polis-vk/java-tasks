package ru.mail.polis.homework.objects;

import java.util.ArrayList;


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

        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        for (int arrItem : array) {
            arrayList.add(arrItem);
        }
        int[] resultArr = new int[count];
        for (int i = 0; i < resultArr.length; i++) {
            resultArr[i] = arrayList.remove(getMax(arrayList));
        }

        return resultArr;
    }

    public static int getMax(ArrayList<Integer> arrayList) {
        if (arrayList.size() == 1) {
            return 0;
        }
        int maxIndex = 0;
        for (int i = 1; i < arrayList.size(); i++) {
            if (arrayList.get(i) > arrayList.get(maxIndex)) {
                maxIndex = i;
            }
        }
        return maxIndex;
    }

}
