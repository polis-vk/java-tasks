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
    private final LinkedList<Integer> maxStack = new LinkedList<>();

    @Override
    public Integer push(Integer item) {
        if (maxStack.isEmpty() || maxStack.getLast() <= item) {
            maxStack.add(item);
        }
        return super.push(item);
    }

    @Override
    public Integer pop() {
        if (maxStack.getLast().equals(super.lastElement())) {
            maxStack.pollLast();
        }
        return super.pop();
    }

    public Integer getMaxValue() {
        if (maxStack.isEmpty()) {
            throw new EmptyStackException();
        }
        return maxStack.getLast();
    }

}
