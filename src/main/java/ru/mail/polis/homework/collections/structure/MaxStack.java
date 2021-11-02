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
    //Будем хранить в стеке не только сами элементы, а пары элемент + максимум
    //Для хранения максимумов при каждом элементе будем использовать стек maxValues
    private final Stack<Integer> maxValues = new Stack<>();

    MaxStack() {
        super();
    }

    @Override
    public Integer push(Integer item) {
        if (maxValues.isEmpty() || item >= maxValues.peek()) {
            maxValues.push(item);
        }
        else {
            maxValues.push(maxValues.peek());
        }

        return super.push(item);
    }

    @Override
    public synchronized Integer pop() {
        maxValues.pop();
        return super.pop();
    }

    public Integer getMaxValue() {
        if (this.size() == 0) {
            throw new EmptyStackException();
        }

        return maxValues.peek();
    }
}
