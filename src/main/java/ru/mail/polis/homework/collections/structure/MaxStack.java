package ru.mail.polis.homework.collections.structure;

import java.util.Stack;

/**
 * Задание оценивается в 2 балла.
 * Надо расширить стандартный класс Stack методом getMaxValue().
 * Этот метод должен возвращать какое максимальное значение сейчас
 * храниться в Stack. Отрабатывать метод должен за О(1).
 */
public class MaxStack extends Stack<Integer> {
    public int maxValue;

    public MaxStack() {
        maxValue = Integer.MIN_VALUE;
    }

    @Override
    public Integer push(Integer value) {
        if (value != null) {
            maxValue = Math.max(value, maxValue);
            addElement(value);
        }
        return value;
    }

    @Override
    public Integer pop() {
        int obj = peek();
        int len = size();
        if (obj == maxValue) {
            maxValue = Integer.MIN_VALUE;
            for (int num : this) {
                maxValue = Math.max(num, maxValue);
            }
        }
        removeElementAt(len - 1);
        return obj;
    }

    public Integer getMaxValue() {
        return size() == 0 ? null : maxValue;
    }
}
