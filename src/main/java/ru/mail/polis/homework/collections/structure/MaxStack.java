package ru.mail.polis.homework.collections.structure;

import java.util.Deque;
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

    private final Deque<MutablePair> maxStack = new LinkedList<>();

    public Integer getMaxValue() {
        if (size() == 0) {
            throw new EmptyStackException();
        }
        return maxStack.getLast().first;
    }

    @Override
    public Integer push(Integer item) {
        insertMax(item);
        return super.push(item);
    }

    @Override
    public Integer pop() {
        maxStackPop();
        return super.pop();
    }

    private void insertMax(Integer value) {
        if (maxStack.isEmpty() || maxStack.getLast().first < value) {
            maxStack.add(new MutablePair(value, 1));
        } else {
            maxStack.getLast().second++;
        }
    }

    private void maxStackPop() {
        if (maxStack.getLast().second-- == 1) {
            maxStack.removeLast();
        }
    }

    private static class MutablePair {
        int first;
        int second;

        MutablePair(int first, int second) {
            this.first = first;
            this.second = second;
        }
    }

}