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
    private Stack<Integer> maxStack = new Stack<>();

    public Integer getMaxValue() {
        if (maxStack.isEmpty()) {
            throw new EmptyStackException();
        }

        return maxStack.peek();
    }

    @Override
    public Integer push(Integer item) {
        maxStack.push(maxStack.isEmpty() ? item : (getMaxValue() > item ? getMaxValue() : item));
        return super.push(item);
    }

    @Override
    public Integer pop() {
        if (super.isEmpty()) {
            throw new EmptyStackException();
        }

        maxStack.pop();
        return super.pop();
    }

    @Override
    public Integer peek() {
        if (super.isEmpty()) {
            throw new EmptyStackException();
        }

        return super.peek();
    }
}
