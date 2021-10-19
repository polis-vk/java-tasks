package ru.mail.polis.homework.collections.structure;

import java.util.Stack;

/**
 * Задание оценивается в 2 балла.
 * Надо расширить стандартный класс Stack методом getMaxValue().
 * Этот метод должен возвращать какое максимальное значение сейчас
 * храниться в Stack. Отрабатывать метод должен за О(1).
 */
public class MaxStack extends Stack<Integer> {

    private Integer maxValue = Integer.MIN_VALUE;

    public Integer getMaxValue() {
        return maxValue;
    }

    @Override
    public Integer push(Integer integer) {
        if (maxValue < integer) {
            maxValue = integer;
        }
        return super.push(integer);
    }
}
