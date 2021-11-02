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
    Stack<Integer> maxIndices = new Stack<>();

    @Override
    public Integer push(Integer item) { //O(1)
        if (maxIndices.isEmpty()) {
            maxIndices.push(0);
        } else if (item > elementAt(maxIndices.peek())) {
            maxIndices.push(size());
        }
        return super.push(item);
    }

    @Override
    public synchronized Integer pop() {//O(1)
        if (maxIndices.size() > 0 && size() - 1 == maxIndices.peek()) {
            maxIndices.pop();
        }
        return super.pop();
    }

    public Integer getMaxValue() {//O(1)
        if (size() == 0) {
            throw new EmptyStackException();
        }
        return elementAt(maxIndices.peek());
    }

}
