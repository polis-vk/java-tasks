package ru.mail.polis.homework.collections.structure;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Задание оценивается в 2 балла.
 * Надо расширить стандартный класс Stack методом getMaxValue().
 * Этот метод должен возвращать какое максимальное значение сейчас
 * храниться в Stack. Отрабатывать метод должен за О(1).
 */
public class MaxStack extends Stack<Integer> {

    private int maxValue;
    private ArrayList<Integer> stack;

    public MaxStack() {
        maxValue = Integer.MIN_VALUE;
    }

    @Override
    public synchronized Integer peek() {
        return stack.get(stack.size() - 1);
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
        }
        stack.add(item);
        return item;
    }

    @Override
    public synchronized Integer pop() {
        int popElement = stack.remove(stack.size() - 1);
        if (popElement == maxValue) {
            maxValue = Integer.MIN_VALUE;
            for (Integer element : stack) {
                maxValue = Math.max(element, maxValue);
            }
        }
        return popElement;
    }

    public Integer getMaxValue() {
        return maxValue;
    }

}
