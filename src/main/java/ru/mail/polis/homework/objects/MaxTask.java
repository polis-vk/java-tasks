package ru.mail.polis.homework.objects;
import java.util.Arrays;
import java.lang.reflect.Array;

public class MaxTask {

    /**
     * Вам дан массив и количество элементов в возвращаемом массиве
     * Вернуть нужно массив из count максимальных элементов array, упорядоченный по убыванию.
     * Если длина массива меньше count, то вернуть null
     * Например ({1, 3, 10, 11, 22, 0}, 2) -> {22, 11}
     * ({1, 3, 22, 11, 22, 0}, 3) -> {22, 22, 11}
     * НЕЛЬЗЯ СОРТИРОВАТЬ массив array и его копии
     *
     */
    public static int[] getMaxArray(int[] array, int count) {
//        int[] maxArray = new int[count];
//        Arrays.copyOf(array, count); // первые значения из базового массива
//        for (int i = 0; i < array.length; i++){
//            for (int j = 0; j < count; j++){
//                if (maxArray[j] < array[i]){
//                    maxArray[j] = array[i];
//                    continue;
//                }
//            }
//        }
        //return maxArray;
        return null;
    }

}
