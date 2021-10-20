package ru.mail.polis.homework.collections.structure;

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
    private int countMaxValues;
    private LinkedList<Integer> stack;

    public MaxStack() {
        maxValue = Integer.MIN_VALUE;
        countMaxValues = 0;
    }

    @Override
    public synchronized Integer peek() {
        return stack.peekLast();
    }

    @Override
    public boolean empty() {
        return stack.isEmpty();
    }

    @Override
    public synchronized int search(Object o) {
        int i = stack.lastIndexOf((Integer) o);
        return i >= 0 ? size() - i : -1;
    }

    @Override
    public Integer push(Integer item) {

        if (item > maxValue) {
            maxValue = item;
            countMaxValues = 1;
        } else if (item == maxValue) {
            countMaxValues++;
        }
        stack.addLast(item);
        return item;
    }

    @Override
    public synchronized Integer pop() {
        int popElement = super.pop();
        if (popElement == maxValue && --countMaxValues == 0) {
            maxValue = Integer.MIN_VALUE;
            for (Integer element : stack) {
                maxValue = Math.max(element, maxValue);
            }
            countMaxValues = 1;
        }
        return popElement;
    }

    public Integer getMaxValue() {
        return maxValue;
    }

}
