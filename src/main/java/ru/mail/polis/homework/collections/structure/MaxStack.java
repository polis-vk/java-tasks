package ru.mail.polis.homework.collections.structure;

import java.util.Comparator;
import java.util.EmptyStackException;
import java.util.PriorityQueue;
import java.util.Stack;

/**
 * Задание оценивается в 2 балла.
 * Надо расширить стандартный класс Stack методом getMaxValue().
 * Этот метод должен возвращать какое максимальное значение сейчас
 * храниться в Stack. Отрабатывать метод должен за О(1).
 */
public class MaxStack extends Stack<Integer> {

    private final PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Comparator.reverseOrder());

    @Override
    public synchronized Integer pop() {
        maxHeap.remove(peek());
        return super.pop();
    }

    @Override
    public Integer push(Integer item) {
        maxHeap.add(item);
        return super.push(item);
    }

    public Integer getMaxValue() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return maxHeap.peek();
    }

}
