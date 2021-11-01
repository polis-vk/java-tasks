package ru.mail.polis.homework.collections.structure;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;

/**
 * Задание оценивается в 2 балла.
 * Надо расширить стандартный класс Stack методом getMaxValue().
 * Этот метод должен возвращать какое максимальное значение сейчас
 * храниться в Stack. Отрабатывать метод должен за О(1).
 */


public class MaxStack extends Stack<Integer> {

    private final List<Integer> sorted = new ArrayList<>();
    private final Stack<Integer> last = new Stack<>();
    private Integer max = 0;


    @Override
    public Integer push(Integer item) {
        sorted.add(item);
        Collections.sort(sorted);
        max = sorted.get(sorted.size() - 1);
        last.push(item);
        return super.push(item);
    }

    @Override
    public synchronized boolean add(Integer integer) {
        sorted.add(integer);
        Collections.sort(sorted);
        max = sorted.get(sorted.size() - 1);
        last.push(integer);
        return super.add(integer);
    }

    @Override
    public synchronized Integer pop() {
        sorted.remove(last.peek());
        Collections.sort(sorted);
        max = sorted.get(sorted.size() - 1);
        last.pop();
        return super.pop();
    }

    public Integer getMaxValue() {
        if (sorted.isEmpty()) {
            throw new EmptyStackException();
        }
        return max;
    }

}