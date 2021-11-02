package ru.mail.polis.homework.collections.structure;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.EmptyStackException;
import java.util.Stack;

/**
 * Задание оценивается в 2 балла.
 * Надо расширить стандартный класс Stack методом getMaxValue().
 * Этот метод должен возвращать какое максимальное значение сейчас
 * храниться в Stack. Отрабатывать метод должен за О(1).
 */
public class MaxStack extends Stack<Integer> {
    
    private final Deque<Integer> stack = new ArrayDeque<Integer>();

    @Override
    public Integer push(Integer item) {
        if (this.size() == 0) {
            this.stack.push(0);
        } else if (item >= (Integer) this.elementData[this.stack.peek()]) {
            this.stack.push(this.size());
        }
        return super.push(item);
    }

    @Override
    public synchronized Integer pop() {
        if (this.stack.size() > 0 && this.size() - 1 == this.stack.peek()) {
            this.stack.pop();
        }
        return super.pop();
    }

    public Integer getMaxValue() {
        if (this.size() == 0) {
            throw new EmptyStackException();
        }
        return (Integer) this.elementData[this.stack.peek()];
    }

}
