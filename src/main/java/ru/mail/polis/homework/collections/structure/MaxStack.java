package ru.mail.polis.homework.collections.structure;

import java.util.Deque;
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
    private final Deque<Integer> maximums = new LinkedList<>();

    public Integer getMaxValue() {
        if (this.isEmpty()) {
            throw new EmptyStackException();
        }
        return maximums.getFirst();
    }

    @Override
    public synchronized Integer pop() {
        Integer retval = super.pop();
        if (retval.equals(maximums.getFirst())) {
            maximums.pop();
        }
        return retval;
    }

    @Override
    public Integer push(Integer item) {
        if (this.isEmpty() || maximums.getFirst() <= item) {
            maximums.push(item);
        }
        return super.push(item);
    }
}
