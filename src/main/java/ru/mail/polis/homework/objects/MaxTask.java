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
        if (array.length < count) {
            return null;
        }
        int checkFinal = 0;
        int[] check = new int[count];
        for (int i = 0; i < count; i++) {
            check[i] = -1;
        }
        int[] sorted = new int[count];
        for (int i = 0; i < count; i++) {
            sorted[i] = Integer.MIN_VALUE;
        }
        for (int i = 0; i < count; i++) {
            for (int k = 0; k < array.length; k++) {
                if (array[k] > sorted[i]) {
                    check[i] = k;
                    if (checked(check)) {
                        sorted[i] = array[k];
                        checkFinal = k;
                    }
                }
                check[i] = checkFinal;
            }
        }
        return sorted;
    }

    public static boolean checked(int[] check) {
        for (int i = 0; i < check.length; i++) {
         for (int j = i + 1; j < check.length; j++) {
                if ((check[i] == check[j]) && (check[i] != -1)) {
                    return false;
                }
            }  
        }
        return true;
      }
}
