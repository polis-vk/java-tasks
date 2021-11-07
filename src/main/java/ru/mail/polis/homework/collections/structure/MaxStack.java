package ru.mail.polis.homework.collections.structure;

import java.util.*;

/**
 * Задание оценивается в 2 балла.
 * Надо расширить стандартный класс Stack методом getMaxValue().
 * Этот метод должен возвращать какое максимальное значение сейчас
 * храниться в Stack. Отрабатывать метод должен за О(1).
 */
public class MaxStack extends Stack<Integer> {

    private final Deque<Integer> maxValsIndex = new LinkedList<>();

    public MaxStack() {
        super();
    }

    @Override
    public Integer push(Integer item) {
        if (maxValsIndex.isEmpty()) {
            maxValsIndex.push(0);
        } else {
            if (get(maxValsIndex.peek()) < item) {
                maxValsIndex.push(size());
            }
        }

        return super.push(item);
    }

    @Override
    public synchronized Integer pop() {
        if (maxValsIndex.peek() == size() - 1) {
            maxValsIndex.pop();
        }
        return super.pop();
    }

    public Integer getMaxValue() {
        if (maxValsIndex.isEmpty()) {
            throw new EmptyStackException();
        }
        return get(maxValsIndex.peek());
    }

}
