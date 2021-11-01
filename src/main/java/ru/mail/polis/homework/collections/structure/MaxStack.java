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

    private final Deque<Integer> maxStack = new LinkedList<>();

    public Integer getMaxValue() {
        if (size() == 0) {
            throw new EmptyStackException();
        }
        return maxStack.getLast();
    }

    @Override
    public Integer push(Integer item) {
        insertMax(item);
        return super.push(item);
    }

    @Override
    public Integer pop() {
        maxStack.removeLast();
        return super.pop();
    }

    private void insertMax(Integer value) {
        if (maxStack.isEmpty() || maxStack.getLast() < value) {
            maxStack.add(value);
        } else {
            maxStack.add(maxStack.getLast());
        }
    }

}
