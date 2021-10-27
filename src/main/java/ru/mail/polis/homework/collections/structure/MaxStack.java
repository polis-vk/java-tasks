package ru.mail.polis.homework.collections.structure;

import java.util.EmptyStackException;
import java.util.Objects;
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
    public synchronized Integer pop() {
        Integer retval = super.pop();
        if (Objects.equals(maxValue, retval)) {
            maxValue = Integer.MIN_VALUE;
            for (Integer tmp : this) {
                if (tmp > maxValue) {
                    maxValue = tmp;
                }
            }
        }
        return retval;
    }

    @Override
    public Integer push(Integer item) {
        if (item > maxValue) {
            maxValue = item;
        }
        return super.push(item);
    }
}
