package ru.mail.polis.homework.collections.structure;

import java.util.*;

/**
 * Задание оценивается в 2 балла.
 * Надо расширить стандартный класс Stack методом getMaxValue().
 * Этот метод должен возвращать какое максимальное значение сейчас
 * храниться в Stack. Отрабатывать метод должен за О(1).
 */
public class MaxStack extends Stack<Integer> {

    private final Deque<Integer> stackMaxValues = new ArrayDeque<>();

    @Override
    public Integer push(Integer item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (stackMaxValues.isEmpty() || item >= stackMaxValues.peek()) {
            stackMaxValues.push(item);
        }
        return super.push(item);
    }

    @Override
    public synchronized Integer pop() {
        if (super.peek().equals(stackMaxValues.peek())) {
            stackMaxValues.pop();
            return super.pop();
        }
        return super.pop();
    }

    public Integer getMaxValue() {
        if (super.isEmpty()) {
            throw new EmptyStackException();
        }
        return stackMaxValues.peek();
    }

}
