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

    private final Deque<Integer> stack = new LinkedList<>();

    @Override
    public synchronized Integer pop() {
        if (!stack.isEmpty() && super.peek().equals(stack.getLast())) {
            stack.removeLast();
        }
        return super.pop();
    }


    @Override
    public Integer push(Integer item) {
        if (stack.isEmpty() || item >= stack.getLast()) {
            stack.addLast(item);
        }
        return super.push(item);
    }

    public Integer getMaxValue() {
        if (stack.isEmpty()) {
            throw new EmptyStackException();
        }
        return stack.getLast();
    }

}
