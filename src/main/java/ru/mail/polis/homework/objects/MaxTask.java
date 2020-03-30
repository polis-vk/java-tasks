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

        if (count>array.length) {
            return null;
        }
        int[] answerArray = new int [count]; //Массив, в который будем записывать ответ
        int[] arrayCopy = java.util.Arrays.copyOf(array, array.length); //Копируем массив, чтобы не изменять параметр метода
        int max; //номер максимального элемента
        for (int i=0; i<count; i++) {
            max = 0;
            for (int j=1; j<arrayCopy.length; j++) {
                if (arrayCopy[max]<arrayCopy[j]) {
                    max =j;
                }
            }
            answerArray[i]=arrayCopy[max];
            arrayCopy[max]=0;
        }
        return answerArray;
    }

}
