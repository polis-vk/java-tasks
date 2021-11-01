package ru.mail.polis.homework.collections.structure;

import java.util.*;

/**
 * Задание оценивается в 2 балла.
 * Надо расширить стандартный класс Stack методом getMaxValue().
 * Этот метод должен возвращать какое максимальное значение сейчас
 * хранится в Stack. Отрабатывать метод должен за О(1).
 */
public class MaxStack extends Stack<Integer> {

    private final Deque<Integer> maximums = new ArrayDeque<>();

    @Override
    public Integer push(Integer item) {
        if (maximums.isEmpty() || maximums.getFirst() <= item) {
            maximums.push(item);
        }
        return super.push(item);
    }

    @Override
    public synchronized Integer pop() {
        int curElem = super.pop();
        if (curElem == maximums.getFirst()) {
            maximums.pop();
        }
        return curElem;
    }

    public Integer getMaxValue() {
        if (empty()) {
            throw new EmptyStackException();
        }
        return maximums.getFirst();
    }

}
