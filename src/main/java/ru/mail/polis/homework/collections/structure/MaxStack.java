package ru.mail.polis.homework.collections.structure;

import java.util.Stack;

/**
 * Задание оценивается в 2 балла.
 * Надо расширить стандартный класс Stack методом getMaxValue().
 * Этот метод должен возвращать какое максимальное значение сейчас
 * храниться в Stack. Отрабатывать метод должен за О(1).
 */
public class MaxStack extends Stack<Integer> {
    private final Stack<Integer> orderedStack = new Stack<>();

    public Integer getMaxValue() {
        return orderedStack.peek();
    }

    @Override
    public Integer push(Integer val) {
        if (orderedStack.isEmpty()) {
            orderedStack.push(val);
            return val;
        }
        Stack<Integer> elems = new Stack<>();
        int size = orderedStack.size();
        for (int i = 0; i < size; i++) {
            Integer currVal = orderedStack.peek();
            if (val >= currVal) {
                break;
            }
            elems.push(orderedStack.pop());
        }
        orderedStack.push(val);
        int elemsSize = elems.size();
        for (int i = 0; i < elemsSize; i++) {
            orderedStack.push(elems.pop());
        }
        return super.push(val);
    }

    @Override
    public Integer pop() {
        Integer removedVal = super.pop();
        int sizeOrdered = orderedStack.size();
        Stack<Integer> elems = new Stack<>();
        for (int i = 0; i < sizeOrdered; i++) {
            Integer currVal = orderedStack.peek();
            if (removedVal.equals(currVal)) {
                orderedStack.pop();
                break;
            }
            elems.push(orderedStack.pop());
        }
        int elemsSize = elems.size();
        for (int i = 0; i < elemsSize; i++) {
            orderedStack.push(elems.pop());
        }
        return removedVal;
    }
}
