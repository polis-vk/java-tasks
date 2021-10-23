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

    private Integer maxValue = Integer.MIN_VALUE;

    @Override
    public Integer push(Integer item) {
        if (item.compareTo(maxValue) > 0) {
            maxValue = item;
        }
        return super.push(item);
    }

    @Override
    public synchronized Integer pop() {
        if (size() == 0) {
            throw new EmptyStackException();
        }
        if (peek().equals(maxValue)) {
            Integer max = Integer.MIN_VALUE;
            for (int i = 0; i < size() - 1; i++) {
                if (((Integer) elementData[i]).compareTo(max) > 0) {
                    max = (Integer) elementData[i];
                }
            }
            maxValue = max;
        }
        return super.pop();
    }

    public Integer getMaxValue() {
        if (size() == 0) {
            throw new EmptyStackException();
        }
        return maxValue;
    }

}
