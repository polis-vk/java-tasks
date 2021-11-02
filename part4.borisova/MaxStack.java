package ru.mail.polis.homework.collections.structure;

import java.util.*;

/**
 * Задание оценивается в 2 балла.
 * Надо расширить стандартный класс Stack методом getMaxValue().
 * Этот метод должен возвращать какое максимальное значение сейчас
 * храниться в Stack. Отрабатывать метод должен за О(1).
 */

public class MaxStack extends Stack<Integer> {
    Stack<Integer> stackMaxValues = new Stack<>();

    @Override
    public Integer push(Integer integer) {
        if (stackMaxValues.empty() || stackMaxValues.peek() <= integer) {
            stackMaxValues.push(integer);
        }
        return super.push(integer);
    }

    @Override
    public Integer pop() {
        Integer temp = super.pop();
        if (temp.equals(stackMaxValues.peek())) {
            stackMaxValues.pop();
        }
        return temp;
    }

    public Integer getMaxValue() {

        return (stackMaxValues.peek());
    }
}
