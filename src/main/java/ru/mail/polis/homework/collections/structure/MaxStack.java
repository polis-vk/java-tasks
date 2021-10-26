package ru.mail.polis.homework.collections.structure;

import java.util.Stack;

/**
 * Задание оценивается в 2 балла.
 * Надо расширить стандартный класс Stack методом getMaxValue().
 * Этот метод должен возвращать какое максимальное значение сейчас
 * храниться в Stack. Отрабатывать метод должен за О(1).
 */
public class MaxStack extends Stack<Integer> {
    @Override
    public Integer push(Integer item) {
        for (int i = 0; i < size(); i++) {
            if (item < elementAt(i)) {
                insertElementAt(item, i);
                return item;
            }
        }
        addElement(item);
        return item;
    }

    public int getMaxValue() {
        return elementAt(size() - 1);
    }

    public static void main(String[] args) {
        MaxStack maxStack = new MaxStack();
        int[] a = new int[]{1, 2, 4, 5, 23, 12, 45, 12, 43, 12};
        for (int el : a) {
            maxStack.push(el);
            System.out.println(maxStack.getMaxValue());
        }
        System.out.println("\n");
        for (int el : a) {
            System.out.println(maxStack.pop());
        }
    }
}
