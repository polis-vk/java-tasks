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
        if (array == null)
            return null;
        if (count > array.length)
            return null;
        int[] exchangeArray = new int[array.length];
        System.arraycopy(array, 0, exchangeArray, 0, exchangeArray.length);
        int[] maxElementsArray = new int[count];
        for (int i = 0; i < count; i++) {
            maxElementsArray[i] = exchangeArray[i];
            for (int j = count; j < exchangeArray.length; j++) {
                if (maxElementsArray[i] < exchangeArray[j]) {
                    maxElementsArray[i] += exchangeArray[j];
                    exchangeArray[j] = maxElementsArray[i] - exchangeArray[j];
                    maxElementsArray[i] -= exchangeArray[j];
                }
            }
        }
        // Сортировка массива в обратном порядке.
        int buff;
        int passesNumber = 0;
        for (int j = 0; j < maxElementsArray.length; j++) {
            for (int i = 0; i < maxElementsArray.length - 1 - passesNumber; i++) {
                if (maxElementsArray[i + 1] > maxElementsArray[i]) {
                    buff = maxElementsArray[i + 1];
                    maxElementsArray[i + 1] = maxElementsArray[i];
                    maxElementsArray[i] = buff;
                }
            }
            passesNumber++;
        }
        return maxElementsArray;
    }
}
