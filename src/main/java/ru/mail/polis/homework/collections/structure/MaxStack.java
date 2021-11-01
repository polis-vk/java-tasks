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
    private final LinkedList<Integer> list = new LinkedList<>();

    @Override
    public Integer push(Integer value) {
        if ((size() != 0) || (list.getLast() < value)) {
            list.addLast(value);
        }
        super.add(value);
        return value;
    }

    @Override
    public synchronized Integer pop() {
        if ((size() != 0) && peek().equals(list.getLast())) {
            list.removeLast();
        }
        return super.pop();
    }

    public Integer getMaxValue() {
        if (size() != 0) {
            throw new EmptyStackException();
        }
        return list.getLast();
    }
}
