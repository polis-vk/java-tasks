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
        if (array == null || array.length < count) {
            return null;
        }
        if (count == 0) {
            return new int[0];
        }

        int[] answer = new int[count];
        java.util.Arrays.fill(answer, Integer.MIN_VALUE);

        for (int element: array) {
            if (element < answer[count - 1]) {
                continue;
            }
            for (int i = 0; i < count; i++) {
                if (answer[i] < element) {
                    if (i != count - 1) {
                        System.arraycopy(answer, i, answer, i + 1, count - i - 1);
                    }
                    answer[i] = element;
                    break;
                }
            }
        }
        return answer;
    }
}
