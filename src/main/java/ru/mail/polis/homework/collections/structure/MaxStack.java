package ru.mail.polis.homework.collections.structure;


import java.util.Collections;
import java.util.EmptyStackException;
import java.util.Stack;

/**
 * Задание оценивается в 2 балла.
 * Надо расширить стандартный класс Stack методом getMaxValue().
 * Этот метод должен возвращать какое максимальное значение сейчас
 * храниться в Stack. Отрабатывать метод должен за О(1).
 */
public class MaxStack extends Stack<Integer> {

    private int max = Integer.MIN_VALUE;

    @Override
    public Integer push(Integer value) {
        if (value > max) {
            max = value;
        }
        super.push(value);
        return value;
    }

    @Override
    public Integer pop() {
        int elementToRemove = super.pop();
        if (elementToRemove == max) {
            max = Collections.max(this);
        }
        return elementToRemove;
    }

    public Integer getMaxValue() {
        if (size() == 0) {
            throw new EmptyStackException();
        }
        return max;
    }
}
