package ru.mail.polis.homework.collections.structure;

import java.util.Stack;

/**
 * Задание оценивается в 2 балла.
 * Надо расширить стандартный класс Stack методом getMaxValue().
 * Этот метод должен возвращать какое максимальное значение сейчас
 * храниться в Stack. Отрабатывать метод должен за О(1).
 */
public class MaxStack extends Stack<Integer> {
    private static int maxValue = Integer.MIN_VALUE;

    @Override
    public Integer push(Integer item) {
        if(item > maxValue) {
            maxValue = item;
        }

        return super.push(item);
    }

    // Здесь не учтен случай, когда максимальный элемент будет удален. Но если его учитывать,
    // то операция pop перестанет работать за O(1).

    public Integer getMaxValue() {
        return maxValue;
    }

}
