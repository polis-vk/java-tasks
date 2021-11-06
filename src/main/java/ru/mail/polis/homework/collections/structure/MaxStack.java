package ru.mail.polis.homework.collections.structure;

import java.util.Stack;

/**
 * Задание оценивается в 2 балла.
 * Надо расширить стандартный класс Stack методом getMaxValue().
 * Этот метод должен возвращать какое максимальное значение сейчас
 * храниться в Stack. Отрабатывать метод должен за О(1).
 */
public class MaxStack extends Stack<Integer> {

    private final Stack<Integer> max = new Stack<>();

    @Override
    public Integer push(Integer value) {
        if (!max.isEmpty() && value < max.peek()) {
            max.push(max.peek());
        } else {
            max.push(value);
        }
        return super.push(value);
    }

    @Override
    public Integer pop() {
        max.pop();
        return super.pop();
    }

    public Integer getMaxValue() {
        return max.peek();
    }
}
