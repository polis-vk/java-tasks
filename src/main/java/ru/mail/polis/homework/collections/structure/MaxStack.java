package ru.mail.polis.homework.collections.structure;

import java.util.EmptyStackException;
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
        if (this.isEmpty()) {
            throw new EmptyStackException();
        }
        return maxValue;
    }

    @Override
    public Integer push(Integer integer) {
        if (maxValue < integer) {
            maxValue = integer;
        }
        return super.push(integer);
    }

    @Override
    public Integer pop() {
        Integer result = super.pop();
        if (result.equals(maxValue)) {
            maxValue = Integer.MIN_VALUE;
            for (Integer num : this) {
                if (num > maxValue) {
                    maxValue = num;
                }
            }
        }
        return result;
    }
}
