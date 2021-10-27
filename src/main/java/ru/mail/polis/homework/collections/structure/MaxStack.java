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
    private int max = Integer.MIN_VALUE;

    @Override
    public Integer push(Integer item) {
        if (item != null) {
            max = Math.max(max, item);
            this.addElement(item);
        }
        return item;
    }

    @Override
    public synchronized Integer pop() {
        int len = this.size();
        if (len == 0) {
            return null;
        }
        int obj = this.peek();
        this.removeElementAt(len - 1);
        if (max == obj) {
            max = Integer.MIN_VALUE;
            for (int el : this) {
                max = Math.max(max, el);
            }
        }
        return obj;
    }

    public Integer getMaxValue() {
        if (this.size() == 0) {
            throw new EmptyStackException();
        }
        return max;
    }
}
