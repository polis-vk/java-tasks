package ru.mail.polis.homework.collections.structure;

import java.util.Collection;
import java.util.Stack;
import java.util.function.Predicate;

/**
 * Задание оценивается в 2 балла.
 * Надо расширить стандартный класс Stack методом getMaxValue().
 * Этот метод должен возвращать какое максимальное значение сейчас
 * храниться в Stack. Отрабатывать метод должен за О(1).
 */
public class MaxStack extends Stack<Integer> {
    private final Stack<Integer> maxes = new Stack<>();

    public Integer getMaxValue() {
        return maxes.peek();
    }

    @Override
    public Integer push(Integer item) {
        maxes.push(maxes.isEmpty() ? item : Integer.max(getMaxValue(), item));
        return super.push(item);
    }

    @Override
    public synchronized Integer pop() {
        maxes.pop();
        return super.pop();
    }

    @Override
    public void clear() {
        maxes.clear();
        super.clear();
    }

    @Override
    public synchronized Integer remove(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized void removeAllElements() {
        maxes.removeAllElements();
        super.removeAllElements();
    }

    @Override
    public synchronized boolean removeElement(Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected synchronized void removeRange(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeIf(Predicate<? super Integer> filter) {
        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized void removeElementAt(int index) {
        if (index != size() - 1) {
            throw new IllegalArgumentException("Can only remove last element");
        }
        super.removeElementAt(index);
    }

    @Override
    public synchronized void setSize(int newSize) {
        maxes.setSize(newSize);
        super.setSize(newSize);
    }
}
