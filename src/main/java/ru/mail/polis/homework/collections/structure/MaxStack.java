package ru.mail.polis.homework.collections.structure;

import java.util.EmptyStackException;
import java.util.Stack;

/**
 * Задание оценивается в 2 балла.
 * Надо расширить стандартный класс Stack методом getMaxValue().
 * Этот метод должен возвращать какое максимальное значение сейчас
 * хранится в Stack. Отрабатывать метод должен за О(1).
 */
public class MaxStack extends Stack<Integer> {

    private int MAX_VALUE = Integer.MIN_VALUE;

    @Override
    public Integer push(Integer item) {
        MAX_VALUE = Math.max(MAX_VALUE, item);
        return super.push(item);
    }

    @Override
    public synchronized Integer pop() {
        int lastElem = super.pop();
        if (lastElem == MAX_VALUE) {
            MAX_VALUE = peek();
            for (int i = 0; i < size(); ++i) {
                MAX_VALUE = Math.max(MAX_VALUE, elementAt(i));
            }
        }
        return MAX_VALUE;
    }

    public Integer getMaxValue() {
        if (empty()) {
            throw new EmptyStackException();
        }
        return MAX_VALUE;
    }

}
