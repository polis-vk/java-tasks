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
    private final Stack<Integer> orderedStack = new Stack<>();
    private int max;

    int lastElement;

    public Integer getMaxValue() {
        if (this.isEmpty()) {
            throw new EmptyStackException();
        }
        return orderedStack.peek();
    }

    @Override
    public Integer push(Integer val) {
        if (max <= val) {
            max = val;
            orderedStack.push(val);
        }
        lastElement = val;
        return super.push(val);
    }

    @Override
    public Integer pop() {
        if (this.isEmpty()) {
            throw new EmptyStackException();
        }
        if (orderedStack.peek() == super.peek()) {
            orderedStack.pop();
        }
        return super.pop();
    }
}
