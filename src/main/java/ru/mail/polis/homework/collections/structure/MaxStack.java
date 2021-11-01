package ru.mail.polis.homework.collections.structure;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.EmptyStackException;
import java.util.Stack;

/**
 * Задание оценивается в 2 балла.
 * Надо расширить стандартный класс Stack методом getMaxValue().
 * Этот метод должен возвращать какое максимальное значение сейчас
 * храниться в Stack. Отрабатывать метод должен за О(1).
 */
public class MaxStack extends Stack<Integer> {

    private final Deque<Integer> maxValues = new ArrayDeque<>();

    @Override
    public Integer push(Integer item) {
        if (maxValues.size() == 0 || item.compareTo(maxValues.peek()) >= 0) {
            maxValues.push(item);
        }
        return super.push(item);
    }

    @Override
    public synchronized Integer pop() {
        if (size() == 0) {
            throw new EmptyStackException();
        }
        if (peek().equals(maxValues.peek())) {
            maxValues.pop();
        }
        return super.pop();
    }

    public Integer getMaxValue() {
        if (size() == 0) {
            throw new EmptyStackException();
        }
        return maxValues.peek();
    }

}
