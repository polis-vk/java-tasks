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
    private final Stack<Integer> maxValues = new Stack<>();

    @Override
    public Integer push(Integer item) {
        if (this.empty() || item >= getMaxValue()) {
            maxValues.push(item);
        }
        return super.push(item);
    }

    @Override
    public synchronized Integer pop() {
        if (super.peek().equals(getMaxValue())) {
            maxValues.pop();
        }
        return super.pop();
    }

    public Integer getMaxValue() {
        if (this.size() == 0) {
            throw new EmptyStackException();
        }
        return maxValues.peek();
    }
}
