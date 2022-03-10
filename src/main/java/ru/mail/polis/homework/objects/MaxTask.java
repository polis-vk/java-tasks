package ru.mail.polis.homework.objects;


import java.util.Arrays;

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
        if (array == null || count > array.length) {
            return null;
        }
        int[] max = new int[count];
        int counter = 0;
        /*
         * Для каждого элемента узнаю больше скольких он из данного массива,
         * тем самым заранее узнаю его номер в возвращаемом массиве
         */
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length; j++) {
                if (array[i] >= array[j] && i != j) {
                    counter++;
                }
            }
            /*
             * Заполняю возвращаемый массив
             * (использую цикл для того, чтобы разрешить проблему коллизии)
             */
            if(array.length - counter <= count) {
                while(array.length - counter - 1 < count){
                    if(array[i] < 0 && array[i] < max[array.length - counter - 1]){
                        max[array.length - counter - 1] = array[i];
                        break;
                    }
                    else if(array[i] > max[array.length - counter - 1]) {
                        max[array.length - counter - 1] = array[i];
                        break;
                    }else{
                        counter--;
                    }
                }
            }
            counter = 0;
        }
        return max;
    }
}


