package ru.mail.polis.homework.collections.structure;

import java.util.*;

/**
 * Задание оценивается в 2 балла.
 * Надо расширить стандартный класс Stack методом getMaxValue().
 * Этот метод должен возвращать какое максимальное значение сейчас
 * храниться в Stack. Отрабатывать метод должен за О(1).
 */
public class MaxStack extends Stack<Integer> {

    private final LinkedList<Integer> maxVals;

    public MaxStack() {
        super();
        maxVals = new LinkedList<>();
    }

    @Override
    public Integer push(Integer item) {
        if (maxVals.isEmpty()) {
            maxVals.add(item);
        } else {
            maxVals.add(Math.max(maxVals.peekLast(), item));
        }
        return super.push(item);
    }

    @Override
    public synchronized Integer pop() {
        maxVals.removeLast();
        return super.pop();
    }

    public Integer getMaxValue() {
        if (maxVals.isEmpty()) {
            throw new EmptyStackException();
        }
        return maxVals.peekLast();
    }

}
