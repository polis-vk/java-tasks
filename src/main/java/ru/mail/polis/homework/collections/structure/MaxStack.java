package ru.mail.polis.homework.collections.structure;

import java.util.Stack;

/**
 * Задание оценивается в 2 балла.
 * Надо расширить стандартный класс Stack методом getMaxValue().
 * Этот метод должен возвращать какое максимальное значение сейчас
 * храниться в Stack. Отрабатывать метод должен за О(1).
 */
public class MaxStack extends Stack<Integer> {

    private int maxValue;
    private int prevMaxValue;

    @Override
    public Integer push(Integer item) {
        if (maxValue < item) {
            prevMaxValue = maxValue;
            maxValue = item;
        }
        return super.push(item);
    }

    @Override
    public synchronized Integer pop() {
        if (peek() == maxValue) {
            maxValue = prevMaxValue;
        }
        return super.pop();
    }

    public Integer getMaxValue() {
        peek();
        return maxValue;
    }

}
