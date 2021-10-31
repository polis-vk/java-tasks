package ru.mail.polis.homework.collections.structure;

import java.util.Stack;

/**
 * Задание оценивается в 2 балла.
 * Надо расширить стандартный класс Stack методом getMaxValue().
 * Этот метод должен возвращать какое максимальное значение сейчас
 * храниться в Stack. Отрабатывать метод должен за О(1).
 */
public class MaxStack extends Stack<Integer> {

    private final Stack<Integer> maxes = new Stack<>();

    @Override
    public Integer push(Integer item) {
        if (maxes.isEmpty() || maxes.peek() < item) {
            maxes.push(item);
        }
        return super.push(item);
    }

    @Override
    public synchronized Integer pop() {
        Integer poppedVal = super.pop();
        if (maxes.peek().equals(poppedVal)) {
            return maxes.pop();
        }
        return poppedVal;
    }

    public Integer getMaxValue() {
        return maxes.peek();
    }

}
