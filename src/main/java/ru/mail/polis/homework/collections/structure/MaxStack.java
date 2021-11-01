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

    private final LinkedList<Integer> maxList = new LinkedList<>();

    @Override
    public Integer push(Integer item) {
        if (isEmpty() || item > maxList.getLast()) {
            maxList.addLast(item);
        }
        return super.push(item);
    }

    @Override
    public synchronized Integer pop() {
        if (peek().equals(maxList.getLast())) {
            maxList.removeLast();
        }
        return super.pop();
    }

    public Integer getMaxValue() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return maxList.getLast();
    }
}
