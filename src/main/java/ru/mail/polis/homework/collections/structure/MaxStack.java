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

    private final Stack<Integer> maxStack = new Stack<>();

    @Override
    public synchronized Integer pop() {
        if (peek().equals(maxStack.peek())) {
            maxStack.pop();
        }
        return super.pop();
    }

    @Override
    public Integer push(Integer item) {
        if (maxStack.empty() || item.compareTo(maxStack.peek()) >= 0) {
            maxStack.push(item);
        }
        return super.push(item);
    }

    public Integer getMaxValue() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return maxStack.peek();
    }

}
