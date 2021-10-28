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
    private int maxValue;

    MaxStack() {
        super();
        maxValue = Integer.MIN_VALUE;
    }

    @Override
    public Integer push(Integer item) {
        if (item > maxValue) {
            maxValue = item;
        }

        return super.push(item);
    }

    @Override
    public synchronized Integer pop() {
        int deleted = super.pop();
        if (deleted == maxValue) {
            maxValue = Integer.MIN_VALUE;
            for (int el : this) {
                if (el > maxValue) {
                    maxValue = el;
                }
            }
        }

        return deleted;
    }

    public Integer getMaxValue() {
        if (this.size() == 0) {
            throw new EmptyStackException();
        }

        return maxValue;
    }

}
