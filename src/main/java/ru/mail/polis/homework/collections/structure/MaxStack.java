package ru.mail.polis.homework.collections.structure;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

/**
 * Задание оценивается в 2 балла.
 * Надо расширить стандартный класс Stack методом getMaxValue().
 * Этот метод должен возвращать какое максимальное значение сейчас
 * храниться в Stack. Отрабатывать метод должен за О(1).
 */
public class MaxStack extends Stack<Integer> {

    private int maxValue;

    public MaxStack() {
        super();
        maxValue = Integer.MIN_VALUE;
    }

    @Override
    public Integer push(Integer item) {
        if (item > maxValue) {
            maxValue = item;
        }
        return super.push(item);
    }

    @Override
    public synchronized Integer pop() {
        int popElement = super.pop();
        if (popElement == maxValue) {
            maxValue = Integer.MIN_VALUE;
            LinkedList<Integer> stackForCopy = new LinkedList<>();
            while (super.size() > 0) {
                stackForCopy.addFirst(super.pop());
                maxValue = Math.max(stackForCopy.getFirst(), maxValue);
            }
            super.addAll(stackForCopy);
        }
        return popElement;
    }

    public Integer getMaxValue() {
        return maxValue;
    }

}
