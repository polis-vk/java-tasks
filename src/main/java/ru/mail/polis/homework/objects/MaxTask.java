package ru.mail.polis.homework.objects;

public class MaxTask {

    /**
     * Вам дан массив и количество элементов в возвращаемом массиве
     * Вернуть нужно массив из count максимальных элементов array, упорядоченный по убыванию.
     * Если длина массива меньше count, то вернуть null
     * Например ({1, 3, 10, 11, 22, 0}, 2) -> {22, 11}
     * ({1, 3, 22, 11, 22, 0}, 3) -> {22, 22, 11}
     */
    public static int[] getMaxArray(int[] array, int count) {

        if (count > array.length) {
            return null;
        }
        if (count == 0) {
            return new int[0];
        }
        int[] maxElem = new int[count];
        for (int elem : array) {
            if (elem>maxElem[count-1]){
                int min=elem;
                for(int i=0; i<count; i++){
                    if (min>maxElem[i]){
                        int temp=maxElem[i];
                        maxElem[i]=min;
                        min=temp;
                    }
                }
            }
        }
        return maxElem;
    }

}
