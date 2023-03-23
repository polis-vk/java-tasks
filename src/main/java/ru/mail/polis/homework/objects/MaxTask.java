package ru.mail.polis.homework.objects;

public class MaxTask {

    /**
     * Вам дан массив и количество элементов в возвращаемом массиве
     * Вернуть нужно массив из count максимальных элементов array, упорядоченный по убыванию.
     * Если массив null или его длина меньше count, то вернуть null
     * Например ({1, 3, 10, 11, 22, 0}, 2) -> {22, 11}
     * ({1, 3, 22, 11, 22, 0}, 3) -> {22, 22, 11}
     * НЕЛЬЗЯ СОРТИРОВАТЬ массив array и его копии
     * 4 тугрика
     */
    public static int[] getMaxArray(int[] array, int count) {

        if (array == null || count > array.length) {//если передан null или количество запрашиваемых чисел > длины массива
            return null;
        }

        int maxValue;
        int maxIndex;
        int[] newArray = new int[count];
        java.util.List<Integer> previousIndexes = new java.util.ArrayList<>();

        for (int i = 0; i < count; i++) {

            maxValue = Integer.MIN_VALUE;
            maxIndex = -1;

            for (int j = 0; j < array.length; j++) {

                if (!previousIndexes.contains(j) && array[j] > maxValue ) {//проверяем что текущий элемент больше предыдущих и он не был еще записан
                    maxIndex = j;
                    maxValue = array[j];
                }
            }

            newArray[i] = maxValue;//делаем запись и записываем индекс использованного элемента
            previousIndexes.add(maxIndex);
        }

        return newArray;
    }

}
