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
    private int indexOfMax = 0;

    @Override
    public Integer push(Integer item) {
        super.push(item);
        if (item > elementAt(indexOfMax)) {
            indexOfMax = size() - 1;
        }
        return item;
    }

    @Override
    public synchronized Integer pop() {
        if (indexOfMax == size() - 1) {
            if (size() != 1) {
                int max = elementAt(size() - 2);
                int index = size() - 2;
                for (int i = size() - 3; i >= 0; i--) {
                    if (elementAt(i) > max) {
                        max = elementAt(i);
                        index = i;
                    }
                }
                indexOfMax = index;
            } else {
                indexOfMax = 0;
            }
        }
        return super.pop();
    }

    public int getMaxValue() {
        if (size() == 0) {
            throw new EmptyStackException();
        }
        return elementAt(indexOfMax);
    }

}
