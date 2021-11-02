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

    private final Stack<Integer> stackOfMax = new Stack<>();

    @Override
    public Integer push(Integer item) {
        if (stackOfMax.size() == 0 || stackOfMax.peek() < item) {
            stackOfMax.push(item);
        } else {
            stackOfMax.push(stackOfMax.peek());
        }
        return super.push(item);
    }

    @Override
    public Integer pop() {
        if (stackOfMax.empty()) {
            throw new EmptyStackException();
        }
        stackOfMax.pop();
        return super.pop();
    }

    public Integer getMaxValue() {
        if (stackOfMax.empty()) {
            throw new EmptyStackException();
        } else {
            return stackOfMax.peek();
        }
    }
}
