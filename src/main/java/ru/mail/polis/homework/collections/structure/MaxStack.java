package ru.mail.polis.homework.collections.structure;

import java.util.EmptyStackException;
import java.util.LinkedList;
import java.util.Stack;

/**
 * Задание оценивается в 2 балла.
 * Надо расширить стандартный класс Stack методом getMaxValue().
 * Этот метод должен возвращать какое максимальное значение сейчас
 * храниться в Stack. Отрабатывать метод должен за О(1).
 */
public class MaxStack extends Stack<Integer> {

    private final LinkedList<Integer> maxes = new LinkedList<>();

    public Integer getMaxValue() {
        if (size() == 0) {
            throw new EmptyStackException();
        }
        return maxes.getLast();
    }

    @Override
    public Integer push(Integer item) {
        insertMax(item);
        return super.push(item);
    }

    @Override
    public Integer pop() {
        int returned = super.pop();
        maxes.remove((Object) returned);
        return returned;
    }

    private void insertMax(Integer value) {
        for (int i = 0; i <= maxes.size(); i++) {
            if (i == maxes.size() || value <= maxes.get(i)) {
                maxes.add(i, value);
                return;
            }
        }
    }

}
