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

    private final Stack<Pair> stackOfMax = new Stack<>();

    @Override
    public Integer push(Integer item) {
        if (stackOfMax.size() == 0 || getMaxValue() < item) {
            stackOfMax.push(new Pair(item, 1));
        } else {
            stackOfMax.peek().value++;
        }
        return super.push(item);
    }

    @Override
    public Integer pop() {
        if (stackOfMax.empty()) {
            throw new EmptyStackException();
        }
        if (stackOfMax.peek().value-- == 1) {
            stackOfMax.pop();
        }
        return super.pop();
    }

    public Integer getMaxValue() {
        if (stackOfMax.empty()) {
            throw new EmptyStackException();
        } else {
            return stackOfMax.peek().key;
        }
    }

    private class Pair {

        int key;
        int value;

        public Pair(int key, int value) {
            this.key = key;
            this.value = value;
        }

    }
}
