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
    private Stack<Integer> maxValues = new Stack<>();
    private Integer maxValue = Integer.MIN_VALUE;


    @Override
    public Integer push(Integer item) {
        if (item >= maxValue) {
            maxValues.push(maxValue);
            maxValue = item;
        }

        return super.push(item);
    }

    @Override
    public synchronized Integer pop() {
        assert !maxValues.isEmpty();
        int item = super.pop();
        if (item == maxValue) {
            maxValue = maxValues.pop();
        }

        return item;
    }

    public Integer getMaxValue() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        assert !maxValues.isEmpty();

        return maxValue;
    }

}
