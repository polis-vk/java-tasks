package ru.mail.polis.homework.collections.structure;

import java.util.EmptyStackException;
import java.util.LinkedList;
import java.util.Stack;

/**
 * Задание оценивается в 2 балла.
 * Надо расширить стандартный класс Stack методом getMaxValue().
 * Этот метод должен возвращать какое максимальное значение сейчас
 * храниться в Stack. Отрабатывать метод должен за О(1).
 */
public class MaxStack extends Stack<Integer> {
    private final Stack<Integer> stack = new Stack<>();

    @Override
    public Integer push(Integer value) {
        if (isEmpty() || (stack.peek() <= value)) {
            stack.push(value);
        }
        super.add(value);
        return value;
    }

    @Override
    public synchronized Integer pop() {
        if (!isEmpty() && peek().equals(stack.peek())) {
            stack.pop();
        }
        return super.pop();
    }

    public Integer getMaxValue() {
        if (size() == 0) {
            throw new EmptyStackException();
        }
        return stack.peek();
    }
}
