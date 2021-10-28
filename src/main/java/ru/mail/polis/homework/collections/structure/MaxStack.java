package ru.mail.polis.homework.collections.structure;

import java.util.Stack;

/**
 * Задание оценивается в 2 балла.
 * Надо расширить стандартный класс Stack методом getMaxValue().
 * Этот метод должен возвращать какое максимальное значение сейчас
 * храниться в Stack. Отрабатывать метод должен за О(1).
 */
public class MaxStack extends Stack<Integer> {
    private final Stack<Integer> maxElems = new Stack<>();

    public Integer getMaxValue() {
        return maxElems.peek();
    }

    @Override
    public Integer push(Integer item) {
        if (maxElems.size() > 0 && maxElems.peek() > item) {
            maxElems.push(maxElems.peek());
        } else {
            maxElems.push(item);
        }
        return super.push(item);
    }

    @Override
    public synchronized Integer pop() {
        maxElems.pop();
        return super.pop();
    }
}
