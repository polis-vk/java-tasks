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

    /** Из найденной мной информации метод Collections.max
     *  отрабатывает за O(n), поэтому данное задание реализовано
     *  именно таким образом.
     */
    private int max = Integer.MIN_VALUE;

    @Override
    public Integer push(Integer item) {
        if (item > max) {
            max = item;
        }
        addElement(item);
        return item;
    }

    public Integer getMaxValue() {
        if (size() == 0) {
            throw new EmptyStackException();
        }
        return max;
    }

}
